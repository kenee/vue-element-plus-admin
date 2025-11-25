package main

import (
	"encoding/json"
	"fmt"
	"gin-admin-backend/internal/conf"
	"gin-admin-backend/internal/data"
	"gin-admin-backend/pkg/utils"
	"strings"

	"github.com/google/uuid"
	"gorm.io/gorm"
)

func main() {
	// 1. Init Config
	conf.InitConfig()

	// 2. Init DB
	data.InitDB()

	// 3. Seed Data
	seedRoles()
	seedDepartments()
	seedMenus()
	seedDictionaries()
	seedAdminUser()
}

func seedRoles() {
	roles := []data.Role{
		{RoleName: "Super Admin", RoleValue: "admin", Status: 1, Remark: "Super Administrator with full access"},
		{RoleName: "General User", RoleValue: "user", Status: 1, Remark: "Standard user with limited access"},
	}

	for _, r := range roles {
		var count int64
		data.DB.Model(&data.Role{}).Where("role_value = ?", r.RoleValue).Count(&count)
		if count == 0 {
			r.ID = uuid.New().String()
			data.DB.Create(&r)
			fmt.Printf("Role created: %s\n", r.RoleName)
		} else {
			fmt.Printf("Role already exists: %s\n", r.RoleName)
		}
	}
}

func seedDepartments() {
	cities := []string{"厦门总公司", "北京分公司", "上海分公司", "福州分公司", "深圳分公司", "杭州分公司"}
	subDepts := []string{"研发部", "产品部", "运营部", "市场部", "销售部", "客服部"}

	for i, city := range cities {
		if i >= 5 {
			break
		}
		var parent data.Department
		// Check by name. Assuming top level departments have empty parent_id or null.
		// We check for name match to be consistent with TS logic which checks existing names.
		result := data.DB.Where("name = ?", city).First(&parent)
		if result.Error == gorm.ErrRecordNotFound {
			parent = data.Department{
				ID:     uuid.New().String(),
				Name:   city,
				Sort:   i + 1,
				Status: 1,
				Remark: city + "的备注信息",
			}
			data.DB.Create(&parent)
			fmt.Printf("Parent department created: %s\n", city)
		} else {
			fmt.Printf("Department already exists: %s\n", city)
		}

		for j, sub := range subDepts {
			var count int64
			data.DB.Model(&data.Department{}).Where("name = ? AND parent_id = ?", sub, parent.ID).Count(&count)
			if count == 0 {
				subDept := data.Department{
					ID:       uuid.New().String(),
					Name:     sub,
					ParentID: parent.ID,
					Sort:     j + 1,
					Status:   1, // Random status in original, setting to 1 for simplicity
					Remark:   sub + "的备注信息",
				}
				data.DB.Create(&subDept)
				fmt.Printf("  └─ Sub department created: %s\n", sub)
			}
		}
	}
}

type PermissionItem struct {
	Label string
	Value string
}

type MockMenu struct {
	Path           string
	Component      string
	Redirect       string
	Name           string
	Title          string
	Type           int
	Status         *int
	Meta           map[string]interface{}
	Children       []MockMenu
	PermissionList []PermissionItem
}

