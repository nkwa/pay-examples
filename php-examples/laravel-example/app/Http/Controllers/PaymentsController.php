<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Log;
use Pay\Pay;
use Pay\Models\Errors\HttpError;
use Pay\Models\Errors\APIException;
use Pay\Models\Components\PaymentRequest;

class PaymentsController extends Controller
{
    private Pay $pay;

    public function __construct()
    {
        $this->pay = Pay::builder()
            ->setSecurity(Config::get('services.nkwa.key'))
            ->build();
    }

    public function collect(Request $request): JsonResponse
    {
        $validated = $request->validate([
            'amount' => 'required|numeric',
            'phoneNumber' => 'required|string'
        ]);

        try {
            $paymentRequest = new PaymentRequest($validated['amount'], $validated['phoneNumber']);

            $response = $this->pay->payments->collect($paymentRequest);

            return Response::json([
                'success' => true,
                'data' => $response->payment
            ]);
        } catch (HttpError|APIException $e) {
            Log::error('Payment collection failed', ['error' => (string)$e]);
            return Response::json([
                'success' => false,
                'error' => (string)$e
            ], 500);
        }
    }

    public function disburse(Request $request): JsonResponse
    {
        $validated = $request->validate([
            'amount' => 'required|numeric',
            'phoneNumber' => 'required|string'
        ]);

        try {
            $paymentRequest = new PaymentRequest($validated['amount'], $validated['phoneNumber']);

            $response = $this->pay->payments->disburse($paymentRequest);

            return Response::json([
                'success' => true,
                'data' => $response->payment
            ]);
        } catch (HttpError|APIException $e) {
            Log::error('Payment disbursement failed', ['error' => (string)$e]);
            return Response::json([
                'success' => false,
                'error' => (string)$e
            ], 500);
        }
    }

    public function status(string $id): JsonResponse
    {
        try {
            $response = $this->pay->payments->get($id);

            return Response::json([
                'success' => true,
                'data' => $response->payment
            ]);
        } catch (HttpError|APIException $e) {
            Log::error('Payment status check failed', ['error' => (string)$e]);
            return Response::json([
                'success' => false,
                'error' => (string)$e
            ], 500);
        }
    }
}
