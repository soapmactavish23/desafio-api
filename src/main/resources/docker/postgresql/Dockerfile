# docker build -t my-postgres .
# docker run -d -p 5432:5432 --name my-postgres-container my-postgres

FROM postgres:latest

ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=mydb

EXPOSE 5432