func seedMenus() {
	// Define menus based on seed.ts
	menus := []MockMenu{
		{
			Path:      "/dashboard",
			Component: "#",
			Redirect:  "/dashboard/analysis",
			Name:      "Dashboard",
			Title:     "首页",
			Type:      0,
			Meta:      map[string]interface{}{"title": "首页", "icon": "vi-ant-design:dashboard-filled", "alwaysShow": true},
			Children: []MockMenu{
				{
					Path:      "analysis",
					Component: "views/Dashboard/Analysis",
					Name:      "Analysis",
					Title:     "分析页",
					Type:      1,
					Meta:      map[string]interface{}{"title": "分析页", "noCache": true, "permission": []string{"add", "edit"}},
					PermissionList: []PermissionItem{
						{Label: "新增", Value: "add"},
						{Label: "编辑", Value: "edit"},
					},
				},
				{
					Path:      "workplace",
					Component: "views/Dashboard/Workplace",
					Name:      "Workplace",
					Title:     "工作台",
					Type:      1,
					Meta:      map[string]interface{}{"title": "工作台", "noCache": true},
					PermissionList: []PermissionItem{
						{Label: "新增", Value: "add"},
						{Label: "编辑", Value: "edit"},
						{Label: "删除", Value: "delete"},
					},
				},
			},
		},
		{
			Path:      "/authorization",
			Component: "#",
			Redirect:  "/authorization/user",
			Name:      "Authorization",
			Title:     "权限管理",
			Type:      0,
			Meta:      map[string]interface{}{"title": "权限管理", "icon": "vi-eos-icons:role-binding", "alwaysShow": true},
			Children: []MockMenu{
				{
					Path:      "department",
					Component: "views/Authorization/Department/Department",
					Name:      "Department",
					Title:     "部门管理",
					Type:      1,
					Meta:      map[string]interface{}{"title": "部门管理"},
					PermissionList: []PermissionItem{
						{Label: "新增", Value: "add"},
						{Label: "编辑", Value: "edit"},
						{Label: "删除", Value: "delete"},
					},
				},
				{
					Path:      "user",
					Component: "views/Authorization/User/User",
					Name:      "User",
					Title:     "用户管理",
					Type:      1,
					Meta:      map[string]interface{}{"title": "用户管理"},
					PermissionList: []PermissionItem{
						{Label: "新增", Value: "add"},
						{Label: "编辑", Value: "edit"},
						{Label: "删除", Value: "delete"},
					},
				},
				{
					Path:      "menu",
					Component: "views/Authorization/Menu/Menu",
					Name:      "Menu",
					Title:     "菜单管理",
					Type:      1,
					Meta:      map[string]interface{}{"title": "菜单管理"},
					PermissionList: []PermissionItem{
						{Label: "新增", Value: "add"},
						{Label: "编辑", Value: "edit"},
						{Label: "删除", Value: "delete"},
					},
				},
				{
					Path:      "role",
					Component: "views/Authorization/Role/Role",
					Name:      "Role",
					Title:     "角色管理",
					Type:      1,
					Meta:      map[string]interface{}{"title": "角色管理"},
					PermissionList: []PermissionItem{
						{Label: "新增", Value: "add"},
						{Label: "编辑", Value: "edit"},
						{Label: "删除", Value: "delete"},
					},
				},
			},
		},
		{
			Path:      "/external-link",
			Component: "#",
			Name:      "ExternalLink",
			Title:     "文档",
			Type:      0,
			Meta:      map[string]interface{}{"title": "文档", "icon": "vi-clarity:document-solid"},
			Children: []MockMenu{
				{
					Path:  "https://element-plus-admin-doc.cn/",
					Name:  "DocumentLink",
					Title: "文档",
					Type:  1,
					Meta:  map[string]interface{}{"title": "文档"},
				},
			},
		},
		{
			Path:      "/level",
			Component: "#",
			Redirect:  "/level/menu1/menu1-1/menu1-1-1",
			Name:      "Level",
			Title:     "菜单",
			Type:      0,
			Meta:      map[string]interface{}{"title": "菜单", "icon": "vi-carbon:skill-level-advanced"},
			Children: []MockMenu{
				{
					Path:      "menu1",
					Name:      "Menu1",
					Component: "##",
					Redirect:  "/level/menu1/menu1-1/menu1-1-1",
					Title:     "菜单1",
					Type:      0,
					Meta:      map[string]interface{}{"title": "菜单1"},
					Children: []MockMenu{
						{
							Path:      "menu1-1",
							Name:      "Menu11",
							Component: "##",
							Redirect:  "/level/menu1/menu1-1/menu1-1-1",
							Title:     "菜单1-1",
							Type:      0,
							Meta:      map[string]interface{}{"title": "菜单1-1", "alwaysShow": true},
							Children: []MockMenu{
								{
									Path:      "menu1-1-1",
									Name:      "Menu111",
									Component: "views/Level/Menu111",
									Title:     "菜单1-1-1",
									Type:      1,
									Meta:      map[string]interface{}{"title": "菜单1-1-1"},
								},
							},
						},
						{
							Path:      "menu1-2",
							Name:      "Menu12",
							Component: "views/Level/Menu12",
							Title:     "菜单1-2",
							Type:      1,
							Meta:      map[string]interface{}{"title": "菜单1-2"},
						},
					},
				},
				{
					Path:      "menu2",
					Name:      "Menu2Demo",
					Component: "views/Level/Menu2",
					Title:     "菜单2",
					Type:      1,
					Meta:      map[string]interface{}{"title": "菜单2"},
				},
			},
		},
		{
			Path:      "/example",
			Component: "#",
			Redirect:  "/example/example-dialog",
			Name:      "Example",
			Title:     "综合示例",
			Type:      0,
			Meta:      map[string]interface{}{"title": "综合示例", "icon": "vi-ep:management", "alwaysShow": true},
			Children: []MockMenu{
				{
					Path:      "example-dialog",
					Component: "views/Example/Dialog/ExampleDialog",
					Name:      "ExampleDialog",
					Title:     "综合示例-弹窗",
					Type:      1,
					Meta:      map[string]interface{}{"title": "综合示例-弹窗"},
					PermissionList: []PermissionItem{
						{Label: "新增", Value: "add"},
						{Label: "编辑", Value: "edit"},
						{Label: "删除", Value: "delete"},
						{Label: "查看", Value: "view"},
					},
				},
				{
					Path:      "example-page",
					Component: "views/Example/Page/ExamplePage",
					Name:      "ExamplePage",
					Title:     "综合示例-页面",
					Type:      1,
					Meta:      map[string]interface{}{"title": "综合示例-页面"},
					PermissionList: []PermissionItem{
						{Label: "新增", Value: "add"},
						{Label: "编辑", Value: "edit"},
						{Label: "删除", Value: "delete"},
						{Label: "查看", Value: "view"},
					},
				},
				{
					Path:      "example-add",
					Component: "views/Example/Page/ExampleAdd",
					Name:      "ExampleAdd",
					Title:     "综合示例-新增",
					Type:      1,
					Meta: map[string]interface{}{
						"title":         "综合示例-新增",
						"noTagsView":    true,
						"noCache":       true,
						"hidden":        true,
						"showMainRoute": true,
						"activeMenu":    "/example/example-page",
					},
				},
				{
					Path:      "example-edit",
					Component: "views/Example/Page/ExampleEdit",
					Name:      "ExampleEdit",
					Title:     "综合示例-编辑",
					Type:      1,
					Meta: map[string]interface{}{
						"title":         "综合示例-编辑",
						"noTagsView":    true,
						"noCache":       true,
						"hidden":        true,
						"showMainRoute": true,
						"activeMenu":    "/example/example-page",
					},
				},
				{
					Path:      "example-detail",
					Component: "views/Example/Page/ExampleDetail",
					Name:      "ExampleDetail",
					Title:     "综合示例-详情",
					Type:      1,
					Meta: map[string]interface{}{
						"title":         "综合示例-详情",
						"noTagsView":    true,
						"noCache":       true,
						"hidden":        true,
						"showMainRoute": true,
						"activeMenu":    "/example/example-page",
					},
				},
			},
		},
	}

	var existingMenus []data.Menu
	data.DB.Find(&existingMenus)

	existingMenuIDs := make(map[string]string, len(existingMenus))
	for _, menu := range existingMenus {
		if menu.Path != "" {
			existingMenuIDs[menu.Path] = menu.ID
		}
	}

	for _, m := range menus {
		createMenuRecursive(m, "", 0, existingMenuIDs)
	}
}

