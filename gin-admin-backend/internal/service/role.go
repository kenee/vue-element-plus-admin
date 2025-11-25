package service

import (
	"gin-admin-backend/internal/biz"
	"gin-admin-backend/internal/data"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

type RoleController struct {
	roleBiz *biz.RoleService
}

func NewRoleController() *RoleController {
	return &RoleController{
		roleBiz: biz.NewRoleService(),
	}
}

type CreateRoleDto struct {
	RoleName  string   `json:"roleName" binding:"required"`
	RoleValue string   `json:"roleValue" binding:"required"`
	Status    int      `json:"status"`
	Remark    string   `json:"remark"`
	MenuIds   []string `json:"menuIds"`
}

func (c *RoleController) Create(ctx *gin.Context) {
	var dto CreateRoleDto
	if err := ctx.ShouldBindJSON(&dto); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"code": 400, "message": err.Error()})
		return
	}

	role := &data.Role{
		RoleName:  dto.RoleName,
		RoleValue: dto.RoleValue,
		Status:    dto.Status,
		Remark:    dto.Remark,
	}

	if len(dto.MenuIds) > 0 {
		var menus []data.Menu
		for _, menuId := range dto.MenuIds {
			menus = append(menus, data.Menu{ID: menuId})
		}
		role.Menus = menus
	}

	if err := c.roleBiz.Create(role); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *RoleController) Update(ctx *gin.Context) {
	id := ctx.Param("id")
	var role data.Role
	if err := ctx.ShouldBindJSON(&role); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"code": 400, "message": err.Error()})
		return
	}

	if err := c.roleBiz.Update(id, &role); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *RoleController) Delete(ctx *gin.Context) {
	id := ctx.Param("id")
	if err := c.roleBiz.Delete(id); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *RoleController) Get(ctx *gin.Context) {
	id := ctx.Param("id")
	role, err := c.roleBiz.Get(id)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": role, "msg": "success"})
}

func (c *RoleController) List(ctx *gin.Context) {
	page, _ := strconv.Atoi(ctx.DefaultQuery("page", "1"))
	pageSize, _ := strconv.Atoi(ctx.DefaultQuery("pageSize", "10"))
	roleName := ctx.Query("roleName")

	roles, total, err := c.roleBiz.List(page, pageSize, roleName)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{
		"code": 0,
		"data": gin.H{
			"list":  roles,
			"total": total,
		},
		"msg": "success",
	})
}
