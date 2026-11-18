# Spring Boot Mall

A RESTful e-commerce backend built with Spring Boot, Spring JDBC, and MySQL. It provides product management with filtering, sorting, and pagination, user registration and login, and order creation with stock validation, covered by integration tests that run against an in-memory H2 database.

Built while following the Hahow course *Java 工程師必備！Spring Boot 零基礎入門* (instructor: 古君葳).

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java 11 |
| Framework | Spring Boot 2.3.7 (Spring MVC, Spring JDBC, Bean Validation) |
| Database | MySQL 8 / 5.7 (runtime), H2 (tests) |
| Data Access | `NamedParameterJdbcTemplate` with custom `RowMapper`s |
| Testing | JUnit 5, MockMvc |
| Build Tool | Maven |

## Features

**Products**
- Create, read, update, and delete products
- Product list with filtering (category, keyword search), sorting (any column, ascending or descending), and pagination
- Paginated responses include the total count for page calculation
- Product categories modeled as an enum (`FOOD`, `CAR`, `E_BOOK`)

**Users**
- Registration with email format validation and duplicate-email checks
- Login with credential verification
- Passwords stored as hashes; the password field is excluded from JSON responses

**Orders**
- Create an order containing multiple items in a single request
- Validates that the user and every product exist, and that stock is sufficient
- Deducts stock and calculates per-item and total amounts at the time of purchase
- Order and order item inserts run in a single transaction (`@Transactional`)
- Order history per user, newest first, with pagination

## Architecture

The project follows a layered Controller → Service → DAO structure:

```
src/main/java/com/irischiu/springbootmall
├── controller   # REST endpoints, request validation
├── service      # Business logic (validation, price calculation, transactions)
├── dao          # SQL queries via NamedParameterJdbcTemplate
├── rowmapper    # ResultSet → model mapping
├── model        # Domain objects (Product, User, Order, OrderItem)
├── dto          # Request bodies and query parameter objects
├── constant     # Enums
└── util         # Generic Page<T> response wrapper
```

## API Endpoints

### Products

| Method | Endpoint | Description | Success |
|---|---|---|---|
| GET | `/products` | List products with filtering, sorting, and pagination | 200 |
| GET | `/products/{productId}` | Get a product by ID | 200 / 404 |
| POST | `/products` | Create a product | 201 |
| PUT | `/products/{productId}` | Update a product | 200 / 404 |
| DELETE | `/products/{productId}` | Delete a product | 204 |

Query parameters for `GET /products`:

| Parameter | Default | Description |
|---|---|---|
| `category` | – | `FOOD`, `CAR`, or `E_BOOK` |
| `search` | – | Keyword matched against the product name |
| `orderBy` | `created_date` | Column to sort by |
| `sort` | `desc` | `asc` or `desc` |
| `limit` | `5` | Page size (0–1000) |
| `offset` | `0` | Number of records to skip |

Example: `GET /products?category=CAR&search=B&orderBy=price&sort=asc&limit=10&offset=0`

### Users

| Method | Endpoint | Description | Success |
|---|---|---|---|
| POST | `/users/register` | Register a new user | 201 |
| POST | `/users/login` | Log in with email and password | 200 |

```json
{
  "email": "test@gmail.com",
  "password": "123"
}
```

### Orders

| Method | Endpoint | Description | Success |
|---|---|---|---|
| POST | `/users/{userId}/orders` | Create an order for a user | 201 |
| GET | `/users/{userId}/orders` | List a user's orders (`limit` default 10, `offset` default 0) | 200 |

```json
{
  "buyItemList": [
    { "productId": 1, "quantity": 2 },
    { "productId": 3, "quantity": 1 }
  ]
}
```

Paginated list endpoints return:

```json
{
  "limit": 5,
  "offset": 0,
  "total": 7,
  "results": [ ... ]
}
```

Invalid input (missing fields, unknown category, duplicate email, wrong password, nonexistent user or product, insufficient stock) returns `400 Bad Request`.

## Database Schema

| Table | Columns |
|---|---|
| `product` | `product_id` (PK), `product_name`, `category`, `image_url`, `price`, `stock`, `description`, `created_date`, `last_modified_date` |
| `user` | `user_id` (PK), `email` (unique), `password`, `created_date`, `last_modified_date` |
| `order` | `order_id` (PK), `user_id`, `total_amount`, `created_date`, `last_modified_date` |
| `order_item` | `order_item_id` (PK), `order_id`, `product_id`, `quantity`, `amount` |

`order` and `order_item` form a one-to-many relationship. Product names and images are retrieved with a `JOIN` on `product_id`, while `amount` and `total_amount` store the price paid at the time of purchase so that later price changes do not affect past orders.

## Getting Started

### Prerequisites

- JDK 11
- Maven
- MySQL

### 1. Create the database

```sql
CREATE DATABASE mall;
USE mall;

CREATE TABLE product
(
    product_id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       VARCHAR(128) NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       DATETIME     NOT NULL,
    last_modified_date DATETIME     NOT NULL
);

CREATE TABLE user
(
    user_id            INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL UNIQUE KEY,
    password           VARCHAR(256) NOT NULL,
    created_date       DATETIME     NOT NULL,
    last_modified_date DATETIME     NOT NULL
);

CREATE TABLE `order`
(
    order_id           INT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id            INT      NOT NULL,
    total_amount       INT      NOT NULL,
    created_date       DATETIME NOT NULL,
    last_modified_date DATETIME NOT NULL
);

CREATE TABLE order_item
(
    order_item_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id      INT NOT NULL,
    product_id    INT NOT NULL,
    quantity      INT NOT NULL,
    amount        INT NOT NULL
);
```

Sample product data is available in [`src/test/resources/data.sql`](src/test/resources/data.sql).

### 2. Configure the connection

Update `src/main/resources/application.properties` with your MySQL host, port, username, and password:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mall?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Taipei&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Run the application

```bash
mvn spring-boot:run
```

The API is available at `http://localhost:8080`. Example requests are provided in [`product-api.http`](product-api.http).

## Testing

```bash
mvn test
```

The test suite contains 29 MockMvc integration tests across `ProductControllerTest`, `UserControllerTest`, and `OrderControllerTest`. Tests run against an in-memory H2 database (MySQL mode) initialized from `src/test/resources/schema.sql` and `data.sql`, so no MySQL instance is required. Tests that modify data are annotated with `@Transactional` and roll back after each run.

## Known Limitations and Possible Improvements

- **Authentication**: endpoints are not protected; adding token-based authentication (e.g. Spring Security with JWT) and role-based access control would restrict product management to administrators.
- **Password hashing**: passwords are hashed with MD5. A salted algorithm such as BCrypt is recommended for production use.
- **Sorting input**: `orderBy` and `sort` are concatenated into SQL; validating them against an allowlist of column names would prevent SQL injection.
- **Concurrency**: stock checks and deductions are not locked, so simultaneous orders for the same product could oversell.
- **Currency**: prices are stored as integers; `DECIMAL` would support fractional amounts.
- **Search**: keyword search uses SQL `LIKE`; a full-text search engine such as Elasticsearch would scale better.