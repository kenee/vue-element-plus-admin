package data

import (
	"time"
)

type User struct {
	ID        string    `gorm:"primaryKey;type:varchar(36)" json:"id"`
	Username  string    `gorm:"type:varchar(50);unique;not null" json:"username"`
	Password  string    `gorm:"type:varchar(100);not null" json:"-"`
	Nickname  string    `gorm:"type:varchar(50)" json:"nickname"`
	DeptID    string    `gorm:"type:varchar(255)" json:"deptId"`
	Email     string    `gorm:"type:varchar(100)" json:"email"`
	Status    int       `gorm:"type:tinyint;default:1" json:"status"`
	CreatedAt time.Time `json:"createdAt"`
	UpdatedAt time.Time `json:"updatedAt"`

	Roles []Role `gorm:"many2many:sys_user_role;joinForeignKey:UserId;joinReferences:RoleId" json:"roles"`
}

func (User) TableName() string {
	return "sys_user"
}
