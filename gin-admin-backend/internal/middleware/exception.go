package middleware

import (
	"gin-admin-backend/pkg/utils"
	"net/http"
	"runtime/debug"

	"github.com/gin-gonic/gin"
)

// GlobalExceptionHandler handles all panics and errors
func GlobalExceptionHandler() gin.HandlerFunc {
	return func(c *gin.Context) {
		defer func() {
			if err := recover(); err != nil {
				// Log the stack trace
				debug.PrintStack()

				// Return standard error response
				c.JSON(http.StatusOK, utils.Result{
					Code: http.StatusInternalServerError,
					Msg:  "Internal Server Error",
					Data: nil,
				})
				c.Abort()
			}
		}()
		c.Next()
	}
}
