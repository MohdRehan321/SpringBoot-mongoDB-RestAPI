# SpringBoot MongoDB RestAPI

A robust Java project using Spring Boot for building RESTful APIs with seamless MongoDB integration. This repository demonstrates best practices for developing scalable backend services, making it ideal for beginners, professionals, and recruiters seeking clean, production-ready code.

## ✨ Key Features

- RESTful API endpoints for resource management
- MongoDB integration using Spring Data
- Robust error handling and validation
- Modular folder structure for easy navigation
- Environment variable support for secure configuration
- Simple setup and deployment

## 🛠️ Tech Stack

- **Java 11+**
- **Spring Boot**
- **Spring Data MongoDB**
- **MongoDB**
- **Maven**

## 🏁 Installation & Run Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/MohdRehan321/SpringBoot-mongoDB-RestAPI.git
   cd SpringBoot-mongoDB-RestAPI
   ```

2. **Set up MongoDB:**
   - Ensure MongoDB is running locally (`mongodb://localhost:27017`) or configure your own URI.

3. **Configure environment variables:**
   - Create an `.env` or update `application.properties`:
     ```
     spring.data.mongodb.uri=mongodb://localhost:27017/yourdbname
     ```

4. **Build and run the project:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access API:**
   - Visit `http://localhost:8080/api/{resource}` in your browser or API client.

## 📦 Sample API Response

```json
{
  "id": "64f0bfc8f1a2b7e6c8a2d1f0",
  "name": "Sample Resource",
  "description": "A sample entry stored in MongoDB."
}
```

## 🗂️ Folder Structure

```
SpringBoot-mongoDB-RestAPI/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/dev/rehan/springbootmongodbrestapi/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── model/
│   │   │       └── repository/
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

## 📄 License

This project is licensed under the [MIT License](LICENSE).

## 👤 Author

Mohd Rehan
