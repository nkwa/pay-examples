declare module '@nkwa-pay/sdk' {
    export class Pay {
        constructor(config: { apiKeyAuth: string });
        payments: {
            collect(data: { amount: number; phoneNumber: string }): Promise<{ payment: any }>;
            disburse(data: { amount: number; phoneNumber: string }): Promise<{ payment: any }>;
            get(id: string): Promise<{ payment: any }>;
        };
    }
}