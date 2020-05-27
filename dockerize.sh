#!/bin/bash
mvn clean package -DskipTests
docker build -f Dockerfile -t market4.0/data-app .