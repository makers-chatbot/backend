# Makers Tech Backend

A Spring Boot backend service for the Makers Tech e-commerce platform.

## 🚀 Features

- Product management with category-based organization
- User authentication and authorization
- Shopping cart functionality
- Order and delivery certificate management
- Customer relationship management (CRM) with interaction tracking
- Company and department management

## 🛠️ Tech Stack

- Java 17
- Spring Boot
- Spring Security
- MongoDB (for products and CRM)
- PostgreSQL (for relational data)
- Gradle
- Docker

## 📋 Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- MongoDB
- PostgreSQL

## 🔧 Environment Setup

1. Clone the repository
2. Create a `.env` file in the root directory with the following variables:
```env
POSTGRES_USER=your_postgres_user
POSTGRES_PASSWORD=your_postgres_password
POSTGRES_DB=your_database_name
```

## 🏃‍♂️ Running the Application

### Using Docker (Recommended)

1. Build and start the containers:
```bash
docker-compose -f docker-compose.dev.yml up -d
```

2. The application will be available at `http://localhost:8080`

### Local Development

1. Start MongoDB and PostgreSQL services
2. Run the application:
```bash
./gradlew bootRun
```

## 📚 API Documentation

### Categories
- `GET /api/categories` - Get all categories
- `POST /api/categories` - Create a new category (Admin only)
- `DELETE /api/categories/{id}` - Delete a category (Admin only)

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get a specific product
- `GET /api/products?category={category}` - Get products by category
- `POST /api/products` - Create a new product
- `DELETE /api/products/{id}` - Delete a product

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Contracts
- `GET /api/contracts` - Get all contracts
- `GET /api/contracts/{id}` - Get a specific contract
- `POST /api/contracts` - Create a new contract

### Delivery Certificates
- `GET /api/deliveryCertificates` - Get all delivery certificates
- `GET /api/deliveryCertificates/{contractId}` - Get certificates by contract
- `POST /api/deliveryCertificates` - Create a new delivery certificate

## 🗄️ Database Schema

### PostgreSQL (Relational)
- Equipment Categories
- Users and Roles
- Companies and Departments
- Contracts
- Delivery Certificates

### MongoDB (NoSQL)
- Products with detailed specifications
- Customer interactions and preferences

## 👥 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
