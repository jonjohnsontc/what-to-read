#!/usr/bin/env bash

# This is designed to run in our containerized dev environment

# exit immediately on errors
set -e

# Capture current datetime in ISO8601 fmt and remove unnecessary chars
date_str=$(date -I'minutes' | sed "s/[-:]//g")

pg_dump -af "/var/dumps/${date_str}_pg_dump.sql"