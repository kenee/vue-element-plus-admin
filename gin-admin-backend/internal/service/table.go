package service

import (
	"gin-admin-backend/internal/biz"
	"gin-admin-backend/internal/data"
	"gin-admin-backend/pkg/utils"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

type TableController struct {
	tableBiz *biz.TableService
}

func NewTableController() *TableController {
	return &TableController{
		tableBiz: biz.NewTableService(),
	}
}

// Table Handlers

func (c *TableController) Create(ctx *gin.Context) {
	var table data.TableExample
	if err := ctx.ShouldBindJSON(&table); err != nil {
		ctx.JSON(http.StatusBadRequest, utils.Result{Code: 400, Msg: err.Error()})
		return
	}

	if err := c.tableBiz.CreateTable(&table); err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
}

func (c *TableController) Update(ctx *gin.Context) {
	id := ctx.Param("id")
	var table data.TableExample
	if err := ctx.ShouldBindJSON(&table); err != nil {
		ctx.JSON(http.StatusBadRequest, utils.Result{Code: 400, Msg: err.Error()})
		return
	}

	if err := c.tableBiz.UpdateTable(id, &table); err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
}

func (c *TableController) Save(ctx *gin.Context) {
	// Save handles both create and update
	var table data.TableExample
	if err := ctx.ShouldBindJSON(&table); err != nil {
		ctx.JSON(http.StatusBadRequest, utils.Result{Code: 400, Msg: err.Error()})
		return
	}

	if table.ID != "" {
		if err := c.tableBiz.UpdateTable(table.ID, &table); err != nil {
			ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
			return
		}
	} else {
		if err := c.tableBiz.CreateTable(&table); err != nil {
			ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
			return
		}
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
}

func (c *TableController) Delete(ctx *gin.Context) {
	// Supports single ID or comma-separated IDs via body or query?
	// NestJS implementation used `remove(ids: string[])`
	// Typically DELETE requests might pass IDs in body or query.
	// Let's assume body for bulk delete or param for single.
	// But standard REST uses param for single.
	// Let's check how the frontend calls it.
	// Assuming `ids` in body for bulk delete.

	var body struct {
		Ids []string `json:"ids"`
	}
	if err := ctx.ShouldBindJSON(&body); err == nil && len(body.Ids) > 0 {
		if err := c.tableBiz.DeleteTable(body.Ids); err != nil {
			ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
			return
		}
		ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
		return
	}

	// Fallback to single ID param if body is empty?
	id := ctx.Param("id")
	if id != "" {
		if err := c.tableBiz.DeleteTable([]string{id}); err != nil {
			ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
			return
		}
		ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
		return
	}

	ctx.JSON(http.StatusBadRequest, utils.Result{Code: 400, Msg: "ids required"})
}

func (c *TableController) Get(ctx *gin.Context) {
	id := ctx.Param("id")
	table, err := c.tableBiz.GetTable(id)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: table})
}

func (c *TableController) List(ctx *gin.Context) {
	page, _ := strconv.Atoi(ctx.DefaultQuery("page", "1"))
	pageSize, _ := strconv.Atoi(ctx.DefaultQuery("pageSize", "10"))
	title := ctx.Query("title")

	tables, total, err := c.tableBiz.ListTable(page, pageSize, title)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: gin.H{
			"list":  tables,
			"total": total,
		},
	})
}

// Card Handlers

func (c *TableController) ListCard(ctx *gin.Context) {
	page, _ := strconv.Atoi(ctx.DefaultQuery("page", "1"))
	pageSize, _ := strconv.Atoi(ctx.DefaultQuery("pageSize", "10"))
	name := ctx.Query("name")

	cards, total, err := c.tableBiz.ListCard(page, pageSize, name)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: gin.H{
			"list":  cards,
			"total": total,
		},
	})
}
