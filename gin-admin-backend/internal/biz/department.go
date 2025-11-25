package biz

import (
	"gin-admin-backend/internal/data"

	"github.com/google/uuid"
)

type DepartmentService struct{}

func NewDepartmentService() *DepartmentService {
	return &DepartmentService{}
}

func (s *DepartmentService) Create(dept *data.Department) error {
	dept.ID = uuid.New().String()
	return data.DB.Create(dept).Error
}

func (s *DepartmentService) Update(id string, dept *data.Department) error {
	return data.DB.Model(&data.Department{}).Where("id = ?", id).Updates(dept).Error
}

func (s *DepartmentService) Delete(id string) error {
	return data.DB.Delete(&data.Department{}, "id = ?", id).Error
}

type DepartmentListResponse struct {
	List  []data.Department `json:"list"`
	Total int               `json:"total"`
}

func (s *DepartmentService) List() (*DepartmentListResponse, error) {
	var depts []data.Department
	err := data.DB.Order("sort").Find(&depts).Error
	if err != nil {
		return nil, err
	}

	return &DepartmentListResponse{
		List:  depts,
		Total: len(depts),
	}, nil
}
