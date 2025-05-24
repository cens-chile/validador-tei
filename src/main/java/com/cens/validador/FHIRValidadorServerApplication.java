package com.cens.validador;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.context.support.IValidationSupport;
import ca.uhn.fhir.context.support.IValidationSupport.IssueSeverity;
import ca.uhn.fhir.context.support.ValidationSupportContext;
import ca.uhn.fhir.validation.FhirValidator;
import com.cens.validador.fhirServer.FHIRValidadorRestfulServer;
import com.cens.validador.tools.CustomFhirValidator;
import javax.print.attribute.standard.Severity;
import org.hl7.fhir.common.hapi.validation.support.*;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.r4.utils.validation.IValidationPolicyAdvisor;
import org.hl7.fhir.r5.utils.validation.constants.BestPracticeWarningLevel;
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
        UnknownCodeSystemWarningValidationSupport unknownCodeSystemWarningValidationSupport = new UnknownCodeSystemWarningValidationSupport(
            ctx);
        unknownCodeSystemWarningValidationSupport.setNonExistentCodeSystemSeverity(
            IssueSeverity.INFORMATION);
        ValidationSupportChain validationSupportChain = new ValidationSupportChain(
            npmPackageSupport,
            new DefaultProfileValidationSupport(ctx),
            new PrePopulatedValidationSupport(ctx),
            new CommonCodeSystemsTerminologyService(ctx),
            new InMemoryTerminologyServerValidationSupport(ctx),
            new RemoteTerminologyServiceValidationSupport(ctx,
                appProperties.getValidador().getSnomed_fhir_base_url()),
            new SnapshotGeneratingValidationSupport(ctx),
            unknownCodeSystemWarningValidationSupport);
        CachingValidationSupport validationSupport = new CachingValidationSupport(
            validationSupportChain);
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
}
