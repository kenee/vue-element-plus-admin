package data

import (
	"time"
)

type Menu struct {
	ID         string    `gorm:"primaryKey;type:varchar(36)" json:"id"`
	ParentID   string    `gorm:"type:varchar(255)" json:"parentId"`
	Path       string    `gorm:"type:varchar(255)" json:"path"`
	Component  string    `gorm:"type:varchar(255)" json:"component"`
	Redirect   string    `gorm:"type:varchar(255)" json:"redirect"`
	Title      string    `gorm:"type:varchar(100);not null" json:"title"`
	Name       string    `gorm:"type:varchar(100)" json:"name"`
	Icon       string    `gorm:"type:varchar(50)" json:"icon"`
	Meta       string    `gorm:"type:json" json:"meta"` // Stored as JSON string
	Type       int       `gorm:"type:tinyint;default:0;comment:0:Directory,1:Menu,2:Button" json:"type"`
	Status     int       `gorm:"type:tinyint;default:1" json:"status"`
	Permission string    `gorm:"type:varchar(100)" json:"permission"`
	Sort       int       `gorm:"type:int;default:0" json:"sort"`
	CreatedAt  time.Time `json:"createdAt"`
	UpdatedAt  time.Time `json:"updatedAt"`
}

func (Menu) TableName() string {
	return "sys_menu"
}
