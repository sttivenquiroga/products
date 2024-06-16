FROM mysql:latest

# Variables de entorno para configurar MySQL
ENV MYSQL_DATABASE=products
ENV MYSQL_USER=usertest
ENV MYSQL_PASSWORD=123456789
ENV MYSQL_ROOT_PASSWORD=987654321

# Copiar un script de inicialización si necesitas configuraciones adicionales
COPY ./init.sql /docker-entrypoint-initdb.d/

# Exponer el puerto 3306
EXPOSE 3306