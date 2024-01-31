# Kirana Register API Documentation

## Endpoints

### 1. Record Transaction

- **Endpoint:** `/transactions/record`
- **Description:** Record a new transaction.
    - Method: `POST`
    - URL: `http://localhost:8080/transactions/record`
    - Body:
      ```json
      {
        "timestamp": "2024-01-31T12:30:00",
        "description": "Sample Transaction",
        "amount": 100.0,
        "currency": "INR",
        "transactionType": "credit"
      }
      ```
- **Response:**
    - `200 OK`: Transaction recorded successfully.
    - `500 Internal Server Error`: Error recording transaction.

### 2. Get Daily Transactions

- **Endpoint:** `/transactions/daily-report`
- **Description:** Get daily transactions details for a specific date.
    - Method: `GET`
    - URL: `http://localhost:8080/transactions/daily-report?date=2024-01-31`
    - Query Parameter: `date` (ISO date format)
- **Response:**
    - `200 OK`: List of daily transactions retrieved successfully.
    - `500 Internal Server Error`: Error fetching daily transactions.
