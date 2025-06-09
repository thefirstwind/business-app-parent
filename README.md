# MCP Business Application

This is a multi-module Spring Boot application with the following components:

## Modules

1. **User Center (userCenter)** - User management and authentication service
   - Port: 8080
   - Database: usercenter
   - Dubbo Port: 20880

2. **Trade Center (tradeCenter)** - Order and transaction management service
   - Port: 8081
   - Database: tradecenter
   - Dubbo Port: 20881

3. **Logistics Center (lgCenter)** - Logistics and shipping management service
   - Port: 8082
   - Database: lgcenter
   - Dubbo Port: 20882

4. **Item Center (itemCenter)** - Product catalog and inventory management service
   - Port: 8083
   - Database: itemcenter
   - Dubbo Port: 20883

5. **Payment Gateway (paygw)** - Payment processing service
   - Port: 8084
   - Database: paygw
   - Dubbo Port: 20884

## Technologies

- Java 8
- Spring Boot 1.5.22.RELEASE
- MyBatis 1.3.5
- MySQL 5.1.49
- Dubbo 2.7.15
- Logback

## Setup

### Prerequisites

- Java 8 or higher
- Maven 3.6 or higher
- MySQL 5.7 or higher

### Database Setup

Run the database setup script to create all required databases:

```bash
./setup_databases.sh
```

### Build

Build the project using Maven:

```bash
mvn clean package
```

### Run

You can run each module separately:

```bash
java -jar userCenter/target/userCenter-1.0.0-SNAPSHOT.jar
java -jar tradeCenter/target/tradeCenter-1.0.0-SNAPSHOT.jar
java -jar lgCenter/target/lgCenter-1.0.0-SNAPSHOT.jar
java -jar itemCenter/target/itemCenter-1.0.0-SNAPSHOT.jar
java -jar paygw/target/paygw-1.0.0-SNAPSHOT.jar
```

Or run them using Spring Boot Maven plugin:

```bash
cd userCenter && mvn spring-boot:run
cd tradeCenter && mvn spring-boot:run
cd lgCenter && mvn spring-boot:run
cd itemCenter && mvn spring-boot:run
cd paygw && mvn spring-boot:run
```

## API Documentation

Each service exposes RESTful APIs:

- User Center: http://localhost:8080/
- Trade Center: http://localhost:8081/
- Logistics Center: http://localhost:8082/
- Item Center: http://localhost:8083/
- Payment Gateway: http://localhost:8084/

## Architecture

```
+----------------+    +----------------+    +----------------+
|   userCenter   |    |  tradeCenter   |    |    lgCenter    |
|   (8080/20880) |    |  (8081/20881)  |    |  (8082/20882)  |
+--------+-------+    +--------+-------+    +--------+-------+
         |                     |                     |
         +----------+----------+----------+----------+
                    |                     |
          +---------+---------+  +--------+--------+
          |    itemCenter     |  |      paygw      |
          |   (8083/20883)    |  |   (8084/20884)  |
          +-------------------+  +-----------------+
```

## Cross-Service Communication

Services communicate with each other using:
- Dubbo RPC for synchronous operations
- RESTful APIs over HTTP for simple operations
