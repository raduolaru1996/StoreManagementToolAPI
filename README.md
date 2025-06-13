# StoreManagementToolAPI 

## Overview
This is a backend only store management system built with Spring Boot API for managing store inventory. The API supports registering users and roles with integrated JWT authentication and basic product operations. 

## Features
- User registration and jwt-based login and authentication.
- Product controller operations
    - `GET /api/v1/products` - List all products
    - `GET /api/v1/products/name?name=...` - List all products that contain input value
    - `POST /api/v1/products` - Add product (ADMIN role)
    - `PATCH /api/v1/products/{id}/name` - Change the name of product based on ID (ADMIN role)
    - `PATCH /api/v1/products/{id}/price` - Change the price of product based on ID (ADMIN role)
    - `PATCH /api/v1/products/{id}/isActive` - Change the isActive flag of product based on ID (ADMIN role)

- Product service operations
    - `addProduct()` - Add a new product
    - `getProducts()` - Get all products
    - `getProductById(Long)` - Get product by ID
    - `getProductsByName(String)` - Get all products that contain the entered string
    - `patchProductPrice(Long, Double)` - Update product price based on id
    - `patchProductName(Long, String)` - Update product name based on id
    - `patchProductIsActive(Long, Boolean)` - Update product isActive flag based on id

- User controller operations
    - `POST /api/v1/auth/register` - Register a new user
    - `POST /api/v1/auth/login` - Login user

- Authentication service operations
    - `register(AuthRequest)` - Register a new user based on AuthRequest
    - `login(AuthRequest)` - Login a new user based on AuthRequest



## Tech Stack
- Java 17
- Spring Boot
- Spring Security (JWT)
- H2 in memory db
- Maven

## Testing
- Unit tests
- H2 used for repository testing
- Automated integration testing

## Authentication
### Register
```
POST /api/v1/auth/register
Content-Type: application/json

{
    "username": "username",
    "password": "password",
    "role": "ADMIN"
}
```

### Login
```
POST /api/v1/auth/login
Content-Type: application/json

{
    "username": "username",
    "password": "password"
}
```

Both return a `jwt token` that you can use with
`Authentication: Bearer <token>`

## Product operations
### GET
```
GET /api/v1/products
```
### POST
```
POST /api/v1/products
Authorization: Bearer <token>
Content-Type: application/json

{
    "name": "Monitor",
    "description": "budget-friendly",
    "price": 53.29,
    "quantity": 20,
    "isActive": true
}
```
### PATCH
```
PATCH /api/v1/products/{id}/price
Authorization: Bearer <token>
Content-Type: application/json

{
    "price": 1234.5
}
```

## Logging
Logs are written using `SLF4J` for product service operations, authentication service operations and global error handling.

## Java17 Feature used: Sealed Class