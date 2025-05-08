import { Controller, Get, Post, Param, Body } from '@nestjs/common';
import { AppService } from './app.service';

interface PaymentRequest {
  amount: number;
  phoneNumber: string;
}

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getHello(): string {
    return this.appService.getHello();
  }

  @Post('collect-payment')
  async collectPayment(@Body() paymentData: PaymentRequest) {
    return this.appService.collectPayment(paymentData);
  }

  @Post('disburse-payment')
  async disbursePayment(@Body() paymentData: PaymentRequest) {
    return this.appService.disbursePayment(paymentData);
  }

  @Get('payment/:id')
  async getPayment(@Param('id') id: string) {
    return this.appService.getPayment(id);
  }
}
