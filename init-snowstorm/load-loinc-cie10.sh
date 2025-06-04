#!/bin/bash
echo "Inicio de Carga LOINC y CIE10"
if [ ! -f /status/script2_executed ]; then
  if [ ! -f /code/hapi-fhir-cli.jar ]; then
    wget -O /code/hapi-fhir-7.0.2-cli.zip https://github.com/hapifhir/hapi-fhir/releases/download/v7.0.2/hapi-fhir-7.0.2-cli.zip
    unzip /code/hapi-fhir-7.0.2-cli.zip -d /code/hapi-fhir-7.0.2-cli
    mv /code/hapi-fhir-7.0.2-cli/hapi-fhir-cli.jar /code/hapi-fhir-cli.jar
    rm /code/hapi-fhir-7.0.2-cli.zip
    rm -R /code/hapi-fhir-7.0.2-cli
  fi
  chmod +x /code/hapi-fhir-cli.jar
  java --version
  if [ ! -f /files/$ICD10_FILE ]; then
      echo "Archivo para carga de CIE-10 no Encontrado!"
      exit 1
  fi
  if [ ! -f /files/$LOINC_FILE ]; then
      echo "Archivo para carga de LOINC no Encontrado!"
      exit 1
  fi
  echo "Comenzando Carga CIE-10"
  java -jar /code/hapi-fhir-cli.jar upload-terminology -s 10GB -d /files/$ICD10_FILE -v R4 -t http://snowstorm:8080/fhir -u http://hl7.org/fhir/sid/icd-10
  echo "Comenzando Carga LOINC"
  java -jar /code/hapi-fhir-cli.jar upload-terminology -s 10GB -d /files/$LOINC_FILE -v R4 -t http://snowstorm:8080/fhir -u http://loinc.org
  echo "Proceso Finalizado"
  echo "1" >> /status/script2_executed
else
  echo "Ejecucion Omitida"
fi