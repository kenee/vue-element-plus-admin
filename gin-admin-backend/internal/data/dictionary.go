package data

import (
	"time"
)

type Dictionary struct {
	ID        string    `gorm:"primaryKey;type:varchar(36)" json:"id"`
	DictName  string    `gorm:"type:varchar(100)" json:"dictName"`
	DictCode  string    `gorm:"type:varchar(100);unique" json:"dictCode"`
	Status    int       `gorm:"type:tinyint;default:1" json:"status"`
	Remark    string    `gorm:"type:varchar(255)" json:"remark"`
	CreatedAt time.Time `json:"createdAt"`
	UpdatedAt time.Time `json:"updatedAt"`

	Items []DictionaryItem `gorm:"foreignKey:DictID" json:"items"`
}

func (Dictionary) TableName() string {
	return "sys_dictionary"
}

type DictionaryItem struct {
	ID        string    `gorm:"primaryKey;type:varchar(36)" json:"id"`
	DictID    string    `gorm:"type:varchar(255);index" json:"dictId"`
	Label     string    `gorm:"type:varchar(100)" json:"label"`
	Value     string    `gorm:"type:varchar(100)" json:"value"`
	Sort      int       `gorm:"type:int;default:0" json:"sort"`
	Status    int       `gorm:"type:tinyint;default:1" json:"status"`
	CreatedAt time.Time `json:"createdAt"`
	UpdatedAt time.Time `json:"updatedAt"`
}

func (DictionaryItem) TableName() string {
	return "sys_dictionary_item"
}
