package service

import (
	"gin-admin-backend/internal/biz"
	"gin-admin-backend/internal/data"
	"net/http"

	"github.com/gin-gonic/gin"
)

type MenuController struct {
	menuBiz *biz.MenuService
}

func NewMenuController() *MenuController {
	return &MenuController{
		menuBiz: biz.NewMenuService(),
	}
}

func (c *MenuController) Create(ctx *gin.Context) {
	var menu data.Menu
	if err := ctx.ShouldBindJSON(&menu); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"code": 400, "message": err.Error()})
		return
	}

	if err := c.menuBiz.Create(&menu); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *MenuController) Update(ctx *gin.Context) {
	id := ctx.Param("id")
	var menu data.Menu
	if err := ctx.ShouldBindJSON(&menu); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"code": 400, "message": err.Error()})
		return
	}

	if err := c.menuBiz.Update(id, &menu); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *MenuController) Delete(ctx *gin.Context) {
	id := ctx.Param("id")
	if err := c.menuBiz.Delete(id); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *MenuController) List(ctx *gin.Context) {
	result, err := c.menuBiz.List()
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": result, "msg": "success"})
}

func (c *MenuController) GetUserMenus(ctx *gin.Context) {
	userId, _ := ctx.Get("userId")
	menus, err := c.menuBiz.GetUserMenus(userId.(string))
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}
	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": menus, "msg": "success"})
}

func (c *MenuController) GetRoutes(ctx *gin.Context) {
	userId, _ := ctx.Get("userId")
	routes, err := c.menuBiz.GetRoutesByUser(userId.(string))
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}
	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": routes, "msg": "success"})
}
