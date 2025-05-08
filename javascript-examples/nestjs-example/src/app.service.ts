import { Injectable } from '@nestjs/common';
import { Pay } from '@nkwa-pay/sdk';

interface PaymentRequest {
  amount: number;
  phoneNumber: string;
}

@Injectable()
export class AppService {
  private pay: Pay;

  constructor() {
    this.pay = new Pay({
      apiKeyAuth: process.env.PAY_API_KEY_AUTH,
    });
  }

  getHello(): string {
    return 'Hello World! NestJS example for nkwa pay-js SDK.';
  }

  async collectPayment(data: PaymentRequest) {
    const response = await this.pay.payments.collect({
      amount: data.amount,
      phoneNumber: data.phoneNumber,
    });

    return {
      success: true,
      data: response.payment,
    };
  }

  async disbursePayment(data: PaymentRequest) {
    const response = await this.pay.payments.disburse({
      amount: data.amount,
      phoneNumber: data.phoneNumber,
    });

    return {
      success: true,
      data: response.payment,
    };
  }

  async getPayment(id: string) {
    const response = await this.pay.payments.get(id);
    return {
      success: true,
      data: response.payment,
    };
  }
}
