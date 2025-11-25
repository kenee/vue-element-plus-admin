package biz

import (
	"errors"
	"gin-admin-backend/internal/data"
	"gin-admin-backend/pkg/utils"

	"gorm.io/gorm"
)

type AuthService struct{}

func NewAuthService() *AuthService {
	return &AuthService{}
}

func (s *AuthService) Login(username, password string) (map[string]interface{}, error) {
	var user data.User
	// Preload Roles to get role info
	if err := data.DB.Preload("Roles").Where("username = ?", username).First(&user).Error; err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, errors.New("user not found")
		}
		return nil, err
	}

	if !utils.CheckPasswordHash(password, user.Password) {
		return nil, errors.New("invalid password")
	}

	token, err := utils.GenerateToken(user.ID, user.Username)
	if err != nil {
		return nil, err
	}

	roleValue := ""
	roleId := ""
	if len(user.Roles) > 0 {
		roleValue = user.Roles[0].RoleValue
		roleId = user.Roles[0].ID
	}

	return map[string]interface{}{
		"access_token": token,
		"username":     user.Username,
		"role":         roleValue,
		"roleId":       roleId,
		"id":           user.ID,
	}, nil
}
