package main

import (
	"fmt"
	"gin-admin-backend/internal/conf"
	"gin-admin-backend/internal/data"
	"gin-admin-backend/internal/middleware"
	"gin-admin-backend/internal/service"
	"gin-admin-backend/pkg/utils"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
)

func main() {
	// 1. Init Config
	conf.InitConfig()

	// 2. Init DB
	data.InitDB()

	// 3. Init Gin
	r := gin.Default()

	// CORS
	config := cors.DefaultConfig()
	config.AllowAllOrigins = true
	config.AllowHeaders = []string{"Origin", "Content-Length", "Content-Type", "Authorization"}
	r.Use(cors.New(config))

	// Global Exception Handler
	r.Use(middleware.GlobalExceptionHandler())

	// 4. Register Routes
	authController := service.NewAuthController()

	api := r.Group("/api")
	{
		auth := api.Group("/auth")
		{
			auth.POST("/login", authController.Login)
			auth.GET("/logout", authController.Logout)
		}

		userController := service.NewUserController()
		user := api.Group("/user")
		user.Use(middleware.JWTAuth())
		{
			user.POST("", userController.Create)
			user.PUT("/:id", userController.Update)
			user.DELETE("/:id", userController.Delete)
			user.GET("/:id", userController.Get)
			user.GET("", userController.List)
			user.GET("/profile", userController.GetProfile)
		}

		roleController := service.NewRoleController()
		role := api.Group("/role")
		role.Use(middleware.JWTAuth())
		{
			role.POST("", roleController.Create)
			role.PUT("/:id", roleController.Update)
			role.DELETE("/:id", roleController.Delete)
			role.GET("/:id", roleController.Get)
			role.GET("", roleController.List)
		}

		menuController := service.NewMenuController()
		menu := api.Group("/menu")
		menu.Use(middleware.JWTAuth())
		{
			menu.POST("", menuController.Create)
			menu.PUT("/:id", menuController.Update)
			menu.DELETE("/:id", menuController.Delete)
			menu.GET("", menuController.List)
			menu.GET("/routes", menuController.GetRoutes)
			menu.GET("/user", menuController.GetUserMenus)
		}

		deptController := service.NewDepartmentController()
		dept := api.Group("/department")
		dept.Use(middleware.JWTAuth())
		{
			dept.POST("", deptController.Create)
			dept.PUT("/:id", deptController.Update)
			dept.DELETE("/:id", deptController.Delete)
			dept.GET("", deptController.List)
		}

		analysisController := service.NewAnalysisController()
		analysis := api.Group("/analysis")
		analysis.Use(middleware.JWTAuth())
		{
			analysis.GET("/total", analysisController.GetTotal)
			analysis.GET("/userAccessSource", analysisController.GetUserAccessSource)
			analysis.GET("/weeklyUserActivity", analysisController.GetWeeklyUserActivity)
			analysis.GET("/monthlySales", analysisController.GetMonthlySales)
		}

		dictController := service.NewDictionaryController()
		dict := api.Group("/dictionary")
		dict.Use(middleware.JWTAuth())
		{
			dict.GET("", dictController.List)
			dict.POST("", dictController.Create)
			dict.PUT("/:id", dictController.Update)
			dict.DELETE("/:id", dictController.Delete)
			dict.GET("/:id", dictController.Get)
			dict.GET("/detail", dictController.ListItem)
			dict.POST("/detail", dictController.CreateItem)
			dict.PUT("/detail/:id", dictController.UpdateItem)
			dict.DELETE("/detail/:id", dictController.DeleteItem)
		}

		workplaceController := service.NewWorkplaceController()
		workplace := api.Group("/workplace")
		workplace.Use(middleware.JWTAuth())
		{
			workplace.GET("/total", workplaceController.GetTotal)
			workplace.GET("/project", workplaceController.GetProject)
			workplace.GET("/dynamic", workplaceController.GetDynamic)
			workplace.GET("/team", workplaceController.GetTeam)
			workplace.GET("/radar", workplaceController.GetRadar)
		}

		tableController := service.NewTableController()
		table := api.Group("/table")
		table.Use(middleware.JWTAuth())
		{
			table.GET("/example/list", tableController.List)
			table.POST("/example/save", tableController.Save)
			table.POST("/example/delete", tableController.Delete)
			table.GET("/example/detail/:id", tableController.Get)
		}

		card := api.Group("/card")
		card.Use(middleware.JWTAuth())
		{
			card.GET("/list", tableController.ListCard)
		}
	}

	// 5. Create Admin User if not exists
	createAdminUser()

	// 6. Run Server
	addr := fmt.Sprintf(":%d", conf.GlobalConfig.Server.Port)
	r.Run(addr)
}

func createAdminUser() {
	var count int64
	data.DB.Model(&data.User{}).Count(&count)
	if count == 0 {
		password, _ := utils.HashPassword("123456")
		admin := data.User{
			ID:       "1",
			Username: "admin",
			Password: password,
			Nickname: "Admin",
			Status:   1,
		}
		data.DB.Create(&admin)
		fmt.Println("Admin user created: admin / 123456")
	}
}
