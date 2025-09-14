#!/bin/bash

echo "Starting DEV..."
docker-compose --env-file .env.dev up --build