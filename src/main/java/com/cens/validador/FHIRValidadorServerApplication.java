package com.cens.validador;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.ConceptValidationOptions;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.context.support.IValidationSupport;
import ca.uhn.fhir.context.support.IValidationSupport.IssueSeverity;
import ca.uhn.fhir.context.support.ValidationSupportContext;
import ca.uhn.fhir.rest.api.SummaryEnum;
import com.cens.validador.fhirServer.FHIRValidadorRestfulServer;
import com.cens.validador.tools.CustomFhirValidator;
import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.common.hapi.validation.support.*;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r5.utils.validation.constants.BestPracticeWarningLevel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, ElasticsearchRestClientAutoConfiguration.class })
public class FHIRValidadorServerApplication {

    private final Logger ourLog = LoggerFactory.getLogger(FHIRValidadorServerApplication.class);
    @Autowired
    AutowireCapableBeanFactory beanFactory;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        context = SpringApplication.run(FHIRValidadorServerApplication.class, args);
    }
        
    @Bean
    public ServletRegistrationBean hapiServletRegistration(AppProperties appProperties) throws IOException {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        FHIRValidadorRestfulServer fhirValidadorRestfulServer = new FHIRValidadorRestfulServer();
        beanFactory.autowireBean(fhirValidadorRestfulServer);
        servletRegistrationBean.setServlet(fhirValidadorRestfulServer);
        servletRegistrationBean.addUrlMappings("/fhir/*");
        servletRegistrationBean.setLoadOnStartup(1);

        return servletRegistrationBean;
  }

    @Bean
    public CustomFhirValidator validator(AppProperties appProperties) throws IOException {

        // Create a validator. Note that for good performance you can create as many validator objects
        // as you like, but you should reuse the same validation support object in all of the,.
        FhirContext ctx = FhirContext.forR4Cached();
        CustomFhirValidator validator =  new CustomFhirValidator(ctx);
        IValidationSupport validationSupport = validationsupport(appProperties);
        FhirInstanceValidator instanceValidator = new FhirInstanceValidator(validationSupport);
        validator.registerValidatorModule(instanceValidator);
        instanceValidator.setBestPracticeWarningLevel(BestPracticeWarningLevel.Ignore);
        return validator;
    }

    @Bean
    public IValidationSupport validationsupport(AppProperties appProperties) throws IOException {
        // Create an NPM Package Support module and load one package in from
        // the classpath
        FhirContext ctx = FhirContext.forR4Cached();
        NpmPackageValidationSupport npmPackageSupport = new NpmPackageValidationSupport(ctx);
        npmPackageSupport.loadPackageFromClasspath(
            "classpath:" + appProperties.getValidador().getFhir_package_tei());
        npmPackageSupport.loadPackageFromClasspath(
            "classpath:" + appProperties.getValidador().getFhir_package_core());
        // Create a support chain including the NPM Package Support
        UnknownCodeSystemWarningValidationSupport unknownCodeSystemWarningValidationSupport = new UnknownCodeSystemWarningValidationSupport(ctx);
        unknownCodeSystemWarningValidationSupport.setNonExistentCodeSystemSeverity(IssueSeverity.INFORMATION);
        ValidationSupportChain validationSupportChain = new ValidationSupportChain(
            npmPackageSupport,
            new DefaultProfileValidationSupport(ctx),
            new PrePopulatedValidationSupport(ctx),
            new CommonCodeSystemsTerminologyService(ctx),
            getInMemoryTerminologyServerValidationSupport(ctx),
            new SnapshotGeneratingValidationSupport(ctx),
            unknownCodeSystemWarningValidationSupport);
        validationSupportChain.addValidationSupport(4,getRemoteTerminologyServiceValidationSupport(ctx, appProperties.getValidador().getLoinc_fhir_base_url(), "http://loinc.org"));
        validationSupportChain.addValidationSupport(5,getRemoteTerminologyServiceValidationSupport(ctx, appProperties.getValidador().getCie10_fhir_base_url(), "http://hl7.org/fhir/sid/icd-10"));
        validationSupportChain.addValidationSupport(6, getRemoteTerminologyServiceValidationSupport(ctx, appProperties.getValidador().getSnomed_fhir_base_url(), "http://snomed.info/sct"));
        CachingValidationSupport validationSupport = new CachingValidationSupport(validationSupportChain);
        return validationSupport;
    }

    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(FHIRValidadorServerApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }

    public InMemoryTerminologyServerValidationSupport getInMemoryTerminologyServerValidationSupport(FhirContext ctx){
        return new InMemoryTerminologyServerValidationSupport(ctx);
    }
    public RemoteTerminologyServiceValidationSupport getRemoteTerminologyServiceValidationSupport(FhirContext ctx, String serverUrl, String serverSystem){
        ourLog.info("Conectando Servidor: {} System: {}",serverUrl, serverSystem);
        return new RemoteTerminologyServiceValidationSupport(ctx, serverUrl){
            @Override
            public boolean isCodeSystemSupported(
                ValidationSupportContext theValidationSupportContext, String theSystem) {
                return serverSystem.equalsIgnoreCase(theSystem);
            }

            @Override
            public boolean isValueSetSupported(ValidationSupportContext theValidationSupportContext, String theValueSetUrl) {
                SummaryEnum summaryParam = null;
                boolean result = super.isValueSetSupported(theValidationSupportContext, theValueSetUrl);
                if(!result){
                    if(serverSystem.equalsIgnoreCase("http://loinc.org")){
                        List<String> specialValueSets = new ArrayList<>();
                        specialValueSets.add("https://interoperabilidad.minsal.cl/fhir/ig/tei/ValueSet/CodigoExamen");
                        if(specialValueSets.contains(theValueSetUrl)){
                            return true;
                        }
                    } else if (serverSystem.equalsIgnoreCase("http://hl7.org/fhir/sid/icd-10")) {
                        List<String> specialValueSets = new ArrayList<>();
                        specialValueSets.add("https://interoperabilidad.minsal.cl/fhir/ig/tei/ValueSet/VSTerminologiasDiag");
                        if(specialValueSets.contains(theValueSetUrl)){
                            return true;
                        }
                    }
                }
                return result;
            }

            @Override
            public CodeValidationResult validateCode(
                ValidationSupportContext theValidationSupportContext,
                ConceptValidationOptions theOptions,
                String theCodeSystem,
                String theCode,
                String theDisplay,
                String theValueSetUrl) {
                if (serverSystem.equalsIgnoreCase(theCodeSystem)) {
                    return super.validateCode(
                        theValidationSupportContext,
                        theOptions,
                        theCodeSystem,
                        theCode,
                        null,
                        theValueSetUrl);
                }
                return null;
            }
            @Override
            public IValidationSupport.CodeValidationResult validateCodeInValueSet(ValidationSupportContext theValidationSupportContext, ConceptValidationOptions theOptions, String theCodeSystem, String theCode, String theDisplay, @NotNull IBaseResource theValueSet) {
                IBaseResource valueSet = theValueSet;
                String codeSystem = theCodeSystem;
                if (StringUtils.isNotBlank(theCode) && StringUtils.isBlank(theCodeSystem)) {
                    codeSystem = ValidationSupportUtils.extractCodeSystemForCode(theValueSet, theCode);
                }

                String valueSetUrl = DefaultProfileValidationSupport.getConformanceResourceUrl(this.myCtx, theValueSet);
                if (StringUtils.isNotBlank(valueSetUrl)) {
                    valueSet = null;
                } else {
                    valueSetUrl = null;
                }
                if(serverSystem.equalsIgnoreCase("http://loinc.org")){
                    valueSet = theValidationSupportContext.getRootValidationSupport().fetchValueSet(valueSetUrl);
                }
                return this.invokeRemoteValidateCode(codeSystem, theCode, null, null, valueSet);
            }
        };
    }
}
