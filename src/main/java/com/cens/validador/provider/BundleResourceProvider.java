package com.cens.validador.provider;

import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Validate;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.ValidationModeEnum;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.cens.validador.tools.CustomFhirValidator;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This is a resource provider which stores RelatedPerson resources in memory using a HashMap. This is obviously not a production-ready solution for many reasons,
 * but it is useful to help illustrate how to build a fully-functional server.
 */
@Component
public class BundleResourceProvider implements IResourceProvider {
    private static final Logger ourLog = LoggerFactory.getLogger(BundleResourceProvider.class);
    CustomFhirValidator validator;

  public BundleResourceProvider(CustomFhirValidator validator) {
    this.validator = validator;
  }

  /**
     * The getResourceType method comes from IResourceProvider, and must be overridden to indicate
     * what type of resource this provider supplies.
     */
    @Override
    public Class<Bundle> getResourceType() {
        return Bundle.class;
    }

    @Validate
    public MethodOutcome validate(@ResourceParam Bundle theBundle,
        @Validate.Mode ValidationModeEnum theMode,
        @Validate.Profile String theProfile) {
        // This method returns a MethodOutcome object
        MethodOutcome retVal = new MethodOutcome();
        // You may also add an OperationOutcome resource to return
        // This part is optional though:
        OperationOutcome outcome = (OperationOutcome) validator.validateWithResult(theBundle).toOperationOutcome();
        retVal.setOperationOutcome(outcome);
        return retVal;
    }

}




