# Python Examples for Nkwa Pay SDK

This directory contains example implementations of the Nkwa Pay SDK using popular Python web frameworks.

## Examples

### Flask Example
A synchronous implementation using Flask web framework.

Setup:
```bash
cd flask-example
python -m venv venv
source venv/bin/activate  # On Windows use: venv\Scripts\activate
pip install -r requirements.txt
export PAY_API_KEY_AUTH=your_api_key_here
python app.py
```

The server will start at http://localhost:5000

### FastAPI Example
An asynchronous implementation using FastAPI framework.

Setup:
```bash
cd fastapi-example
python -m venv venv
source venv/bin/activate  # On Windows use: venv\Scripts\activate
pip install -r requirements.txt
export PAY_API_KEY_AUTH=your_api_key_here
uvicorn main:app --reload
```

The server will start at http://localhost:8000

## API Endpoints

Both implementations provide the following endpoints:

1. Collect Payment (POST `/collect-payment`):
```json
{
    "amount": 1000,
    "phoneNumber": "+237600000000",
    "currency": "XAF",
    "description": "Test payment"
}
```

2. Disburse Payment (POST `/disburse-payment`):
```json
{
    "amount": 1000,
    "phoneNumber": "+237600000000",
    "currency": "XAF",
    "description": "Test disbursement"
}
```

3. Get Payment Status (GET `/payment/{payment_id}`)