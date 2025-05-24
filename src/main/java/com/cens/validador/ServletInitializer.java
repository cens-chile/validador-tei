package com.cens.validador;

import com.cens.validador.fhirServer.FHIRValidadorRestfulServer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FHIRValidadorRestfulServer.class);
	}

}
