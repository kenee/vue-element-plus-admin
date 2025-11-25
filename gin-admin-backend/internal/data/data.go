package data

import (
	"fmt"
	"gin-admin-backend/internal/conf"
	"log"

	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

var DB *gorm.DB

func InitDB() {
	dsn := conf.GlobalConfig.Database.Source
	fmt.Println("Connecting to DB with source:", dsn)
	var err error
	DB, err = gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatalf("failed to connect database: %v", err)
	}

	// Auto Migrate
	err = DB.AutoMigrate(&User{}, &Role{}, &Menu{}, &Department{})
	if err != nil {
		log.Fatalf("failed to auto migrate: %v", err)
	}
	fmt.Println("Database connected and migrated successfully")
}
