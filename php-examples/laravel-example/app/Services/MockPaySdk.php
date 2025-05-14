<?php

namespace App\Services;

class MockPaySdk
{
    private $apiKey;
    public $payments;

    public function __construct(string $apiKey)
    {
        $this->apiKey = $apiKey;
        $this->payments = new MockPayments();
    }

    public static function builder(): MockPaySdkBuilder
    {
        return new MockPaySdkBuilder();
    }
}

class MockPaySdkBuilder
{
    private $apiKey;

    public function setSecurity(string $apiKey): self
    {
        $this->apiKey = $apiKey;
        return $this;
    }

    public function build(): MockPaySdk
    {
        return new MockPaySdk($this->apiKey);
    }
}

class MockPayments
{
    public function collect($amount, $phoneNumber)
    {
        return (object)[
            'payment' => [
                'id' => uniqid('pay_'),
                'amount' => $amount,
                'phoneNumber' => $phoneNumber,
                'status' => 'pending'
            ]
        ];
    }

    public function disburse($amount, $phoneNumber)
    {
        return (object)[
            'payment' => [
                'id' => uniqid('pay_'),
                'amount' => $amount,
                'phoneNumber' => $phoneNumber,
                'status' => 'pending'
            ]
        ];
    }

    public function get($id)
    {
        return (object)[
            'payment' => [
                'id' => $id,
                'status' => 'completed'
            ]
        ];
    }
}