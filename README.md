# Validador TEI

## Alcances

* Si valida códigos CIE10 SNOMED LOINC 
* No valida el display de los códigos.
* Valida estructuralmente cada Bundle.

## Requerimientos

* 8gb de RAM
* 5gb disco para volumen snowstorm
* Descargar fuente LOINC: https://loinc-org.translate.goog/downloads/?_x_tr_sl=en&_x_tr_tl=es&_x_tr_hl=es&_x_tr_pto=tc
* Descargar fuente CIE10: https://icdcdn.who.int/icd10/index.html

## Puesta en Marcha

* Guardar archivos LOINC y CIE10 en la carpeta **init-files**
* Crear archivo .env

```env
SERVER_PORT=9090
TEI_FHIR_PACKAGE_TEI=package_tei.tgz
TEI_FHIR_PACKAGE_TEI_VERSION=0.2.1
TEI_FHIR_PACKAGE_CORE=package_core.tgz
SNOMED_FHIR_BASE_URL=http://snowstorm:8080/fhir
LOINC_FHIR_BASE_URL=http://snowstorm:8180/fhir
CIE10_FHIR_BASE_URL=http://snowstorm:8180/fhir
ICD10_FILE=icd102019en.xml.zip
LOINC_FILE=Loinc_2.80.zip
```

### Puesta en marcha con Docker Compose

```bash
docker volume rm validador-tei_snowstorm_preload
sudo rm -rf .snowstorm-init-status/
docker compose up -d
```

### Puesta en marcha solo modo desarrollo

* Actualizar archivo .env la variable **SNOMED_FHIR_BASE_URL** con el valor http://localhost:8180/fhir
* Actualizar archivo .env la variable **LOINC_FHIR_BASE_URL** con el valor http://localhost:8180/fhir
* Actualizar archivo .env la variable **CIE10_FHIR_BASE_URL** con el valor http://localhost:8180/fhir

#### Levantar SnowStorm

* Guardar archivos LOINC y CIE10 en la carpeta **init-files**
* Crear archivo .env

```env
SERVER_PORT=9090
TEI_FHIR_PACKAGE_TEI=package_tei.tgz
TEI_FHIR_PACKAGE_TEI_VERSION=0.2.1
TEI_FHIR_PACKAGE_CORE=package_core.tgz
SNOMED_FHIR_BASE_URL=http://snowstorm:8080/fhir
LOINC_FHIR_BASE_URL=http://snowstorm:8180/fhir
CIE10_FHIR_BASE_URL=http://snowstorm:8180/fhir
ICD10_FILE=icd102019en.xml.zip
LOINC_FILE=Loinc_2.80.zip
```

```bash
docker volume rm validador-tei_snowstorm_preload
sudo rm -rf .snowstorm-init-status/
docker compose up -d snowstorm 
docker compose up -d init-script-snowstorm
docker compose up -d init-script-loinc-cie
```

* Iniciar proyecto con maven.

```bash
export SNOMED_FHIR_BASE_URL=http://localhost:8180/fhir
export LOINC_FHIR_BASE_URL=http://localhost:8180/fhir
export CIE10_FHIR_BASE_URL=http://localhost:8180/fhir
mvn spring-boot:run
```

## Actualizar Packages segun versión de Guía

* Agregar packages en la ruta arc/main/resources

* bajar servicio y reconstruir nueva imagen.

```bash
docker compose down
docker compose up -d --build
```

## Acceso al Validador

* Validar Bundle

```
curl --location 'http://localhost:9090/fhir/Bundle/$validate' \
--header 'Cache-Control: no-cache' \
--header 'Content-Type: application/json' \
--data '{}'
```
* Se debe agregar el contenido del Bundle a validar.

## Acceso al Browser de Snowstorm

* http://localhost:8082/

## Version de Snomed

* March 2024 release of the SNOMED CT Spanish Edition. 