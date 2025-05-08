from flask import Flask, request, jsonify
from nkwa_pay_sdk import Pay
import os
import httpx

app = Flask(__name__)

# Initialize Nkwa Pay client with custom HTTP client for better configuration
pay = Pay(
    api_key_auth=os.getenv("PAY_API_KEY_AUTH", ""),
)

@app.route('/')
def hello():
    return 'Hello World! Flask example for nkwa-pay-sdk.'

@app.route('/collect-payment', methods=['POST'])
def collect_payment():
    try:
        data = request.get_json()
        
        # Validate required fields
        required_fields = ['amount', 'phoneNumber']
        for field in required_fields:
            if field not in data:
                return jsonify({
                    'success': False,
                    'error': f'Missing required field: {field}'
                }), 400

        # Create collection request
        response = pay.payments.collect(
            amount=data['amount'],
            phone_number=data['phoneNumber']
        )

        return jsonify({
            'success': True,
            'data': vars(response)
        })
    except Exception as e:
        return jsonify({
            'success': False,
            'error': str(e)
        }), 500

@app.route('/disburse-payment', methods=['POST'])
def disburse_payment():
    try:
        data = request.get_json()
        
        # Validate required fields
        required_fields = ['amount', 'phoneNumber']
        for field in required_fields:
            if field not in data:
                return jsonify({
                    'success': False,
                    'error': f'Missing required field: {field}'
                }), 400

        # Create disbursement request
        response = pay.payments.disburse(
            amount=data['amount'],
            phone_number=data['phoneNumber']
        )

        return jsonify({
            'success': True,
            'data': vars(response)
        })
    except Exception as e:
        return jsonify({
            'success': False,
            'error': str(e)
        }), 500

@app.route('/payment/<payment_id>')
def get_payment(payment_id):
    try:
        response = pay.payments.get(id=payment_id)
        return jsonify({
            'success': True,
            'data': vars(response)
        })
    except Exception as e:
        return jsonify({
            'success': False,
            'error': str(e)
        }), 500

if __name__ == '__main__':
    app.run(debug=True, port=8001)