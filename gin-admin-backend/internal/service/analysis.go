package service

import (
	"gin-admin-backend/internal/data"
	"gin-admin-backend/pkg/utils"
	"net/http"

	"github.com/gin-gonic/gin"
)

type AnalysisController struct{}

func NewAnalysisController() *AnalysisController {
	return &AnalysisController{}
}

// GetTotal returns total counts
func (c *AnalysisController) GetTotal(ctx *gin.Context) {
	var userCount int64
	data.DB.Model(&data.User{}).Count(&userCount)

	// Mock data for other stats
	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: gin.H{
			"user":    userCount,
			"views":   10240,
			"message": 321,
			"money":   8888,
		},
	})
}

// GetUserAccessSource returns user access source data
func (c *AnalysisController) GetUserAccessSource(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: []gin.H{
			{"value": 1048, "name": "Search Engine"},
			{"value": 735, "name": "Direct"},
			{"value": 580, "name": "Email Marketing"},
			{"value": 484, "name": "Union Ads"},
			{"value": 300, "name": "Video Ads"},
		},
	})
}

// GetWeeklyUserActivity returns weekly user activity
func (c *AnalysisController) GetWeeklyUserActivity(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: []gin.H{
			{"value": 13253, "name": "Mon"},
			{"value": 34235, "name": "Tue"},
			{"value": 26321, "name": "Wed"},
			{"value": 12340, "name": "Thu"},
			{"value": 24643, "name": "Fri"},
			{"value": 1322, "name": "Sat"},
			{"value": 1324, "name": "Sun"},
		},
	})
}

// GetMonthlySales returns monthly sales data
func (c *AnalysisController) GetMonthlySales(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: []gin.H{
			{"estimate": 100, "actual": 120, "name": "Jan"},
			{"estimate": 120, "actual": 82, "name": "Feb"},
			{"estimate": 161, "actual": 91, "name": "Mar"},
			{"estimate": 134, "actual": 154, "name": "Apr"},
			{"estimate": 105, "actual": 162, "name": "May"},
			{"estimate": 160, "actual": 140, "name": "Jun"},
			{"estimate": 165, "actual": 145, "name": "Jul"},
			{"estimate": 114, "actual": 250, "name": "Aug"},
			{"estimate": 163, "actual": 134, "name": "Sep"},
			{"estimate": 185, "actual": 56, "name": "Oct"},
			{"estimate": 118, "actual": 99, "name": "Nov"},
			{"estimate": 123, "actual": 123, "name": "Dec"},
		},
	})
}