func createMenuRecursive(m MockMenu, parentId string, depth int, existing map[string]string) {
	indent := strings.Repeat("  ", depth)
	pathLabel := displayPath(m.Path)

	if m.Path != "" {
		if existingID, ok := existing[m.Path]; ok {
			fmt.Printf("%sMenu already exists: %s (%s)\n", indent, m.Title, pathLabel)
			for _, child := range m.Children {
				createMenuRecursive(child, existingID, depth+1, existing)
			}
			return
		}
	}

	meta := cloneMeta(m.Meta)
	if _, ok := meta["title"]; !ok {
		meta["title"] = m.Title
	}

	icon := ""
	if iconVal, ok := meta["icon"]; ok {
		if iconStr, ok := iconVal.(string); ok {
			icon = iconStr
		}
	}

	metaBytes, err := json.Marshal(meta)
	if err != nil {
		fmt.Printf("%sFailed to marshal meta for menu %s: %v\n", indent, m.Title, err)
		return
	}

	status := 1
	if m.Status != nil {
		status = *m.Status
	}

	menu := data.Menu{
		ID:         uuid.New().String(),
		ParentID:   parentId,
		Path:       m.Path,
		Component:  m.Component,
		Redirect:   m.Redirect,
		Title:      m.Title,
		Name:       m.Name,
		Icon:       icon,
		Meta:       string(metaBytes),
		Type:       m.Type,
		Status:     status,
		Sort:       depth,
		Permission: buildPermissionString(m.PermissionList),
	}

	if err := data.DB.Create(&menu).Error; err != nil {
		fmt.Printf("%sFailed to create menu %s: %v\n", indent, m.Title, err)
		return
	}

	fmt.Printf("%sMenu created: %s (%s)\n", indent, m.Title, pathLabel)

	if m.Path != "" {
		existing[m.Path] = menu.ID
	}

	for _, child := range m.Children {
		createMenuRecursive(child, menu.ID, depth+1, existing)
	}
}

