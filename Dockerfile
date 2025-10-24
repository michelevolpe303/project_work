# Usa l'immagine ufficiale di MySQL
FROM mysql:8.0

# Imposta variabili d'ambiente per MySQL
ENV MYSQL_ROOT_PASSWORD=rootpass
ENV MYSQL_DATABASE=mydb
ENV MYSQL_USER=myuser
ENV MYSQL_PASSWORD=mypass

# Copia il file schema.sql nella cartella di inizializzazione di MySQL
COPY schema.sql /docker-entrypoint-initdb.d/
COPY test.sql /

# Espone la porta di MySQL
EXPOSE 3306
