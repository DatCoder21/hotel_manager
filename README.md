# Artisan Lakeview Hotel Management System

A full‑stack hotel management system designed for **room booking, food ordering, invoicing, and administration**. The system supports role‑based access (Admin, Staff, Customer) and provides REST APIs for web/mobile integration.

---

## Key Features

### Authentication & Users

* User registration
* Login with authentication
* Role-based authorization (ADMIN / STAFF / CUSTOMER)
* Manage users (view lists, delete users)

### Room Management

* View room list
* Filter rooms by category
* Update room status (Available / Occupied / Maintenance)
* Adjust room type pricing

### Bookingnagement

* Customers can book rooms
* Admin/Staff can update booking status
* Customers can view booking history
* Track check-in / check-out dates
* Auto calculate total price

### Foodrvice

* Add food items
* Classify food by category
* Update food quantity
* Delete food items
* Customers can order food with booking

### InvoicePayment

* Generate invoice from booking
* Include ordered food in invoice items
* Customers can view invoices
* Customers can pay invoices

---

## Database Design

Main entities:

* **User** — system accounts
* **RoomType** — room categories and base price
* **Room** — hotel rooms
* **Booking** — room reservations
* **FoodType** — food categories
* **Food** — food menu items
* **Invoice** — payment summary
* **InvoiceItem** — food items in invoice

Relationships:

* One RoomType → Many Rooms
* One User → Many Bookings
* One Room → Many Bookings
* One Booking → One Invoice
* One Invoice → Many InvoiceItems
* One FoodType → Many Food

---

## API Endpoints

### Auth

| Method | Endpoint | Description        |
| ------ | -------- | ------------------ |
| POST   | Register | Create new account |
| POST   | Login    | Authenticate user  |

### Rooms

| Method | Endpoint              | Description              |
| ------ | --------------------- | ------------------------ |
| GET    | GetListRoom           | List all rooms           |
| GET    | GetListRoomByCategory | Filter rooms by category |
| PUT    | Update Room Status    | Change room status       |
| PUT    | FixRoomPrice          | Update room type price   |

### 📅 Booking

| Method | Endpoint                      | Description               |
| ------ | ----------------------------- | ------------------------- |
| POST   | Booking Room                  | Customer books room       |
| PUT    | Admin Edit Booking Status     | Staff/Admin update status |
| GET    | See My Booking                | Customer bookings         |
| GET    | Customer View Booking History | Booking history           |

### 🍽 Food

| Method | Endpoint              | Description       |
| ------ | --------------------- | ----------------- |
| POST   | Add Food              | Add new food item |
| GET    | GetListFoodByCategory | Filter food       |
| PUT    | Add Food Number       | Update quantity   |
| DEL    | Delete Food           | Remove food item  |
| POST   | Customer Booking Food | Order food        |

### 🧾 Invoice

| Method | Endpoint              | Description          |
| ------ | --------------------- | -------------------- |
| GET    | Customer View Invoice | View invoice details |
| POST   | Customer Pay Invoice  | Pay invoice          |

### Users

| Method | Endpoint        | Description    |
| ------ | --------------- | -------------- |
| GET    | GetListCustomer | List customers |
| GET    | GetListStaff    | List staff     |
| DEL    | Delete Users    | Remove user    |

---

## Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* JPA / Hibernate
* SQL Server
* Maven

### Tools

* Postman (API testing)
* IntelliJ IDEA
* Git

---

## Roles & Permissions

| Role     | Permissions                          |
| -------- | ------------------------------------ |
| ADMIN    | Full system control                  |
| STAFF    | Manage rooms, bookings               |
| CUSTOMER | Book rooms, order food, pay invoices |

---

## 📄 License

This project is for educational purposes and subject Project 2 by Group 8.
