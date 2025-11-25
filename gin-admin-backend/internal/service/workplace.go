package service

import (
	"gin-admin-backend/pkg/utils"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)

type WorkplaceController struct{}

func NewWorkplaceController() *WorkplaceController {
	return &WorkplaceController{}
}

func (c *WorkplaceController) GetTotal(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: gin.H{
			"project": 40,
			"access":  2340,
			"todo":    10,
		},
	})
}

func (c *WorkplaceController) GetProject(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: []gin.H{
			{
				"name":     "Github",
				"icon":     "akar-icons:github-fill",
				"message":  "workplace.introduction",
				"personal": "Archer",
				"time":     time.Now(),
			},
			{
				"name":     "Vue",
				"icon":     "logos:vue",
				"message":  "workplace.introduction",
				"personal": "Archer",
				"time":     time.Now(),
			},
			{
				"name":     "Angular",
				"icon":     "logos:angular-icon",
				"message":  "workplace.introduction",
				"personal": "Archer",
				"time":     time.Now(),
			},
			{
				"name":     "React",
				"icon":     "logos:react",
				"message":  "workplace.introduction",
				"personal": "Archer",
				"time":     time.Now(),
			},
			{
				"name":     "Webpack",
				"icon":     "logos:webpack",
				"message":  "workplace.introduction",
				"personal": "Archer",
				"time":     time.Now(),
			},
			{
				"name":     "Vite",
				"icon":     "vscode-icons:file-type-vite",
				"message":  "workplace.introduction",
				"personal": "Archer",
				"time":     time.Now(),
			},
		},
	})
}

func (c *WorkplaceController) GetDynamic(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: []gin.H{
			{
				"keys": []string{"workplace.push", "Github"},
				"time": time.Now(),
			},
			{
				"keys": []string{"workplace.push", "Github"},
				"time": time.Now(),
			},
			{
				"keys": []string{"workplace.push", "Github"},
				"time": time.Now(),
			},
			{
				"keys": []string{"workplace.push", "Github"},
				"time": time.Now(),
			},
			{
				"keys": []string{"workplace.push", "Github"},
				"time": time.Now(),
			},
			{
				"keys": []string{"workplace.push", "Github"},
				"time": time.Now(),
			},
		},
	})
}

func (c *WorkplaceController) GetTeam(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: []gin.H{
			{
				"name": "Github",
				"icon": "akar-icons:github-fill",
			},
			{
				"name": "Vue",
				"icon": "logos:vue",
			},
			{
				"name": "Angular",
				"icon": "logos:angular-icon",
			},
			{
				"name": "React",
				"icon": "logos:react",
			},
			{
				"name": "Webpack",
				"icon": "logos:webpack",
			},
			{
				"name": "Vite",
				"icon": "vscode-icons:file-type-vite",
			},
		},
	})
}

func (c *WorkplaceController) GetRadar(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, utils.Result{
		Code: 0,
		Msg:  "success",
		Data: []gin.H{
			{"name": "workplace.quote", "max": 65, "personal": 42, "team": 50},
			{"name": "workplace.contribution", "max": 160, "personal": 30, "team": 140},
			{"name": "workplace.hot", "max": 300, "personal": 20, "team": 28},
			{"name": "workplace.yield", "max": 130, "personal": 35, "team": 35},
			{"name": "workplace.follow", "max": 100, "personal": 80, "team": 90},
		},
	})
}
