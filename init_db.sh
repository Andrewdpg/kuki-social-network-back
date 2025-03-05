#!/bin/bash
service postgresql start
psql -c "CREATE USER $DB_USERNAME WITH PASSWORD '$DB_PASSWORD';"
psql -c "CREATE DATABASE $DB_NAME OWNER $DB_USERNAME;"