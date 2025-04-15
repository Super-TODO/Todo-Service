# TO-DO

##  ERD Diagram
The Entity-Relationship Diagram (ERD) for the Todo Service, which includes the `items` and `items_details` tables.

```mermaid
erDiagram
    ITEMS ||--o{ ITEM_DETAILS : has

    ITEMS {
        bigint id PK "Auto-incremented"
        varchar title "Todo item title"
        bigint user_id FK "References user(id) in User Service"
        bigint item_details_id FK "References item_details(id)"
    }

    ITEM_DETAILS {
        bigint id PK "Auto-incremented"
        varchar description "Item description"
        datetime created_at "Creation timestamp"
        datetime modified_at "Last modified timestamp"
        enum priority "HIGH, MEDIUM, LOW"
        enum status "IN_PROGRESS, DONE, PENDING, CANCELLED"
    }

```


## What Is It?
The ToDo Service is a clean and reliable task management system. It lets you add, view, update, and delete tasks in a way that is easy to understand and use. It is part of a larger system that helps students and teachers manage learning tasks.


## Key Features
- **Full Task Management:** Handle all task actions like add, update, delete, and view.
- **Clear Data Plans (DTOs):** We use special objects called DTOs to send data in and out. This means the program is organized and changes inside don’t affect how the outside world sees the data.
- **Good Error Messages:** If there’s an error, a central system catches it and sends a clear message back.
- **Automatically Documented APIs:** The system automatically creates easy-to-read documentation (with Swagger) for all its functions.

## Problems I've Solved
- **Database Issue:**  
  *Problem:* A column in the database did not have a default value, causing errors when saving tasks.  
  *Solution:* I fixed the way my data was set up so that the database works correctly without missing values.

- **Version Mismatches:**  
  *Problem:* Some tools were not working well together because of version conflicts.  
  *Solution:* search at *Stackoverflow.com* to solve this problem, then I chose versions that work well together (like Spring Boot 3.4.1 and Springdoc OpenAPI 2.7.0) so everything runs smoothly.

- **Partial Updates:**  
  *Problem:* I neededto update only the parts of a task that have changed.  
  *Solution:* I built a separate data plan called `ItemUpdateDTO` so that only the provided fields are updated.

  
![Swagger UI Screenshot](https://github.com/Super-TODO/Todo-Service/blob/main/Swagger-ui.png)

