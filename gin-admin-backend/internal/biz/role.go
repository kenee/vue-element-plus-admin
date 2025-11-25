package biz

import (
	"gin-admin-backend/internal/data"

	"github.com/google/uuid"
)

type RoleService struct{}

func NewRoleService() *RoleService {
	return &RoleService{}
}

func (s *RoleService) Create(role *data.Role) error {
	role.ID = uuid.New().String()
	return data.DB.Create(role).Error
}

func (s *RoleService) Update(id string, role *data.Role) error {
	return data.DB.Model(&data.Role{}).Where("id = ?", id).Updates(role).Error
}

func (s *RoleService) Delete(id string) error {
	return data.DB.Delete(&data.Role{}, "id = ?", id).Error
}

func (s *RoleService) Get(id string) (*data.Role, error) {
	var role data.Role
	err := data.DB.Preload("Menus").First(&role, "id = ?", id).Error
	if err != nil {
		return nil, err
	}
	return &role, nil
}

func (s *RoleService) List(page, pageSize int, roleName string) ([]data.Role, int64, error) {
	var roles []data.Role
	var total int64
	db := data.DB.Model(&data.Role{})

	if roleName != "" {
		db = db.Where("role_name LIKE ?", "%"+roleName+"%")
	}

	db.Count(&total)

	offset := (page - 1) * pageSize
	err := db.Preload("Menus").Offset(offset).Limit(pageSize).Find(&roles).Error
	return roles, total, err
}
