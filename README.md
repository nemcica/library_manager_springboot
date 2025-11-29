# Library Management System (Java / Spring Boot / Maven / Docker)

A backend‑only library management application built with Spring Boot and Maven, containerized with Docker.  
No frontend — the API is tested and used via HTTP clients (e.g. Postman).

---

## Features

- Full CRUD operations on **books** and **users**  
- Borrowing system: users can borrow and return books, renew membership  
- Business‑logic constraints:  
  - Borrow limit per user  
  - No borrowing if membership expired or user is overdue with a returned book  
- Containerized deployment using Docker (images, containers, volumes) for easy setup  

---

## Tech Stack

- Java  
- Spring Boot  
- Maven  
- Docker / Docker Compose  
- RESTful API interface (tested via Postman)  

---

## Getting Started (Run via Docker)

1. Clone the repository:  
   git clone https://github.com/nemcica/library_manager_springboot.git
   cd library_manager_springboot
2. Build and run with Docker Compose:
   docker-compose up --build
