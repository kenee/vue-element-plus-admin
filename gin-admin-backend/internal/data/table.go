package data

import "time"

type TableExample struct {
	ID          string    `gorm:"primaryKey;type:varchar(36)" json:"id"`
	Author      string    `gorm:"type:varchar(100)" json:"author"`
	Title       string    `gorm:"type:varchar(200)" json:"title"`
	Content     string    `gorm:"type:text" json:"content"`
	Importance  int       `gorm:"type:int;default:1" json:"importance"`
	DisplayTime time.Time `json:"displayTime"`
	Pageviews   int       `gorm:"type:int;default:0" json:"pageviews"`
	ImageUri    string    `gorm:"type:varchar(500)" json:"imageUri"`
	CreatedAt   time.Time `json:"createdAt"`
	UpdatedAt   time.Time `json:"updatedAt"`
}

func (TableExample) TableName() string {
	return "sys_table_example"
}

type CardExample struct {
	ID        string    `gorm:"primaryKey;type:varchar(36)" json:"id"`
	Name      string    `gorm:"type:varchar(200)" json:"name"`
	Desc      string    `gorm:"type:varchar(500)" json:"desc"`
	Logo      string    `gorm:"type:varchar(500)" json:"logo"`
	CreatedAt time.Time `json:"createdAt"`
	UpdatedAt time.Time `json:"updatedAt"`
}

func (CardExample) TableName() string {
	return "sys_card_example"
}
