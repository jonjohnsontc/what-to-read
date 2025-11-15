#!/bin/bash
# PostgreSQL initialization script
# This script runs automatically when the PostgreSQL container is first created
# It creates the test database needed for integration tests

set -e

echo "Creating test database..."

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    -- Create test database if it doesn't exist
    SELECT 'CREATE DATABASE postgres_test'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'postgres_test')\gexec

    -- Grant privileges
    GRANT ALL PRIVILEGES ON DATABASE postgres_test TO postgres;
EOSQL

echo "Test database 'postgres_test' created successfully!"
