from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from nkwa_pay_sdk import Pay
import os

app = FastAPI(title="FastAPI Nkwa Pay Example")

# Payment request model
class PaymentRequest(BaseModel):
    amount: float
    phoneNumber: str

# Initialize Nkwa Pay client
pay = Pay(
    api_key_auth=os.getenv("PAY_API_KEY_AUTH", "")
)

@app.get("/")
async def read_root():
    return {"message": "Hello World! FastAPI example for nkwa-pay-sdk."}

@app.post("/collect-payment")
async def collect_payment(payment: PaymentRequest):
    try:
        response = await pay.payments.collect_async(
            amount=payment.amount,
            phone_number=payment.phoneNumber
        )
        
        return {
            "success": True,
            "data": response
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/disburse-payment")
async def disburse_payment(payment: PaymentRequest):
    try:
        response = await pay.payments.disburse_async(
            amount=payment.amount,
            phone_number=payment.phoneNumber
        )
        
        return {
            "success": True,
            "data": response
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/payment/{payment_id}")
async def get_payment(payment_id: str):
    try:
        response = await pay.payments.get_async(id=payment_id)
        return {
            "success": True,
            "data": response
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))