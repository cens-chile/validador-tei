<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a id="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Unlicense License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">

  <h3 align="center">Componente de Validación</h3>

  <p align="center">
    Componente que permite la validación de eventos basados en la guía TEI 
    <br />
    <a href="https://interoperabilidad.minsal.cl/fhir/ig/tei/0.2.1/index.html"><strong>Guía TEI »</strong></a>
    <br />
    <br />
    <a href="https://github.com/cens-chile/validador-tei">Repositorio</a>
    &middot;
    <a href="https://github.com/cens-chile/validador-tei/issues/new?labels=bug&template=bug-report---.md">Reportar Bug</a>
    &middot;
    <a href="https://github.com/cens-chile/validador-tei/issues/new?labels=enhancement&template=feature-request---.md">Solicitar Funcionalidades</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#acerca-del-proyecto">Acerca del Proyecto</a>
      <ul>
        <li><a href="#construido-con">Desarrollado con</a></li>
      </ul>
    </li>
    <li>
      <a href="#como-empezar">Como Empezar</a>
      <ul>
        <li><a href="#requisitos-del-sistema-operativo">Requisitos del sistema operativo</a></li>
        <li><a href="#hardware-recomendado">Hardware recomendado</a></li>
        <li><a href="#prerrequisitos">Prerequisitos</a></li>
        <li><a href="#instalación">Instalación</a></li>
        <li><a href="#desarrollo">Desarrollo</a></li>
      </ul>
    </li>
    <li>
      <a href="#uso">Uso</a>
      <ul>
        <li><a href="#funcionalidades">Funcionalidades</a></li>
        <li><a href="#acceso-al-browser-de-snowstorm">Acceso a SNOWSTORM</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contribuir">Contribuir</a></li>
    <li><a href="#licencia">Licencia</a></li>
    <li><a href="#contacto">Contacto</a></li>
    <li><a href="#agradecimientos">Agradecimientos</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## Acerca del Proyecto

El sistema de salud en Chile se estructura en niveles (primario, secundario y terciario), 
siendo el nivel primario el con mayor despliegue en el territorio, con atenciones de menor complejidad
y la puerta de entrada a todas las atenciones de salud en la red pública de establecimientos. 
Para optar a una atención de especialidad, las personas deben ser derivadas desde la atención primaria 
a un centro de mayor complejidad, teniendo que esperar para recibir esta atención en el nivel secundario o terciario.

Las personas y tiempos que deben esperar para una atención de salud han sido y son una preocupación para todo el 
sistema sanitario.

Los sistemas que soportan actualmente la información de las personas y tiempos de espera por su estructura y forma
de operar, no permiten conocer la realidad de la situación, trazar al paciente y tampoco permite mantener informado
al paciente. Para mejorar la gestión de la red asistencial y la coordinación entre sus niveles, se requiere implementar
un proceso interoperable de solicitud de nueva consulta de especialidad desde APS a nivel secundario, para patologías
no adscritas a las garantías explícitas de salud (GES).

