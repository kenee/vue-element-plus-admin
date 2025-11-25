package biz

import (
	"errors"
	"gin-admin-backend/internal/data"

	"github.com/google/uuid"
)

type DictionaryService struct{}

func NewDictionaryService() *DictionaryService {
	return &DictionaryService{}
}

// Dictionary CRUD

func (s *DictionaryService) CreateDictionary(dict *data.Dictionary) error {
	var count int64
	data.DB.Model(&data.Dictionary{}).Where("dict_code = ?", dict.DictCode).Count(&count)
	if count > 0 {
		return errors.New("dictionary code already exists")
	}

	dict.ID = uuid.New().String()
	return data.DB.Create(dict).Error
}

func (s *DictionaryService) UpdateDictionary(id string, dict *data.Dictionary) error {
	return data.DB.Model(&data.Dictionary{}).Where("id = ?", id).Updates(dict).Error
}

func (s *DictionaryService) DeleteDictionary(id string) error {
	// Transaction to delete dictionary and its details
	tx := data.DB.Begin()
	if err := tx.Delete(&data.DictionaryItem{}, "dict_id = ?", id).Error; err != nil {
		tx.Rollback()
		return err
	}
	if err := tx.Delete(&data.Dictionary{}, "id = ?", id).Error; err != nil {
		tx.Rollback()
		return err
	}
	return tx.Commit().Error
}

func (s *DictionaryService) GetDictionary(id string) (*data.Dictionary, error) {
	var dict data.Dictionary
	err := data.DB.Preload("Items").First(&dict, "id = ?", id).Error
	if err != nil {
		return nil, err
	}
	return &dict, nil
}

func (s *DictionaryService) ListDictionary(page, pageSize int, dictName, dictCode string) ([]data.Dictionary, int64, error) {
	var dicts []data.Dictionary
	var total int64
	db := data.DB.Model(&data.Dictionary{})

	if dictName != "" {
		db = db.Where("dict_name LIKE ?", "%"+dictName+"%")
	}
	if dictCode != "" {
		db = db.Where("dict_code LIKE ?", "%"+dictCode+"%")
	}

	db.Count(&total)

	offset := (page - 1) * pageSize
	err := db.Preload("Items").Offset(offset).Limit(pageSize).Find(&dicts).Error
	return dicts, total, err
}

// Dictionary Item CRUD

func (s *DictionaryService) CreateDictionaryItem(item *data.DictionaryItem) error {
	item.ID = uuid.New().String()
	return data.DB.Create(item).Error
}

func (s *DictionaryService) UpdateDictionaryItem(id string, item *data.DictionaryItem) error {
	return data.DB.Model(&data.DictionaryItem{}).Where("id = ?", id).Updates(item).Error
}

func (s *DictionaryService) DeleteDictionaryItem(id string) error {
	return data.DB.Delete(&data.DictionaryItem{}, "id = ?", id).Error
}

func (s *DictionaryService) ListDictionaryItem(dictId string) ([]data.DictionaryItem, error) {
	var items []data.DictionaryItem
	err := data.DB.Where("dict_id = ?", dictId).Order("sort asc").Find(&items).Error
	return items, err
}
