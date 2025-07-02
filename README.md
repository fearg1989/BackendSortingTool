# BackendSortingTool API
🚀 Project developed with **Spring Boot**, **Docker**, **H2**, **Cache**, and **Hexagonal Architecture**.

Autor: Felix Rodriguez

Email: felixalejandr33@gmail.com

## Technologies Used
- **Java 23** → Programming language.
- **Spring Boot** → Backend framework.
- **Hexagonal Architecture** → Decoupled and modular design.
- **Cache**
- **H2 Database** → In-memory database for testing.
- **Docker** → Containerized deployment.

## How to Run the Project
### 1️⃣ Clone the repository
git clone https://github.com/fearg1989/BackendSortingTool.git

### 2️⃣ Build the project
mvn clean install

### 3️⃣ Run the application locally
mvn spring-boot:run


### 4️⃣ Check the API in Swagger
Visit: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Running with Docker
### 1️⃣ Build the Docker image
docker build -t prices-app .

### 2️⃣ Run the container
docker run -p 8080:8080 prices-app

## Postman Collection
docs/postman/BackendSortingTool.postman_collection.json

## JaCoco Report
target/site/jacoco/index.html