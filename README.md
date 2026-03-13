# Práctica de Dockerización - Proyecto Cursos

Este proyecto muestra cómo **dockerizar una aplicación web completa** compuesta por:

* Frontend desarrollado con **Angular**
* Backend desarrollado con **Spring Boot**
* Base de datos **MySQL**

La aplicación se ejecuta completamente en **contenedores Docker** y puede ser consumida desde el **host (localhost)**.

---

# Precondiciones

Antes de ejecutar el proyecto debes tener instalado:

* Docker
* Git

Verificar instalación:

```bash
docker --version
git --version
```

---

# Clonar el repositorio

```bash
git clone https://github.com/yuseth289/practica-de-dockerizacion-.git
cd practica-de-dockerizacion-
```

---

# Crear redes Docker

El proyecto utiliza **dos redes** para separar responsabilidades:

* `frond-cursos` → comunicación frontend ↔ backend
* `backend-network` → comunicación backend ↔ base de datos

Crear redes:

```bash
docker network create frond-cursos
docker network create backend-network
```

Verificar redes:

```bash
docker network ls
```

---

# Crear volumen para la base de datos

Esto permite **persistir los datos de MySQL** aunque el contenedor se elimine.

```bash
docker volume create mysql-data
```

Verificar volumen:

```bash
docker volume ls
```

---

# Crear contenedor de MySQL

```bash
docker run -d --name mysql-cursos --network backend-network -v mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cursos -e MYSQL_USER=cursos -e MYSQL_PASSWORD=cursos -p 3307:3306 mysql:8
```

Verificar contenedor:

```bash
docker ps
```

---

# Construir imagen del Backend

```bash
docker build .

o

docker build -t backend-spring-cursos .
```

---

# Ejecutar contenedor Backend

Primero lo conectamos a la red de backend:

```bash
docker run -d --name backend-spring-cursos --network backend-network -p 8080:8080 backend-spring-cursos
```

Luego conectamos el backend a la red del frontend:

```bash
docker network connect frond-cursos backend-spring-cursos
```

---

# Construir imagen del Frontend

```bash
docker build -t cursos-frontend ./frontend
```

---

# Ejecutar contenedor Frontend

```bash
docker run -d --name cursos-frontend --network frond-cursos -p 4200:80 cursos-frontend
```

---

# Acceder a la aplicación

Frontend:

```
http://localhost:4200
```

Backend API:

```
http://localhost:8080
```

---

# Verificar contenedores

```bash
docker ps
```

Contenedores esperados:

```
cursos-frontend
backend-spring-cursos
mysql-cursos
```

--

# Detener contenedores

```bash
docker stop cursos-frontend
docker stop backend-spring-cursos
docker stop mysql-cursos
```

---

# Eliminar contenedores

```bash
docker rm cursos-frontend
docker rm backend-spring-cursos
docker rm mysql-cursos
```

---

# Eliminar imágenes

```bash
docker rmi cursos-frontend
docker rmi backend-spring-cursos
```

---

# Eliminar redes

```bash
docker network rm frond-cursos
docker network rm backend-network
```

---

# Eliminar volumen

```bash
docker volume rm mysql-data
```

---
