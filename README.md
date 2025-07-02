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

### Ejemplo de Request

```json
POST /api/products/sort
Content-Type: application/json

{
  "weights": {
    "salesUnits": 0.1,
    "stockRatio": 10
  }
}
```

### Ejemplo de Response

```json
[
    {
        "id": 4,
        "name": "PLEATED T-SHIRT",
        "salesUnits": 3,
        "stockBySize": {
            "S": 25,
            "M": 30,
            "L": 10
        }
    },
    {
        "id": 2,
        "name": "CONTRASTING FABRIC T-SHIRT",
        "salesUnits": 50,
        "stockBySize": {
            "S": 35,
            "M": 9,
            "L": 9
        }
    },
    {
        "id": 3,
        "name": "RAISED PRINT T-SHIRT",
        "salesUnits": 80,
        "stockBySize": {
            "S": 20,
            "M": 2,
            "L": 20
        }
    },
    {
        "id": 6,
        "name": "SLOGAN T-SHIRT",
        "salesUnits": 20,
        "stockBySize": {
            "S": 9,
            "M": 2,
            "L": 5
        }
    },
    {
        "id": 1,
        "name": "V-NECH BASIC SHIRT",
        "salesUnits": 100,
        "stockBySize": {
            "S": 4,
            "M": 9,
            "L": 0
        }
    },
    {
        "id": 5,
        "name": "CONTRASTING LACE T-SHIRT",
        "salesUnits": 650,
        "stockBySize": {
            "S": 0,
            "M": 1,
            "L": 0
        }
    }
]
```