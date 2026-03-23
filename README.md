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
