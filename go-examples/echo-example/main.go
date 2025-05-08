package main

import (
	"context"
	"net/http"
	"os"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	"github.com/nkwa/pay-go"
	"github.com/nkwa/pay-go/models/components"
)

type PaymentRequest struct {
	Amount      int64  `json:"amount" binding:"required"`
	PhoneNumber string `json:"phoneNumber" binding:"required"`
}

func main() {
	e := echo.New()

	// Initialize Nkwa Pay client
	ctx := context.Background()
	client := paygo.New(
		paygo.WithSecurity(os.Getenv("PAY_API_KEY_AUTH")),
	)

	e.Use(middleware.Logger())
	e.Use(middleware.Recover())

	e.GET("/", func(c echo.Context) error {
		return c.String(http.StatusOK, "Hello World! Echo example for nkwa pay-go SDK.")
	})

	// Collect payment endpoint
	e.POST("/collect-payment", func(c echo.Context) error {
		var paymentData PaymentRequest
		if err := c.Bind(&paymentData); err != nil {
			return c.JSON(http.StatusBadRequest, map[string]interface{}{
				"success": false,
				"error":   err.Error(),
			})
		}

		// Create collection request
		response, err := client.Payments.Collect(ctx, components.PaymentRequest{
			Amount:      paymentData.Amount,
			PhoneNumber: paymentData.PhoneNumber,
		})

		if err != nil {
			return c.JSON(http.StatusInternalServerError, map[string]interface{}{
				"success": false,
				"error":   err.Error(),
			})
		}

		return c.JSON(http.StatusOK, map[string]interface{}{
			"success": true,
			"data":    response.Payment,
		})
	})

	// Disburse payment endpoint
	e.POST("/disburse-payment", func(c echo.Context) error {
		var paymentData PaymentRequest
		if err := c.Bind(&paymentData); err != nil {
			return c.JSON(http.StatusBadRequest, map[string]interface{}{
				"success": false,
				"error":   err.Error(),
			})
		}

		// Create disbursement request
		response, err := client.Payments.Disburse(ctx, components.PaymentRequest{
			Amount:      paymentData.Amount,
			PhoneNumber: paymentData.PhoneNumber,
		})

		if err != nil {
			return c.JSON(http.StatusInternalServerError, map[string]interface{}{
				"success": false,
				"error":   err.Error(),
			})
		}

		return c.JSON(http.StatusOK, map[string]interface{}{
			"success": true,
			"data":    response.Payment,
		})
	})

	// Get payment status
	e.GET("/payment/:id", func(c echo.Context) error {
		paymentID := c.Param("id")
		res, err := client.Payments.GetByID(ctx, paymentID)
		if err != nil {
			return c.JSON(http.StatusInternalServerError, map[string]interface{}{
				"success": false,
				"error":   err.Error(),
			})
		}

		return c.JSON(http.StatusOK, map[string]interface{}{
			"success": true,
			"data":    res.Payment,
		})
	})

	e.Logger.Fatal(e.Start(":8081"))
}
