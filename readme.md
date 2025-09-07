# GlobalBooks Inc: A Service-Oriented Architecture (SOA) Implementation

This project is a practical implementation of Service-Oriented Architecture, demonstrating the decomposition of a traditional monolithic application for "GlobalBooks Inc." into a collection of independent, maintainable, and scalable services.

## 🏛️ System Architecture

The architecture consists of several decoupled services that communicate through standardized contracts and messaging queues. This design improves agility, allowing individual components to be developed, deployed, and scaled independently.

- **`CatalogService` (SOAP):** A traditional, contract-first web service for managing the product catalog.
- **`OrdersService` (REST):** A modern, lightweight RESTful API for handling customer orders.
- **`PaymentsService` & `ShippingService` (Async Messaging):** Two decoupled services that communicate asynchronously via a message broker, ensuring resilience and scalability.
- **BPEL Engine (Apache ODE):** An orchestration layer designed to manage complex, long-running business processes that involve multiple service calls.



---

## ✨ Key Features & Concepts Implemented

- **Loose Coupling:** Services like `Payments` and `Shipping` are completely decoupled using **RabbitMQ**, ensuring that a failure in one does not directly impact the other.
- **Standardized Contracts:** Clear separation of interface and implementation using **WSDL** for SOAP services and **OpenAPI/JSON Schema** for REST services.
- **Resilience:** A **Dead-Letter Queue (DLQ)** strategy is implemented to handle message processing failures gracefully, preventing data loss.
- **Governance:** A clear policy for **Versioning**, **SLAs** (99.5% uptime, <200ms response), and **Deprecation** is defined to ensure the long-term maintainability of the system.

---

## 🛠️ Technology Stack

- **Backend:** Java 8/11
- **Build Tool:** Apache Maven
- **SOAP Service:** JAX-WS (CXF)
- **REST Service:** Spring Boot
- **Messaging:** RabbitMQ (via CloudAMQP)
- **Orchestration:** BPEL (Apache ODE on Tomcat)
- **Database:** (e.g., H2 In-memory, PostgreSQL)

---

## 🚀 Getting Started

Follow these instructions to get the project running on your local machine.

### Prerequisites

- **Java Development Kit (JDK):** Version 8 or higher.
- **Apache Maven:** To build the projects and manage dependencies.
- **CloudAMQP Account:** A free-tier RabbitMQ instance from [CloudAMQP](https://www.cloudamqp.com/).
- **Postman:** To test the API endpoints.

### Configuration

The only configuration required is for the message queue.

1.  Sign up for a CloudAMQP account and create a free instance.
2.  Copy the **AMQP URL** from your CloudAMQP dashboard.
3.  In both the `PaymentsService` and `ShippingService` projects, open the `ShippingQueueProducer.java` and `ShippingQueueConsumer.java` files respectively.
4.  Paste your AMQP URL into the `amqpUrl` variable:
    ```java
    String amqpUrl = "YOUR_CLOUDAMQP_URL_HERE";
    ```

### Running the Services

Each service is an independent project and must be run separately.

1.  **`CatalogService` (SOAP):**
    - Navigate to the `CatalogService` project folder.
    - Run the `main` method in the `CatalogPublisher.java` class.
    - The service will be available at: `http://localhost:9999/ws/catalog`

2.  **`OrdersService` (REST):**
    - Navigate to the `OrdersService` project folder.
    - Run the main application class (the one with the `@SpringBootApplication` annotation).
    - The service will be available at: `http://localhost:8080/api/v1/orders`

3.  **`ShippingService` (Consumer):**
    - Navigate to the `ShippingService` project folder.
    - Run the `main` method in the `Main.java` class.
    - The console will display: `[*] Waiting for messages...`

4.  **`PaymentsService` (Producer):**
    - Navigate to the `PaymentsService` project folder.
    - Run the `main` method in the `Main.java` class to simulate a successful payment and send a message.

---

## 🧪 How to Test

- **`CatalogService`:**
  - Open Postman and create a new SOAP request.
  - Set the URL to `http://localhost:9999/ws/catalog`.
  - Use the following XML in the body to test the `getProductDetails` operation:
    ```xml
    <soapenv:Envelope xmlns:soapenv="[http://schemas.xmlsoap.org/soap/envelope/](http://schemas.xmlsoap.org/soap/envelope/)" xmlns:cat="[http://catalog.yourcompany.com/](http://catalog.yourcompany.com/)">
       <soapenv:Header/>
       <soapenv:Body>
          <cat:getProductDetails>
             <arg0>p001</arg0>
          </cat:getProductDetails>
       </soapenv:Body>
    </soapenv:Envelope>
    ```

- **`OrdersService`:**
  - Open Postman and create a new **POST** request.
  - Set the URL to `http://localhost:8080/api/v1/orders`.
  - In the `Body` tab, select `raw` and `JSON`, and paste the following:
    ```json
    {
        "customerId": "CUST-123",
        "items": [
            { "productId": "p001", "quantity": 1 }
        ]
    }
    ```

- **Asynchronous System Test:**
  1. Make sure the `ShippingService` is running and its console shows "Waiting for messages...".
  2. Run the `PaymentsService`.
  3. Immediately check the console of the `ShippingService`. You should see the `[x] Received '...'` message appear. This confirms the end-to-end message flow.
