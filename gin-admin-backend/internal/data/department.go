package data

import (
	"time"
)

type Department struct {
	ID        string    `gorm:"primaryKey;type:varchar(36)" json:"id"`
	ParentID  string    `gorm:"type:varchar(255)" json:"parentId"`
	Name      string    `gorm:"type:varchar(100);not null" json:"name"`
	Sort      int       `gorm:"type:int;default:0" json:"sort"`
	Status    int       `gorm:"type:tinyint;default:1" json:"status"`
	Remark    string    `gorm:"type:varchar(255)" json:"remark"`
	CreatedAt time.Time `json:"createdAt"`
	UpdatedAt time.Time `json:"updatedAt"`
}

func (Department) TableName() string {
	return "sys_department"
}
