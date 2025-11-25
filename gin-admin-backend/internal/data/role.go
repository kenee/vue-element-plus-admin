package data

import (
	"time"
)

type Role struct {
	ID        string    `gorm:"primaryKey;type:varchar(36)" json:"id"`
	RoleName  string    `gorm:"type:varchar(50);not null" json:"roleName"`
	RoleValue string    `gorm:"type:varchar(50);unique;not null" json:"roleValue"`
	Status    int       `gorm:"type:tinyint;default:1" json:"status"`
	Remark    string    `gorm:"type:varchar(255)" json:"remark"`
	CreatedAt time.Time `json:"createdAt"`
	UpdatedAt time.Time `json:"updatedAt"`

	Menus []Menu `gorm:"many2many:sys_role_menu;joinForeignKey:RoleId;joinReferences:MenuId" json:"menus"`
}

func (Role) TableName() string {
	return "sys_role"
}
