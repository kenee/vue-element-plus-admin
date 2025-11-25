package biz

import (
	"encoding/json"
	"gin-admin-backend/internal/data"

	"github.com/google/uuid"
)

type MenuService struct{}

func NewMenuService() *MenuService {
	return &MenuService{}
}

func (s *MenuService) Create(menu *data.Menu) error {
	menu.ID = uuid.New().String()
	return data.DB.Create(menu).Error
}

func (s *MenuService) Update(id string, menu *data.Menu) error {
	return data.DB.Model(&data.Menu{}).Where("id = ?", id).Updates(menu).Error
}

func (s *MenuService) Delete(id string) error {
	return data.DB.Delete(&data.Menu{}, "id = ?", id).Error
}

type MenuListResponse struct {
	List  []data.Menu `json:"list"`
	Total int         `json:"total"`
}

func (s *MenuService) List() (*MenuListResponse, error) {
	var menus []data.Menu
	err := data.DB.Order("sort").Find(&menus).Error
	if err != nil {
		return nil, err
	}

	return &MenuListResponse{
		List:  menus,
		Total: len(menus),
	}, nil
}

func (s *MenuService) GetUserMenus(userId string) ([]data.Menu, error) {
	// Simple implementation: return all menus for now, or filter by role if needed
	// In a real app, we would join User -> Role -> Menu
	// For now, let's just return all menus as the requirement is a rewrite, assuming admin has access to everything or similar logic
	// But let's try to do it right if we can.

	var user data.User
	if err := data.DB.Preload("Roles.Menus").First(&user, "id = ?", userId).Error; err != nil {
		return nil, err
	}

	// Collect menus from all roles
	menuMap := make(map[string]data.Menu)
	for _, role := range user.Roles {
		for _, menu := range role.Menus {
			menuMap[menu.ID] = menu
		}
	}

	var menus []data.Menu
	for _, menu := range menuMap {
		menus = append(menus, menu)
	}

	// If no menus found (e.g. admin), maybe return all?
	// For this task, let's stick to the roles.

	return menus, nil
}

// RouteItem represents a frontend route structure
type RouteItem struct {
	Name      string                 `json:"name"`
	Path      string                 `json:"path"`
	Component string                 `json:"component"`
	Redirect  string                 `json:"redirect,omitempty"`
	Meta      map[string]interface{} `json:"meta"`
	Children  []RouteItem            `json:"children,omitempty"`
}

// GetRoutesByUser returns routes formatted for frontend based on user's role permissions
func (s *MenuService) GetRoutesByUser(userId string) ([]RouteItem, error) {
	// Get user with roles and menus
	var user data.User
	if err := data.DB.Preload("Roles.Menus").First(&user, "id = ?", userId).Error; err != nil {
		return []RouteItem{}, nil
	}

	// Collect unique menu IDs from all roles (only enabled menus)
	menuIDSet := make(map[string]bool)
	for _, role := range user.Roles {
		for _, menu := range role.Menus {
			if menu.Status == 1 { // Only enabled menus
				menuIDSet[menu.ID] = true
			}
		}
	}

	if len(menuIDSet) == 0 {
		return []RouteItem{}, nil
	}

	// Get menu IDs as slice
	menuIDs := make([]string, 0, len(menuIDSet))
	for id := range menuIDSet {
		menuIDs = append(menuIDs, id)
	}

	// Fetch menus from database
	var menus []data.Menu
	if err := data.DB.Where("id IN ? AND status = ?", menuIDs, 1).Order("sort ASC, created_at ASC").Find(&menus).Error; err != nil {
		return nil, err
	}

	// Convert to route structure
	return s.convertMenusToRoutes(menus), nil
}

// routeNode is an internal structure used during tree construction
type routeNode struct {
	Name      string
	Path      string
	Component string
	Redirect  string
	Meta      map[string]interface{}
	Children  []*routeNode
}

