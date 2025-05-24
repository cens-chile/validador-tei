package com.cens.validador;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "hapi.fhir")
@Configuration
@EnableConfigurationProperties
public class AppProperties {

	private Validador validador = new Validador();

	public Validador getValidador() {
		return validador;
	}

	public void setValidador(Validador validador) {
		this.validador = validador;
	}

	public static class Validador {
		private String fhir_package_tei = null;
		private String fhir_package_core = null;
		private String fhir_package_tei_version = null;
		private String snomed_fhir_base_url = null;


		public String getFhir_package_tei_version() {
			return fhir_package_tei_version;
		}

		public void setFhir_package_tei_version(String fhir_package_tei_version) {
			this.fhir_package_tei_version = fhir_package_tei_version;
		}

		public String getSnomed_fhir_base_url() {
			return snomed_fhir_base_url;
		}

		public void setSnomed_fhir_base_url(String snomed_fhir_base_url) {
			this.snomed_fhir_base_url = snomed_fhir_base_url;
		}

		public String getFhir_package_tei() {
			return fhir_package_tei;
		}

		public void setFhir_package_tei(String fhir_package_tei) {
			this.fhir_package_tei = fhir_package_tei;
		}

		public String getFhir_package_core() {
			return fhir_package_core;
		}

		public void setFhir_package_core(String fhir_package_core) {
			this.fhir_package_core = fhir_package_core;
		}
	}
	}

