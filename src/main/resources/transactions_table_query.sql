CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    timestamp TIMESTAMP,
    description VARCHAR(255),
    amount NUMERIC,
    currency VARCHAR(3)
);
