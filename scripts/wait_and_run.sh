#!/bin/sh

until pg_isready -h db -p 5432 -U postgres; do
  echo "Waiting for postgres..."
  sleep 2
done

echo "Running Flyway migrations..."
flyway -url=jdbc:postgresql://db:5432/your_db -user=postgres -password=${POSTGRES_PASSWORD} migrate
if [ $? -ne 0 ]; then
  echo "Flyway migration failed!"
  exit 1
fi

if [ "$DEV_MODE" = "true" ]; then
  echo "Running in development mode..."
  mvn spring-boot:run
else
  echo "Running JAR..."
  exec java -jar /app/whatToRead-0.0.1-SNAPSHOT.jar
fi