# Product test CRUD With Authentication

Breve descripción de la aplicación.

## Prerrequisitos

Antes de empezar, asegúrate de tener los siguientes programas instalados en tu máquina:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Configuración

1. **Clonar el repositorio**

   ```sh
   git clone https://github.com/sttivenquiroga/products.git
   cd products

2. **Construir los contenedores**

   ```sh
   docker-compose build

3. **Levantar los contenedores**

   ```sh
   docker-compose up
4. **Realizar pruebas**
   En este apartado puedes importar los siguientes dos archivos en postman:
   
   Prueba Productos.postman_collection
   
   Prueba productos.postman_environment

   Una vez importados puedes empezar tus pruebas asegurandote de seleccionar el ambiente del desplegable:
   ![image](https://github.com/sttivenquiroga/products/assets/83785617/f9cf3891-b9e7-4614-a1ba-3f10a6c20224)

   Nota: hay dos usuarios creados, por lo que a nivel de creación, actualización y borrado solo te permitira acceder con un usuario de tipo ADMIN, para los get podrás acceder con un usuario rol USER.

   Datos ADMIN:
    "username": "usertest",
    "password": "123456789"

   Datos USER:
    "username": "prueba",
    "password": "doh!12345"

   

