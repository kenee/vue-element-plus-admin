package service

import (
	"gin-admin-backend/internal/biz"
	"net/http"

	"github.com/gin-gonic/gin"
)

type AuthController struct {
	authBiz *biz.AuthService
}

func NewAuthController() *AuthController {
	return &AuthController{
		authBiz: biz.NewAuthService(),
	}
}

type LoginDto struct {
	Username string `json:"username" binding:"required"`
	Password string `json:"password" binding:"required"`
}

func (c *AuthController) Login(ctx *gin.Context) {
	var dto LoginDto
	if err := ctx.ShouldBindJSON(&dto); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"code": 400, "message": err.Error()})
		return
	}

	result, err := c.authBiz.Login(dto.Username, dto.Password)
	if err != nil {
		ctx.JSON(http.StatusUnauthorized, gin.H{"code": 401, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{
		"code": 0,
		"data": result,
		"msg":  "success",
	})
}

func (c *AuthController) Logout(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, gin.H{
		"code": 0,
		"data": "ok",
		"msg":  "success",
	})
}
