<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\DependencyInjection\Attribute\Autowire;
use Psr\Log\LoggerInterface;
use Pay\Pay;
use Pay\Models\Errors\HttpError;
use Pay\Models\Errors\APIException;
use Pay\Models\Components\PaymentRequest;

class PaymentController extends AbstractController
{
    private Pay $pay;
    private LoggerInterface $logger;

    public function __construct(
        #[Autowire('%env(PAY_API_KEY_AUTH)%')] string $apiKey,
        LoggerInterface $logger
    ) {
        $this->logger = $logger;
        $this->pay = Pay::builder()
            ->setSecurity($apiKey)
            ->build();
    }

    #[Route('/collect-payment', name: 'collect_payment', methods: ['POST'])]
    public function collect(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);

        if (!$this->validatePaymentData($data)) {
            return new JsonResponse([
                'success' => false,
                'error' => 'Amount and phoneNumber are required'
            ], Response::HTTP_BAD_REQUEST);
        }

        try {
            $paymentRequest = new PaymentRequest($data['amount'], $data['phoneNumber']);
            
            $response = $this->pay->payments->collect($paymentRequest);

            return new JsonResponse([
                'success' => true,
                'data' => $response->payment
            ]);
        } catch (HttpError|APIException $e) {
            $this->logger->error('Payment collection failed', ['error' => (string)$e]);
            return new JsonResponse([
                'success' => false,
                'error' => (string)$e
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    #[Route('/disburse-payment', name: 'disburse_payment', methods: ['POST'])]
    public function disburse(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);

        if (!$this->validatePaymentData($data)) {
            return new JsonResponse([
                'success' => false,
                'error' => 'Amount and phoneNumber are required'
            ], Response::HTTP_BAD_REQUEST);
        }

        try {
            $paymentRequest = new PaymentRequest($data['amount'], $data['phoneNumber']);
            
            $response = $this->pay->payments->disburse($paymentRequest);

            return new JsonResponse([
                'success' => true,
                'data' => $response->payment
            ]);
        } catch (HttpError|APIException $e) {
            $this->logger->error('Payment disbursement failed', ['error' => (string)$e]);
            return new JsonResponse([
                'success' => false,
                'error' => (string)$e
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    #[Route('/payment/{id}', name: 'payment_status', methods: ['GET'])]
    public function status(string $id): JsonResponse
    {
        try {
            $response = $this->pay->payments->get($id);

            return new JsonResponse([
                'success' => true,
                'data' => $response->payment
            ]);
        } catch (HttpError|APIException $e) {
            $this->logger->error('Payment status check failed', ['error' => (string)$e]);
            return new JsonResponse([
                'success' => false,
                'error' => (string)$e
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    private function validatePaymentData(?array $data): bool
    {
        return isset($data['amount']) 
            && isset($data['phoneNumber'])
            && is_numeric($data['amount'])
            && is_string($data['phoneNumber']);
    }
}
