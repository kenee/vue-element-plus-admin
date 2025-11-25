package biz

import (
	"errors"
	"gin-admin-backend/internal/data"
	"gin-admin-backend/pkg/utils"

	"github.com/google/uuid"
)

type UserService struct{}

func NewUserService() *UserService {
	return &UserService{}
}

func (s *UserService) Create(user *data.User) error {
	var count int64
	data.DB.Model(&data.User{}).Where("username = ?", user.Username).Count(&count)
	if count > 0 {
		return errors.New("username already exists")
	}

	user.ID = uuid.New().String()
	hashedPassword, err := utils.HashPassword(user.Password)
	if err != nil {
		return err
	}
	user.Password = hashedPassword
	return data.DB.Create(user).Error
}

func (s *UserService) Update(id string, user *data.User) error {
	return data.DB.Model(&data.User{}).Where("id = ?", id).Updates(user).Error
}

func (s *UserService) Delete(id string) error {
	return data.DB.Delete(&data.User{}, "id = ?", id).Error
}

func (s *UserService) Get(id string) (*data.User, error) {
	var user data.User
	err := data.DB.Preload("Roles").First(&user, "id = ?", id).Error
	if err != nil {
		return nil, err
	}
	return &user, nil
}

func (s *UserService) List(page, pageSize int, username string) ([]data.User, int64, error) {
	var users []data.User
	var total int64
	db := data.DB.Model(&data.User{})

	if username != "" {
		db = db.Where("username LIKE ?", "%"+username+"%")
	}

	db.Count(&total)

	offset := (page - 1) * pageSize
	err := db.Preload("Roles").Offset(offset).Limit(pageSize).Find(&users).Error
	return users, total, err
}

func (s *UserService) UpdatePassword(id, password string) error {
	hashedPassword, err := utils.HashPassword(password)
	if err != nil {
		return err
	}
	return data.DB.Model(&data.User{}).Where("id = ?", id).Update("password", hashedPassword).Error
}
