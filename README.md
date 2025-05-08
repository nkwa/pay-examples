# Nkwa Pay Integration Examples

This repository contains example implementations demonstrating how to integrate the Nkwa Pay API across various programming languages and frameworks.

## Overview

The examples showcase best practices for integrating Nkwa Pay's payment processing capabilities using popular web frameworks and technologies.

## Available Examples

### Go

- Echo framework implementation
- Gin framework implementation

### JavaScript/TypeScript

- Express.js implementation
- NestJS implementation

### PHP

- Laravel implementation
- Symfony implementation

### Python

- FastAPI implementation
- Flask implementation

## Prerequisites

Before running any example, make sure you have:

- A Nkwa Pay API key
- The required runtime for your chosen example:
  - Python 3.7+ for Python examples
  - Node.js 14+ for JavaScript examples
  - PHP 8.2+ for PHP examples
  - Go 1.20+ for Go examples

## Running the Examples

### Python Examples

#### FastAPI Example

```bash
cd python-examples/fastapi-example
python3 -m venv venv
source venv/bin/activate  # On Windows use: venv\Scripts\activate
pip3 install -r requirements.txt
export PAY_API_KEY_AUTH=your_api_key_here
uvicorn main:app --reload
```

Server runs at <http://localhost:8000>

#### Flask Example

```bash
cd python-examples/flask-example
python3 -m venv venv
source venv/bin/activate  # On Windows use: venv\Scripts\activate
pip3 install -r requirements.txt
export PAY_API_KEY_AUTH=your_api_key_here
python3 app.py
```

Server runs at <http://localhost:8001>

### JavaScript Examples

#### Express.js Example

```bash
cd javascript-examples/express-example
npm install
export PAY_API_KEY_AUTH=your_api_key_here
npm run dev
```

Server runs at <http://localhost:3000>

#### NestJS Example

```bash
cd javascript-examples/nestjs-example
npm install
export PAY_API_KEY_AUTH=your_api_key_here
npm run start:dev
```

Server runs at <http://localhost:3001>

### PHP Examples

#### Laravel Example

```bash
cd php-examples/laravel-example
composer install
cp .env.example .env
# Edit .env and set PAY_API_KEY_AUTH=your_api_key_here
php artisan key:generate
php artisan serve
```

Server runs at <http://localhost:8000>

#### Symfony Example

```bash
cd php-examples/symfony-example
composer install
# Create .env and set PAY_API_KEY_AUTH=your_api_key_here
symfony serve or php -S localhost:8000 -t public/
```

Server runs at <http://localhost:8000>

### Go Examples

#### Echo Example

```bash
cd go-examples/echo-example
go mod tidy
export PAY_API_KEY_AUTH=your_api_key_here
go run main.go
```

Server runs at <http://localhost:8081>

#### Gin Example

```bash
cd go-examples/gin-example
go mod tidy
export PAY_API_KEY_AUTH=your_api_key_here
go run main.go
```

Server runs at <http://localhost:8080>

## Testing the API

Once any example is running, you can test it using the following endpoints:

### Collect Payment

```bash
curl -X POST http://localhost:<PORT>/collect-payment \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 1000,
    "phoneNumber": "237600000000"
  }'
```

### Disburse Payment

```bash
curl -X POST http://localhost:<PORT>/disburse-payment \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 1000,
    "phoneNumber": "+237600000000"
  }'
```

### Check Payment Status

```bash
curl http://localhost:<PORT>/payment/<payment_id>
```

Replace `<PORT>` with the appropriate port number for your chosen example:

- FastAPI: 8000
- Flask: 8001
- Express: 3000
- NestJS: 3001
- Laravel: 8000
- Symfony: 8000
- Echo: 8081
- Gin: 8080

## Contributing

Feel free to contribute additional examples or improvements to existing ones by submitting pull requests.

## Support

For questions about the Nkwa Pay API, please refer to the official documentation or contact our support team.

## License

This project contains example code that you can freely use in your applications.
