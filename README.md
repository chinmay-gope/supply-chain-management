
# AI-Powered Smart Inventory & Supply Chain Management System

An intelligent inventory and supply chain management platform built with Java Spring Boot, MySQL, and AI integration.

The system helps businesses manage products, suppliers, purchases, sales, and stock movements while using AI-powered features to forecast demand, optimize inventory, and generate purchase recommendations.

---

## Features

### Core Features

- Product Management
- Category Management
- Supplier Management
- Purchase Order Management
- Sales Management
- Stock-In and Stock-Out Transactions
- Inventory Dashboard
- Low-Stock Alerts
- Sales Reports
- Inventory Reports

### AI-Powered Features

- **Demand Forecasting:** Predict future product demand using historical sales data.
- **Stock Optimization:** Identify inventory shortages and excess stock.
- **Purchase Recommendations:** Generate recommended purchase quantities.
- **Natural Language Inventory Analytics:** Ask questions about inventory using conversational AI.

---

## Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Backend framework |
| Spring Data JPA | Database operations |
| Spring Security | Authentication and authorization |
| JWT | Token-based authentication |
| MySQL | Relational database |
| Maven | Dependency management |
| Hibernate | ORM |
| REST API | Client-server communication |
| Gemini / OpenAI-compatible API | AI integration |

---

## Project Architecture

```text
src/main/java/com/inventory
│
├── config
│   └── SecurityConfig.java
│
├── controller
│   ├── AuthController.java
│   ├── ProductController.java
│   ├── SupplierController.java
│   ├── PurchaseOrderController.java
│   ├── SalesController.java
│   ├── ReportController.java
│   ├── AnalyticsController.java
│   └── AI Controllers
│
├── dto
│   ├── request
│   └── response
│
├── entity
│   ├── User.java
│   ├── Product.java
│   ├── Category.java
│   ├── Supplier.java
│   ├── ProdSupplier.java
│   ├── StockTransaction.java
│   ├── PurchaseOrder.java
│   ├── PurchaseOrderItem.java
│   ├── Sale.java
│   ├── SaleItem.java
│   ├── DemandForecast.java
│   └── PurchaseRecommendation.java
│
├── repository
│
├── service
│
├── security
│   ├── JwtService.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
│
└── InventoryApplication.java
```

*Note: This is the intended architecture overview. Adjust it to match the actual project packages and files.*

---

## Database

Database: MySQL

Main entities:

1. Users
2. Categories
3. Products
4. Suppliers
5. Product Suppliers
6. Stock Transactions
7. Purchase Orders
8. Purchase Order Items
9. Sales
10. Sale Items
11. Demand Forecasts
12. Purchase Recommendations

---

## Authentication and Authorization

The application uses Spring Security and JWT authentication.

### User Roles

| Role | Access |
|---|---|
| ADMIN | Full system access |
| INVENTORY_MANAGER | Inventory and product management |
| PURCHASE_MANAGER | Supplier and purchase management |
| SALES_MANAGER | Sales management |
| STAFF | Permitted operational functions |

### Security Features

- JWT-based authentication
- BCrypt password encryption
- Role-Based Access Control (RBAC)
- Protected REST endpoints
- Account status validation
- Stateless session management

---

## Getting Started

### Prerequisites

Install the following:

- Java 21 or compatible configured project JDK
- MySQL 8+
- Git
- IntelliJ IDEA (recommended)

Check Java installation:

```bash
java -version
```

Check Maven wrapper:

```bash
./mvnw -version
```

On Windows PowerShell:

```powershell
.\mvnw.cmd -version
```

### 1. Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

Navigate to the project:

```bash
cd <PROJECT_DIRECTORY>
```

### 2. Create the Database

Open MySQL and execute:

```sql
CREATE DATABASE inventory_db;
```

### 3. Configure Environment Variables

Configure these environment variables in IntelliJ IDEA or your operating system.

| Variable | Description |
|---|---|
| DB_URL | MySQL connection URL |
| DB_USERNAME | MySQL username |
| DB_PASSWORD | MySQL password |
| JWT_SECRET | Base64-encoded JWT signing secret |
| AI_API_KEY | AI provider API key, if used |

If using Gemini through an OpenAI-compatible Spring AI client, configure the appropriate Gemini API key variable expected by your application.

Example values:

```properties
DB_URL=jdbc:mysql://localhost:3306/inventory_db
DB_USERNAME=root
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_base64_encoded_secret
AI_API_KEY=your_ai_api_key
```

Never commit real credentials or secret keys to GitHub.

### 4. Configure application.yml

Example configuration:

```yaml
spring:
  application:
    name: smart-inventory

  datasource:
    url: ${DB_URL:jdbc:mysql://localhost:3306/inventory_db}
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

app:
  jwt:
    secret: ${JWT_SECRET}
    expiration: 86400000
```

Adapt the configuration to your actual application properties and AI provider setup.

### 5. Build the Application

Windows:

```powershell
.\mvnw.cmd clean install
```

Linux / macOS:

```bash
./mvnw clean install
```

### 6. Run the Application

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Linux / macOS:

```bash
./mvnw spring-boot:run
```

The backend will start at:

```text
http://localhost:8080
```

---

## API Modules

| Module | Endpoint |
|---|---|
| Authentication | `/api/auth` |
| Products | `/api/products` |
| Categories | `/api/categories` |
| Suppliers | `/api/suppliers` |
| Purchase Orders | `/api/purchase-orders` |
| Sales | `/api/sales` |
| Reports | `/api/reports` |
| Analytics | `/api/analytics` |
| Demand Forecasting | `/api/ai/forecast` |
| Stock Optimization | `/api/ai/optimization` |
| Purchase Recommendations | `/api/ai/purchase-recommendations` |
| AI Chat | `/api/ai/chat` |

### JWT Authorization

For protected endpoints, include the JWT token in the request header:

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

---

## Team Contributions

The project is developed collaboratively with separate responsibilities.

| Member | Responsibility |
|---|---|
| Member 4 | Category, Product, ProdSupplier, StockTransaction, Supplier |
| Member 5 | PurchaseOrder, PurchaseOrderItem, Sale, SaleItem, DemandForecast, PurchaseRecommendation and related APIs |

Other team responsibilities can be added here.

---

## Git Workflow

Recommended collaboration workflow:

```bash
# Get latest changes
git pull origin main

# Create a feature branch
git checkout -b feature/your-feature

# Stage changes
git add .

# Commit changes
git commit -m "feat: implement your feature"

# Push branch
git push -u origin feature/your-feature
```

Create a Pull Request to merge completed features into `main`.

Avoid pushing unfinished or untested code directly to `main`.

---

## Future Enhancements

- Advanced machine learning demand forecasting
- Automated supplier selection
- Real-time inventory notifications
- Inventory trend visualization
- Sales performance analytics
- Multi-warehouse inventory management
- Cloud deployment
- CI/CD pipeline
- Frontend dashboard integration

---

## Security Notes

- Never expose database credentials or API keys.
- Never store plain-text passwords.
- Validate all incoming API requests.
- Restrict endpoints according to user roles.
- Use HTTPS in production.
- Generate a strong JWT signing secret.
- Do not allow public registration to assign ADMIN privileges.

---

## License

This project is developed for educational and demonstration purposes.

Add your chosen open-source license here if you intend to distribute the project publicly.

---

**Built with Java, Spring Boot, MySQL, Spring Security, and AI integration.**
