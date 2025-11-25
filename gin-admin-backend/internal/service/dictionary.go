package service

import (
	"gin-admin-backend/internal/biz"
	"gin-admin-backend/internal/data"
	"gin-admin-backend/pkg/utils"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

type DictionaryController struct {
	dictBiz *biz.DictionaryService
}

func NewDictionaryController() *DictionaryController {
	return &DictionaryController{
		dictBiz: biz.NewDictionaryService(),
	}
}

// Dictionary Handlers

func (c *DictionaryController) Create(ctx *gin.Context) {
	var dict data.Dictionary
	if err := ctx.ShouldBindJSON(&dict); err != nil {
		ctx.JSON(http.StatusBadRequest, utils.Result{Code: 400, Msg: err.Error()})
		return
	}

	if err := c.dictBiz.CreateDictionary(&dict); err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
}

func (c *DictionaryController) Update(ctx *gin.Context) {
	id := ctx.Param("id")
	var dict data.Dictionary
	if err := ctx.ShouldBindJSON(&dict); err != nil {
		ctx.JSON(http.StatusBadRequest, utils.Result{Code: 400, Msg: err.Error()})
		return
	}

	if err := c.dictBiz.UpdateDictionary(id, &dict); err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
}

func (c *DictionaryController) Delete(ctx *gin.Context) {
	id := ctx.Param("id")
	if err := c.dictBiz.DeleteDictionary(id); err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
}

func (c *DictionaryController) Get(ctx *gin.Context) {
	id := ctx.Param("id")
	dict, err := c.dictBiz.GetDictionary(id)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: dict})
}

func (c *DictionaryController) List(ctx *gin.Context) {
	page, _ := strconv.Atoi(ctx.DefaultQuery("page", "1"))
	pageSize, _ := strconv.Atoi(ctx.DefaultQuery("pageSize", "10"))
	dictName := ctx.Query("dictName")
	dictCode := ctx.Query("dictCode")

	dicts, total, err := c.dictBiz.ListDictionary(page, pageSize, dictName, dictCode)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: gin.H{
			"list":  dicts,
			"total": total,
		},
	})
}

// Dictionary Item Handlers

func (c *DictionaryController) CreateItem(ctx *gin.Context) {
	var item data.DictionaryItem
	if err := ctx.ShouldBindJSON(&item); err != nil {
		ctx.JSON(http.StatusBadRequest, utils.Result{Code: 400, Msg: err.Error()})
		return
	}

	if err := c.dictBiz.CreateDictionaryItem(&item); err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
}

func (c *DictionaryController) UpdateItem(ctx *gin.Context) {
	id := ctx.Param("id")
	var item data.DictionaryItem
	if err := ctx.ShouldBindJSON(&item); err != nil {
		ctx.JSON(http.StatusBadRequest, utils.Result{Code: 400, Msg: err.Error()})
		return
	}

	if err := c.dictBiz.UpdateDictionaryItem(id, &item); err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
}

func (c *DictionaryController) DeleteItem(ctx *gin.Context) {
	id := ctx.Param("id")
	if err := c.dictBiz.DeleteDictionaryItem(id); err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: "success"})
}

func (c *DictionaryController) ListItem(ctx *gin.Context) {
	dictId := ctx.Query("dictId")
	items, err := c.dictBiz.ListDictionaryItem(dictId)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, utils.Result{Code: 500, Msg: err.Error()})
		return
	}

	ctx.JSON(http.StatusOK, utils.Result{Code: 0, Msg: "success", Data: items})
}