El componente de Validación permite contar con una herramienta que realiza una revisión del contenido de un evento evaluando
su comprobación de las variables y su estructura basado en la guía [TEI](https://interoperabilidad.minsal.cl/fhir/ig/tei/0.2.1/index.html), 
además realiza una validación de los conjuntos de valores definidos en las estructuras de la gía de Implementación y de catalogos externos como SNOMED, LOINC, ICD10. 

Utiliza un Servidor [HAPIFHIR Plain Server](https://hapifhir.io/hapi-fhir/docs/server_plain/server_types.html#plain-server-facade) con la precarga de la guía de implementación TEI y un Servidor terminológico Open Source llamado [Snowstorm](https://github.com/IHTSDO/snowstorm)


<p align="right">(<a href="#readme-top">volver al inicio</a>)</p>



### Construido con

* [![Java][Java.com]][Java-url]
* [![Hapifhir][Hapifhir.io]][Hapifhir-url]
* [![Elastic][Elastic.co]][Elastic-url]
* [![Snowstorm][Snowstorm]][Snowstorm-url]
* [![Git][Git-scm.com]][Git-url]
* [![Docker][Docker.com]][Docker-url]


<p align="right">(<a href="#readme-top">volver al inicio</a>)</p>



<!-- GETTING STARTED -->
## Como Empezar

Inicialmente se necesita un servidor donde desplegar el componente de validación con acceso a internet

### Requisitos del sistema operativo

* GNU/Linux 3.10 o superior.

### Hardware recomendado

* 12 GB
* 20 GB o más de espacio de disco duro.

### Prerrequisitos

This is an example of how to list things you need to use the software and how to install them.
* [Instalación de Docker](https://docs.docker.com/desktop/setup/install/linux/)
* [Instalación de GIT](https://git-scm.com/downloads/linux)
* Descargar fuente LOINC: https://loinc-org.translate.goog/downloads/?_x_tr_sl=en&_x_tr_tl=es&_x_tr_hl=es&_x_tr_pto=tc
* Descargar fuente CIE10: https://icdcdn.who.int/icd10/index.html


### Instalación

1. Clone the repo
    ```sh
    cd $HOME/
    git https://github.com/cens-chile/validador-tei
    cd $HOME/validador-tei
    ```
2. Mover los archivos descargados de LOINC y CIE10 en carpeta **init-files**

Los nombres de los archivos pueden variar según las versiones
   
   * icd102019en.xml.zip
   * Loinc_2.80.zip
    
3. Ingresamos al directorio en donde se descargo el código
    ```sh
    cd $HOME/validador-tei
    ```  
4. Creamos el arhivo con las variables de ambiente
    ```sh
    nano .env
    ```  
5. Ingresamos las variables de ambiente al archivo **.env**
    ```env
    SERVER_PORT=9090
    TEI_FHIR_PACKAGE_TEI=package_tei.tgz
    TEI_FHIR_PACKAGE_TEI_VERSION=0.2.1
    TEI_FHIR_PACKAGE_CORE=package_core.tgz
    SNOMED_FHIR_BASE_URL=http://snowstorm:8180/fhir
    LOINC_FHIR_BASE_URL=http://snowstorm:8180/fhir
    CIE10_FHIR_BASE_URL=http://snowstorm:8180/fhir
    ICD10_FILE=icd102019en.xml.zip
    LOINC_FILE=Loinc_2.80.zip
    ```
| Variable                      | Descripción                                                                                                                                     | Ejemplo                    |
|:----------------------------- |:-----------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------|
| SERVER_PORT                   | Puerto del Servicio                                                                                                                             | 9090                       |
| TEI_FHIR_PACKAGE_TEI          | Nombre del archivo package.tgz descargado desde la guía de implementación(modificar solo en caso  especiales)                                   | package_tei.tgz            |
| TEI_FHIR_PACKAGE_TEI_VERSION  | Version del package.tgz(modificar solo en caso  especiales)                                                                                     | 0.2.1                      |
| TEI_FHIR_PACKAGE_CORE         | Nombre del archivo package.tgz de la guía Core-CL(modificar solo en caso  especiales) debe ser un valor distinto al de **TEI_FHIR_PACKAGE_TEI** | package_core.tgz           |
| SNOMED_FHIR_BASE_URL          | Url base del servidor snowstorm(modificar solo en caso  especiales)                                                                             | http://snowstorm:8180/fhir |
| LOINC_FHIR_BASE_URL           | Url base del servidor LOINC(modificar solo en caso  especiales)                                                                                 | http://snowstorm:8180/fhir |
| CIE10_FHIR_BASE_URL           | Url base del servidor CIE10(modificar solo en caso  especiales)                                                                                 | http://snowstorm:8180/fhir |
| ICD10_FILE                    | Nombre del archivo comprimido de los términos CIE10                                                                                             | icd102019en.xml.zip        |
| LOINC_FILE                    | Nombre del archivo comprimido de los términos LOINC                                                                                             | Loinc_2.80.zip             |

    
6. Puesta en marcha
    ```bash
    docker volume rm validador-tei_snowstorm_preload
    sudo rm -rf .snowstorm-init-status/
    docker compose up -d
    ```  
* De ser necesario volver a poner en marcha una versión limpia se debe hacer lo siguiente
  ```bash
  docker compose down
  docker volume rm validador-tei_snowstorm_preload
  sudo rm -rf .snowstorm-init-status/
  docker compose up -d
  ```
<p align="right">(<a href="#readme-top">volver al inicio</a>)</p>

### Desarrollo

#### Iniciar solo SNOWSTORM con precarga de CIE10 y LOINC

* Iniciar los servicios

```bash
docker volume rm validador-tei_snowstorm_preload
sudo rm -rf .snowstorm-init-status/
docker compose up -d snowstorm 
docker compose up -d init-script-snowstorm
docker compose up -d init-script-loinc-cie
```

#### Iniciar modo desarrollo, requiere que este disponible el servicio de SNOWSTORM

```bash
export SNOMED_FHIR_BASE_URL=http://localhost:8180/fhir
export LOINC_FHIR_BASE_URL=http://localhost:8180/fhir
export CIE10_FHIR_BASE_URL=http://localhost:8180/fhir
mvn spring-boot:run
```

#### Actualizar Packages según versión de guía TEI/CORE

* Agregar packages en la ruta **src/main/resources**
* cambiar nombres de archivos de ser necesario
* bajar servicio y reconstruir nueva imagen.

```bash
docker compose down
docker compose up -d --build
```

<p align="right">(<a href="#readme-top">volver al inicio</a>)</p>

<!-- USAGE EXAMPLES -->
## Uso

* Accedemos a http://0.0.0.0:9090/fhir que sería la ruta base del servidor FHIR

### Funcionalidades

* **_/Bundle/$validate/_**: Endpoint para validación
* Ejemplo:

```bash
curl --location 'http://localhost:9090/fhir/Bundle/$validate' \
--header 'Cache-Control: no-cache' \
--header 'Content-Type: application/json' \
--data '{}'
```
### Acceso al Browser de Snowstorm

* http://localhost:8082/
* Version de Snomed: March 2024 release of the SNOMED CT Spanish Edition.

Para ejemplos de los Bundle de eventos consultar la [Documentación](https://interoperabilidad.minsal.cl/fhir/ig/tei/0.2.1/index.html)

<p align="right">(<a href="#readme-top">volver al inicio</a>)</p>

<!-- ROADMAP -->
## Roadmap

- [x] Validar códigos CIE10 SNOMED LOINC
- [x] Validar Bundles de distintos eventos
- [ ] Validaciónes Adicionales
    - [ ] Validar Display de Códigos

Ver la sección de [open issues](https://github.com/cens-chile/validador-tei/issues) para una lista complete de las nuevas funcionalidades (y errores conocidos).

<p align="right">(<a href="#readme-top">volver al inicio</a>)</p>



<!-- Contribuir -->
## Contribuir

Toda contribución que hagas será agradecida

Si tienes alguna sugenrencia para hacer mejor este proyecto, por favor crea tu fork y crea un pull request. También puedes abrir un issue con el tag "mejora"
No olvides dar una estrella al proyecto! Gracias!

1. Crea un fork de este proyecto
2. Crea un branch para tu funcionalidad (`git checkout -b feature/AmazingFeature`)
3. Haz el Commit con tus cambios(`git commit -m 'Add: mi funcionalidad'`)
4. Sube tus cambios al repositorio (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

### Top contributors:

<a href="https://github.com/cens-chile/validador-tei/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=cens-chile/validador-tei" />
</a>

<p align="right">(<a href="#readme-top">volver al inicio</a>)</p>



<!-- LICENSE -->
## Licencia

Apache 2.0

Ver el archivo incluido `LICENSE` para detalles.

<p align="right">(<a href="#readme-top">volver al inicio</a>)</p>



<!-- CONTACT -->
## Contacto

Interoperabilidad - [@CENSChile](https://x.com/CENSChile) - interoperabilidad@cens.cl

Link al Proyecto: [https://github.com/cens-chile/validador-tei](https://github.com/cens-chile/validador-tei)

<p align="right">(<a href="#readme-top">volver al inicio</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Agradecimientos

* Equipo CENS

<p align="right">(<a href="#readme-top">volver al inicio</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/cens-chile/validador-tei.svg?style=for-the-badge
[contributors-url]: https://github.com/cens-chile/validador-tei/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/cens-chile/validador-tei.svg?style=for-the-badge
[forks-url]: https://github.com/cens-chile/validador-tei/network/members
[stars-shield]: https://img.shields.io/github/stars/cens-chile/validador-tei.svg?style=for-the-badge
[stars-url]: https://github.com/cens-chile/validador-tei/stargazers
[issues-shield]: https://img.shields.io/github/issues/cens-chile/validador-tei.svg?style=for-the-badge
[issues-url]: https://github.com/cens-chile/validador-tei/issues
[license-shield]: https://img.shields.io/badge/Apache-LICENSE-as?style=for-the-badge&logo=apache
[license-url]: https://github.com/cens-chile/validador-tei/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/cens-chile-red?style=for-the-badge&labelColor=blue
[linkedin-url]: https://linkedin.com/in/othneildrew
[Java-url]: https://www.java.com/
[Java.com]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[Elastic.co]: https://img.shields.io/badge/elasticsearch-%230377CC.svg?style=for-the-badge&logo=elasticsearch&logoColor=white
[Elastic-url]: https://www.elastic.co/elasticsearch
[Hapifhir.io]: https://img.shields.io/badge/HAPI%20FHIR-%F0%9F%94%A5-white?style=for-the-badge
[Hapifhir-url]: https://hapifhir.io/
[DjangoREST-url]: https://www.django-rest-framework.org/
[DjangoREST.org]: https://img.shields.io/badge/DJANGO-REST-ff1709?style=for-the-badge&logo=django&logoColor=white&color=ff1709&labelColor=gray
[Snowstorm]: https://img.shields.io/badge/Snowstorm-SNOMED-blue?style=for-the-badge
[Snowstorm-url]: https://github.com/IHTSDO/snowstorm
[Git-scm.com]: https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white
[Git-url]: https://git-scm.com/
[Docker.com]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/
