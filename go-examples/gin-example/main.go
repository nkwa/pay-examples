package main

import (
	"context"
	"net/http"
	"os"

	"github.com/gin-gonic/gin"
	"github.com/nkwa/pay-go"
	"github.com/nkwa/pay-go/models/components"
)

type PaymentRequest struct {
	Amount      int64  `json:"amount" binding:"required"`
	PhoneNumber string `json:"phoneNumber" binding:"required"`
}

func main() {
	r := gin.Default()

	// Initialize Nkwa Pay client
	ctx := context.Background()
	client := paygo.New(
		paygo.WithSecurity(os.Getenv("PAY_API_KEY_AUTH")),
	)

	r.GET("/", func(c *gin.Context) {
		c.String(http.StatusOK, "Hello World! Gin example for nkwa pay-go SDK.")
	})

	// Collect payment endpoint
	r.POST("/collect-payment", func(c *gin.Context) {
		var paymentData PaymentRequest
		if err := c.ShouldBindJSON(&paymentData); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{
				"success": false,
				"error":   err.Error(),
			})
			return
		}

		// Create collection request
		res, err := client.Payments.Collect(ctx, components.PaymentRequest{
			Amount:      paymentData.Amount,
			PhoneNumber: paymentData.PhoneNumber,
		})

		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{
				"success": false,
				"error":   err.Error(),
			})
			return
		}

		c.JSON(http.StatusOK, gin.H{
			"success": true,
			"data":    res.Payment,
		})
	})

	// Disburse payment endpoint
	r.POST("/disburse-payment", func(c *gin.Context) {
		var paymentData PaymentRequest
		if err := c.ShouldBindJSON(&paymentData); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{
				"success": false,
				"error":   err.Error(),
			})
			return
		}

		// Create disbursement request
		res, err := client.Payments.Disburse(ctx, components.PaymentRequest{
			Amount:      paymentData.Amount,
			PhoneNumber: paymentData.PhoneNumber,
		})

		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{
				"success": false,
				"error":   err.Error(),
			})
			return
		}

		c.JSON(http.StatusOK, gin.H{
			"success": true,
			"data":    res.Payment,
		})
	})

	// Get payment status
	r.GET("/payment/:id", func(c *gin.Context) {
		paymentID := c.Param("id")
		res, err := client.Payments.GetByID(ctx, paymentID)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{
				"success": false,
				"error":   err.Error(),
			})
			return
		}

		c.JSON(http.StatusOK, gin.H{
			"success": true,
			"data":    res.Payment,
		})
	})

	r.Run(":8080")
}
