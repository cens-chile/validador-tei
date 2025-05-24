package com.cens.validador.interceptors;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import com.cens.validador.AppProperties;
import org.hl7.fhir.instance.model.api.IBaseConformance;
import org.hl7.fhir.r4.model.CapabilityStatement;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.codesystems.RestfulSecurityService;

@Interceptor
public class CapabilityStatementCustomizer {

	private AppProperties appProperties;
	public CapabilityStatementCustomizer(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	@Hook(Pointcut.SERVER_CAPABILITY_STATEMENT_GENERATED)
	public void customize(IBaseConformance theCapabilityStatement) {

		// Cast to the appropriate version
		CapabilityStatement cs = (CapabilityStatement) theCapabilityStatement;

		// Customize the CapabilityStatement as desired
		cs
			.getSoftware()
			.setName("TEI Validator FHIR SERVER")
			.setVersion("1.0")
			.setReleaseDateElement(new DateTimeType("2025-06-15"));
	}

}
