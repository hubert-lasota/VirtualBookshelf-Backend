#!/bin/bash

echo "Starting PROD..."
docker-compose --env-file .env.prod up --build