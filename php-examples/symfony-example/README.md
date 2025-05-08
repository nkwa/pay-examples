# Symfony Example for Nkwa Pay SDK

This example demonstrates how to integrate the Nkwa Pay SDK with Symfony.

## Setup

1. Install dependencies:
```bash
composer install
```

2. Configure your API key in `.env.local`:
```
PAY_API_KEY_AUTH=your_api_key_here
```

3. Start the server:
```bash
symfony serve
```

## API Endpoints

The following endpoints are available:

- `POST /collect-payment` - Collect a payment
  ```json
  {
    "amount": 1000,
    "phoneNumber": "+237600000000"
  }
  ```

- `POST /disburse-payment` - Disburse a payment
  ```json
  {
    "amount": 1000,
    "phoneNumber": "+237600000000"
  }
  ```

- `GET /payment/{id}` - Check payment status