func cloneMeta(src map[string]interface{}) map[string]interface{} {
	if src == nil {
		return map[string]interface{}{}
	}
	dst := make(map[string]interface{}, len(src))
	for k, v := range src {
		dst[k] = v
	}
	return dst
}

func buildPermissionString(list []PermissionItem) string {
	if len(list) == 0 {
		return ""
	}

	perms := make([]string, 0, len(list))
	for _, item := range list {
		val := strings.TrimSpace(item.Value)
		if val != "" {
			perms = append(perms, val)
		}
	}

	return strings.Join(perms, ",")
}

func displayPath(path string) string {
	if path == "" {
		return "N/A"
	}
	return path
}

func seedDictionaries() {
	dicts := []data.Dictionary{
		{DictName: "重要性", DictCode: "importance", Remark: "重要性字典：0-普通，1-良好，2-重要"},
	}

	for _, d := range dicts {
		var count int64
		data.DB.Model(&data.Dictionary{}).Where("dict_code = ?", d.DictCode).Count(&count)
		if count == 0 {
			d.ID = uuid.New().String()
			d.Status = 1
			data.DB.Create(&d)
			fmt.Printf("Dictionary created: %s\n", d.DictName)

			// Seed details for importance
			if d.DictCode == "importance" {
				items := []data.DictionaryItem{
					{Label: "普通", Value: "0", Sort: 0},
					{Label: "良好", Value: "1", Sort: 1},
					{Label: "重要", Value: "2", Sort: 2},
				}
				for _, item := range items {
					item.ID = uuid.New().String()
					item.DictID = d.ID
					item.Status = 1
					data.DB.Create(&item)
				}
			}
		} else {
			fmt.Printf("Dictionary already exists: %s\n", d.DictName)
		}
	}
}

func seedAdminUser() {
	var user data.User
	result := data.DB.Where("username = ?", "admin").First(&user)
	if result.Error == gorm.ErrRecordNotFound {
		// Find the specific department "厦门总公司"
		var dept data.Department
		// Try to find '厦门总公司' first, fallback to any if not found (though it should exist)
		if err := data.DB.Where("name = ?", "厦门总公司").First(&dept).Error; err != nil {
			data.DB.First(&dept)
		}

		password, _ := utils.HashPassword("123456")
		user = data.User{
			ID:       uuid.New().String(),
			Username: "admin",
			Password: password,
			Nickname: "Admin",
			Email:    "admin@example.com",
			Status:   1,
			DeptID:   dept.ID,
		}
		data.DB.Create(&user)
		fmt.Println("User created: admin")
	} else {
		fmt.Println("User already exists: admin")
	}

	// Assign Admin Role
	var role data.Role
	data.DB.Where("role_value = ?", "admin").First(&role)

	// Assign All Menus to Admin Role
	var allMenus []data.Menu
	data.DB.Find(&allMenus)

	data.DB.Model(&role).Association("Menus").Replace(allMenus)
	fmt.Printf("Assigned %d menus to admin role\n", len(allMenus))

	// Assign Role to User
	data.DB.Model(&user).Association("Roles").Replace([]data.Role{role})
	fmt.Println("Assigned admin role to admin user")
}
