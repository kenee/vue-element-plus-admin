package service

import (
	"gin-admin-backend/internal/biz"
	"gin-admin-backend/internal/data"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

type UserController struct {
	userBiz *biz.UserService
}

func NewUserController() *UserController {
	return &UserController{
		userBiz: biz.NewUserService(),
	}
}

type CreateUserDto struct {
	Username string   `json:"username" binding:"required"`
	Password string   `json:"password" binding:"required"`
	Nickname string   `json:"nickname"`
	RoleIds  []string `json:"roleIds"`
}

func (c *UserController) Create(ctx *gin.Context) {
	var dto CreateUserDto
	if err := ctx.ShouldBindJSON(&dto); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"code": 400, "message": err.Error()})
		return
	}

	user := &data.User{
		Username: dto.Username,
		Password: dto.Password,
		Nickname: dto.Nickname,
	}

	if len(dto.RoleIds) > 0 {
		var roles []data.Role
		for _, roleId := range dto.RoleIds {
			roles = append(roles, data.Role{ID: roleId})
		}
		user.Roles = roles
	}

	if err := c.userBiz.Create(user); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *UserController) Update(ctx *gin.Context) {
	id := ctx.Param("id")
	var user data.User
	if err := ctx.ShouldBindJSON(&user); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"code": 400, "message": err.Error()})
		return
	}

	if err := c.userBiz.Update(id, &user); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *UserController) Delete(ctx *gin.Context) {
	id := ctx.Param("id")
	if err := c.userBiz.Delete(id); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *UserController) Get(ctx *gin.Context) {
	id := ctx.Param("id")
	user, err := c.userBiz.Get(id)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": user, "msg": "success"})
}

func (c *UserController) List(ctx *gin.Context) {
	page, _ := strconv.Atoi(ctx.DefaultQuery("page", "1"))
	pageSize, _ := strconv.Atoi(ctx.DefaultQuery("pageSize", "10"))
	username := ctx.Query("username")

	users, total, err := c.userBiz.List(page, pageSize, username)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{
		"code": 0,
		"data": gin.H{
			"list":  users,
			"total": total,
		},
		"msg": "success",
	})
}

func (c *UserController) GetProfile(ctx *gin.Context) {
	userId, _ := ctx.Get("userId")
	user, err := c.userBiz.Get(userId.(string))
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}
	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": user, "msg": "success"})
}
