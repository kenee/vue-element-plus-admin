package conf

import (
	"github.com/joho/godotenv"
	"github.com/spf13/viper"
	"log"
	"strings"
)

type Config struct {
	Server   ServerConfig
	Database DatabaseConfig
	Jwt      JwtConfig
}

type ServerConfig struct {
	Port int
	Mode string
}

type DatabaseConfig struct {
	Driver string
	Source string
}

type JwtConfig struct {
	Secret string
	Expire int64
}

var GlobalConfig Config

func InitConfig() {
	// 0. Load .env file if exists
	if err := godotenv.Load(); err != nil {
		log.Printf("Info: .env file not found or error loading it: %s", err)
	}

	// 1. Read config.yaml
	viper.SetConfigName("config")
	viper.SetConfigType("yaml")
	viper.AddConfigPath(".")

	if err := viper.ReadInConfig(); err != nil {
		log.Printf("Info: config.yaml not found, using .env and environment variables: %s", err)
	}

	// 2. Automatic Env Vars
	viper.AutomaticEnv()
	viper.SetEnvKeyReplacer(strings.NewReplacer(".", "_"))

	// Set defaults to ensure Viper knows these keys exist for Env binding
	viper.SetDefault("server.port", 3000)
	viper.SetDefault("server.mode", "debug")
	viper.SetDefault("database.driver", "mysql")
	viper.SetDefault("database.source", "")
	viper.SetDefault("jwt.secret", "")
	viper.SetDefault("jwt.expire", 86400)

	if err := viper.Unmarshal(&GlobalConfig); err != nil {
		log.Fatalf("Unable to decode into struct, %v", err)
	}
}
