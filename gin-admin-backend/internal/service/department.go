package service

import (
	"gin-admin-backend/internal/biz"
	"gin-admin-backend/internal/data"
	"net/http"

	"github.com/gin-gonic/gin"
)

type DepartmentController struct {
	deptBiz *biz.DepartmentService
}

func NewDepartmentController() *DepartmentController {
	return &DepartmentController{
		deptBiz: biz.NewDepartmentService(),
	}
}

func (c *DepartmentController) Create(ctx *gin.Context) {
	var dept data.Department
	if err := ctx.ShouldBindJSON(&dept); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"code": 400, "message": err.Error()})
		return
	}

	if err := c.deptBiz.Create(&dept); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *DepartmentController) Update(ctx *gin.Context) {
	id := ctx.Param("id")
	var dept data.Department
	if err := ctx.ShouldBindJSON(&dept); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"code": 400, "message": err.Error()})
		return
	}

	if err := c.deptBiz.Update(id, &dept); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *DepartmentController) Delete(ctx *gin.Context) {
	id := ctx.Param("id")
	if err := c.deptBiz.Delete(id); err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": "success", "msg": "success"})
}

func (c *DepartmentController) List(ctx *gin.Context) {
	result, err := c.deptBiz.List()
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"code": 500, "message": err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, gin.H{"code": 0, "data": result, "msg": "success"})
}