// convertMenusToRoutes converts database menus to frontend route format
func (s *MenuService) convertMenusToRoutes(menus []data.Menu) []RouteItem {
	menuMap := make(map[string]*routeNode)
	nameSet := make(map[string]bool)
	var rootNodes []*routeNode

	// First pass: create all route nodes
	for _, menu := range menus {
		// Build meta object
		meta := make(map[string]interface{})
		if menu.Meta != "" {
			// Parse JSON meta string
			var metaMap map[string]interface{}
			if err := json.Unmarshal([]byte(menu.Meta), &metaMap); err == nil {
				meta = metaMap
			}
		}

		// Ensure title exists
		if _, ok := meta["title"]; !ok {
			meta["title"] = menu.Title
		}

		// Add icon if exists
		if menu.Icon != "" {
			if _, ok := meta["icon"]; !ok {
				meta["icon"] = menu.Icon
			}
		}

		// Handle permissions
		if menu.Permission != "" {
			// Split comma-separated permissions
			permissions := []string{}
			for _, p := range splitAndTrim(menu.Permission, ",") {
				if p != "" {
					permissions = append(permissions, p)
				}
			}
			if len(permissions) > 0 {
				meta["permission"] = permissions
			}
		}

		// Generate unique name
		routeName := menu.Name
		if routeName == "" {
			routeName = generateMenuName(menu.Path, menu.Title)
		}

		// Ensure name uniqueness
		uniqueName := routeName
		counter := 1
		for nameSet[uniqueName] {
			uniqueName = routeName + string(rune('0'+counter))
			counter++
		}
		nameSet[uniqueName] = true

		node := &routeNode{
			Name:      uniqueName,
			Path:      menu.Path,
			Component: menu.Component,
			Redirect:  menu.Redirect,
			Meta:      meta,
			Children:  []*routeNode{},
		}

		menuMap[menu.ID] = node
	}

	// Second pass: build tree structure
	for _, menu := range menus {
		node := menuMap[menu.ID]
		if menu.ParentID != "" {
			// Has parent
			if parent, ok := menuMap[menu.ParentID]; ok {
				parent.Children = append(parent.Children, node)
			}
		} else {
			// Root node
			rootNodes = append(rootNodes, node)
		}
	}

	// Convert node tree to RouteItem tree
	result := convertNodesToRoutes(rootNodes)

	// Filter hidden menus and set redirects
	return filterHiddenMenus(result)
}

// convertNodesToRoutes recursively converts routeNode tree to RouteItem tree
func convertNodesToRoutes(nodes []*routeNode) []RouteItem {
	result := make([]RouteItem, len(nodes))
	for i, node := range nodes {
		route := RouteItem{
			Name:      node.Name,
			Path:      node.Path,
			Component: node.Component,
			Redirect:  node.Redirect,
			Meta:      node.Meta,
			Children:  []RouteItem{},
		}

		// Recursively convert children
		if len(node.Children) > 0 {
			route.Children = convertNodesToRoutes(node.Children)
		}

		result[i] = route
	}
	return result
}

// Helper functions
func splitAndTrim(s, sep string) []string {
	parts := []string{}
	for _, part := range splitString(s, sep) {
		trimmed := trimString(part)
		if trimmed != "" {
			parts = append(parts, trimmed)
		}
	}
	return parts
}

func splitString(s, sep string) []string {
	if s == "" {
		return []string{}
	}
	result := []string{}
	current := ""
	for _, c := range s {
		if string(c) == sep {
			result = append(result, current)
			current = ""
		} else {
			current += string(c)
		}
	}
	result = append(result, current)
	return result
}

func trimString(s string) string {
	start := 0
	end := len(s)
	for start < end && (s[start] == ' ' || s[start] == '\t' || s[start] == '\n') {
		start++
	}
	for end > start && (s[end-1] == ' ' || s[end-1] == '\t' || s[end-1] == '\n') {
		end--
	}
	return s[start:end]
}

func generateMenuName(path, title string) string {
	if path == "" {
		if title != "" {
			return title
		}
		return "Menu"
	}

	// Remove leading/trailing slashes
	cleanPath := path
	for len(cleanPath) > 0 && cleanPath[0] == '/' {
		cleanPath = cleanPath[1:]
	}
	for len(cleanPath) > 0 && cleanPath[len(cleanPath)-1] == '/' {
		cleanPath = cleanPath[:len(cleanPath)-1]
	}

	if cleanPath == "" {
		return title
	}

	// Convert to PascalCase
	parts := splitString(cleanPath, "/")
	result := ""
	for _, part := range parts {
		if len(part) > 0 {
			// Uppercase first character
			firstChar := part[0]
			if firstChar >= 'a' && firstChar <= 'z' {
				firstChar = firstChar - 32
			}
			result += string(firstChar) + part[1:]
		}
	}

	if result == "" {
		return title
	}
	return result
}

func filterHiddenMenus(routes []RouteItem) []RouteItem {
	filtered := []RouteItem{}

	for _, route := range routes {
		// Check if hidden
		if hidden, ok := route.Meta["hidden"].(bool); ok && hidden {
			continue
		}

		// Process children recursively
		if len(route.Children) > 0 {
			route.Children = filterHiddenMenus(route.Children)

			// Set redirect if not set and has children
			if route.Redirect == "" && len(route.Children) > 0 {
				firstChild := route.Children[0]
				childPath := firstChild.Path
				if len(childPath) > 0 && childPath[0] != '/' {
					childPath = route.Path + "/" + childPath
				}
				route.Redirect = childPath
			}
		}

		filtered = append(filtered, route)
	}

	return filtered
}
