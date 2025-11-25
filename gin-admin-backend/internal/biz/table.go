package biz

import (
	"gin-admin-backend/internal/data"

	"github.com/google/uuid"
)

type TableService struct{}

func NewTableService() *TableService {
	return &TableService{}
}

// Table Methods

func (s *TableService) CreateTable(table *data.TableExample) error {
	table.ID = uuid.New().String()
	return data.DB.Create(table).Error
}

func (s *TableService) UpdateTable(id string, table *data.TableExample) error {
	return data.DB.Model(&data.TableExample{}).Where("id = ?", id).Updates(table).Error
}

func (s *TableService) DeleteTable(ids []string) error {
	return data.DB.Delete(&data.TableExample{}, "id IN ?", ids).Error
}

func (s *TableService) GetTable(id string) (*data.TableExample, error) {
	var table data.TableExample
	err := data.DB.First(&table, "id = ?", id).Error
	if err != nil {
		return nil, err
	}
	return &table, nil
}

func (s *TableService) ListTable(page, pageSize int, title string) ([]data.TableExample, int64, error) {
	var tables []data.TableExample
	var total int64
	db := data.DB.Model(&data.TableExample{})

	if title != "" {
		db = db.Where("title LIKE ?", "%"+title+"%")
	}

	db.Count(&total)
	offset := (page - 1) * pageSize
	err := db.Order("created_at DESC").Offset(offset).Limit(pageSize).Find(&tables).Error
	return tables, total, err
}

// Card Methods

func (s *TableService) ListCard(page, pageSize int, name string) ([]data.CardExample, int64, error) {
	var cards []data.CardExample
	var total int64
	db := data.DB.Model(&data.CardExample{})

	if name != "" {
		db = db.Where("name LIKE ?", "%"+name+"%")
	}

	db.Count(&total)

	offset := (page - 1) * pageSize
	err := db.Order("created_at DESC").Offset(offset).Limit(pageSize).Find(&cards).Error
	return cards, total, err
}
