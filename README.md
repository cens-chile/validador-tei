# Validador TEI

## Requerimientos

* 8gb de RAM
* 5gb disco para volumen snowstorm

## Puesta en Marcha

* Crear archivo .env

```env
SERVER_PORT=9090
TEI_FHIR_PACKAGE_TEI=package_tei.tgz
TEI_FHIR_PACKAGE_TEI_VERSION=0.2.1
TEI_FHIR_PACKAGE_CORE=package_core.tgz
SNOMED_FHIR_BASE_URL=http://snowstorm:8080/fhir
```

### Puesta en marcha con Docker Compose

```bash
docker compose up -d
```

### Puesta en marcha solo modo desarrollo

* Actualizar archivo .env la variable **SNOMED_FHIR_BASE_URL** con el valor http://localhost:8180/fhir

* Levantar SnowStorm

```bash
docker compose up -d snowstorm
```

* Iniciar proyecto con maven.

```bash
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