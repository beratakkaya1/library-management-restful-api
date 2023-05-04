# Library Management API

This is a simple RESTful API for managing a library using the Java Spring Boot framework. The API provides basic CRUD (Create, Read, Update, Delete) operations for books and authors. Each book has a title, publication year, and an author, while each author has a name and a list of their books.

## Technologies used

- Java 11
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Swagger

## Getting started

### Prerequisites

- JDK 11
- Maven
- PostgreSQL

### Configuration

1. Install PostgreSQL and create a new database named `library`.
2. Update `src/main/resources/application.properties` with your PostgreSQL credentials and URL.
   spring.datasource.url=jdbc:postgresql://localhost:5432/library
   spring.datasource.username=your_postgres_username       
   spring.datasource.password=your_postgres_password


### Building the project

To build the project, run the following command from the project's root directory:
- mvn clean install
### Running the project

To run the project, execute the following command from the project's root directory:
- mvn spring-boot:run


The API will be accessible at `http://localhost:8080/api/`.

### Swagger UI

To access the Swagger UI, visit `http://localhost:8080/swagger-ui.html` in your browser after running the project. You can use the Swagger UI to explore and test the API endpoints.

## API Endpoints

### Authors

- `POST /api/authors`: Create a new author.
- `GET /api/authors`: Get a list of all authors.
- `GET /api/authors/{id}`: Get an author by their ID.
- `PUT /api/authors/{id}`: Update an author's information.
- `DELETE /api/authors/{id}`: Delete an author.

### Books

- `POST /api/books`: Create a new book.
- `GET /api/books`: Get a list of all books.
- `GET /api/books/{id}`: Get a book by its ID.
- `PUT /api/books/{id}`: Update a book's information.
- `DELETE /api/books/{id}`: Delete a book.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
