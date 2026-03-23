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

Querry:
* Table Room:
  INSERT INTO room_type (category, price, max_room_number) VALUES
  -- 1 người
  ('SINGLE_1P_STANDARD',  400000, 10),
  ('SINGLE_1P_DELUXE',    600000, 8),
  ('SINGLE_1P_SUITE',     900000, 5),

    -- 2 người
    ('DOUBLE_2P_STANDARD',  700000, 15),
    ('DOUBLE_2P_DELUXE',   1000000, 12),
    ('DOUBLE_2P_SUITE',    1500000, 8),
    
    -- 3 người
    ('TRIPLE_3P_STANDARD', 1100000, 10),
    ('TRIPLE_3P_DELUXE',   1500000, 8),
    ('TRIPLE_3P_SUITE',    2100000, 6),
    
    -- 4 người
    ('FAMILY_4P_STANDARD', 1600000, 8),
    ('FAMILY_4P_DELUXE',   2100000, 6),
    ('FAMILY_4P_SUITE',    2800000, 4);
    
    ;WITH Numbers AS (
    SELECT TOP 50 ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS num
    FROM sys.objects
    )
    INSERT INTO room (room_number, room_type_id, status)
    SELECT
    CONCAT(rt.id, RIGHT('00' + CAST(n.num AS VARCHAR(2)), 2)) AS room_number,
    rt.id,
    'AVAILABLE'
    FROM room_type rt
    JOIN Numbers n
    ON n.num <= rt.max_room_number
    ORDER BY rt.id, n.num;

* Table Food
  INSERT INTO food_type (category) VALUES
  ('APPETIZER'),
  ('MAIN_COURSE'),
  ('DESSERT'),
  ('DRINK');

    INSERT INTO food (food_name, number, price, food_type_id) VALUES
    ('Spring Rolls', 50, 50000, 1),
    ('Caesar Salad', 30, 75000, 1),
    ('Grilled Chicken', 40, 150000, 2),
    ('Beef Steak', 20, 220000, 2),
    ('Chocolate Cake', 25, 90000, 3),
    ('Fruit Salad', 20, 70000, 3),
    ('Orange Juice', 50, 45000, 4),
    ('Coffee', 60, 40000, 4);


# 🏨 Hotel Management System — API Documentation
## API Endpoints
## 👤 User APIs — `/api/users`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Create new user |
| POST | `/api/users/login` | User login (get JWT) |
| DELETE | `/api/users/{id}` | Delete user (ADMIN) |
| GET | `/api/users/customer` | Get all customers (ADMIN, STAFF) |
| GET | `/api/users/staff` | Get all staff (ADMIN, STAFF) |
| GET | `/api/users/me` | Get my profile |
| PUT | `/api/users/me` | Update my profile |

---

## 🏨 Room APIs — `/api/rooms`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/rooms` | Get all rooms |
| PUT | `/api/rooms/{id}/status?status=` | Update room status (ADMIN, STAFF) |
| GET | `/api/rooms/available?category=&checkIn=&checkOut=` | Find available rooms |

---

## 🛏️ Room Type APIs — `/api/room-types`

| Method | Endpoint | Description |
|--------|----------|-------------|
| PUT | `/api/room-types/{category}/price` | Update room type price (ADMIN) |
| GET | `/api/room-types/{category}` | Get rooms by category |

---

## 📅 Booking APIs — `/api/bookings`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/bookings` | Create booking (CUSTOMER) |
| GET | `/api/bookings/my` | Get my bookings (CUSTOMER) |
| PUT | `/api/bookings/{id}/status?status=` | Update booking status (ADMIN, STAFF) |
| PUT | `/api/bookings/checkin/{id}` | Customer check-in |
| PUT | `/api/bookings/checkout/{id}` | Customer check-out |
| GET | `/api/bookings` | Get all bookings (ADMIN, STAFF) |

---

## 🍽️ Food APIs — `/api/foods`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/foods` | Add new food (ADMIN, STAFF) |
| DELETE | `/api/foods/{id}` | Delete food (ADMIN, STAFF) |
| PUT | `/api/foods/{id}/increase?amount=` | Increase food quantity (ADMIN, STAFF) |
| GET | `/api/foods/category/{category}` | Get foods by category |
| PUT | `/api/foods/{id}/price?price=` | Update food price |

---

## 🧾 Invoice APIs — `/api/invoices`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/invoices/booking/{bookingId}/foods/{foodId}?quantity=` | Add food to invoice (order food) |
| GET | `/api/invoices/booking/{bookingId}/history` | Get ordered food history |

---

## 💳 Invoice Management APIs — `/api/invoice-management`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/invoice-management/booking/{bookingId}` | Get invoice by booking |
| POST | `/api/invoice-management/{invoiceId}/pay` | Pay invoice |

---

## 🔐 Roles
- **ADMIN**: Full access
- **STAFF**: Manage rooms, foods, bookings
- **CUSTOMER**: Booking, check-in/out, order food, view profile

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
