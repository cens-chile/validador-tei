/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cens.validador.fhirServer;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;
import ca.uhn.fhir.narrative.INarrativeGenerator;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import com.cens.validador.AppProperties;
import com.cens.validador.interceptors.CapabilityStatementCustomizer;
import com.cens.validador.provider.BundleResourceProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author José <jose.m.andrade@gmail.com>
 */
public class FHIRValidadorRestfulServer extends RestfulServer{
    
    private static final long serialVersionUID = 1L;

	@Autowired
	AppProperties appProperties;

	@Autowired
	BundleResourceProvider bundleResourceProvider;

	/**
	 * Constructor
	 */
	public FHIRValidadorRestfulServer() {

		super(FhirContext.forR4Cached()); // This is an R4 server


	}
	
	/**
	 * This method is called automatically when the
	 * servlet is initializing.
	 */
	@Override
	public void initialize() {

		setResourceProviders(bundleResourceProvider);
		/*
		 * Use a narrative generator. This is a completely optional step, 
		 * but can be useful as it causes HAPI to generate narratives for
		 * resources which don't otherwise have one.
		 */
		INarrativeGenerator narrativeGen = new DefaultThymeleafNarrativeGenerator();
		getFhirContext().setNarrativeGenerator(narrativeGen);

		/*
		 * Use nice coloured HTML when a browser is used to request the content
		 */
		registerInterceptor(new ResponseHighlighterInterceptor());
		registerInterceptor(new CapabilityStatementCustomizer(appProperties));
                	
	}
    
}
