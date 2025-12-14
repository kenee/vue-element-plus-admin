package com.example.admin.service.impl;

import com.example.admin.entity.SysMenu;
import com.example.admin.repository.SysMenuRepository;
import com.example.admin.service.ISysMenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 *
 * @author example
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired
    private SysMenuRepository sysMenuRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SysMenu findById(String id) {
        return sysMenuRepository.selectById(id);
    }

    @Override
    public List<SysMenu> findAll() {
        List<SysMenu> menus = sysMenuRepository.selectList(null);
        // Parse meta JSON for each menu
        for (SysMenu menu : menus) {
            parseMetaJson(menu);
        }
        return menus;
    }

    /**
     * Parse meta JSON string to Map object
     */
    @SuppressWarnings("unchecked")
    private void parseMetaJson(SysMenu menu) {
        if (menu.getMeta() != null && !menu.getMeta().isEmpty()) {
            try {
                Map<String, Object> metaMap = objectMapper.readValue(menu.getMeta(), Map.class);
                menu.setMetaObj(metaMap);
            } catch (Exception e) {
                // If parsing fails, create a basic meta object
                Map<String, Object> metaMap = new HashMap<>();
                if (menu.getTitle() != null) {
                    metaMap.put("title", menu.getTitle());
                }
                if (menu.getIcon() != null) {
                    metaMap.put("icon", menu.getIcon());
                }
                menu.setMetaObj(metaMap);
            }
        } else {
            // Create basic meta object
            Map<String, Object> metaMap = new HashMap<>();
            if (menu.getTitle() != null) {
                metaMap.put("title", menu.getTitle());
            }
            if (menu.getIcon() != null) {
                metaMap.put("icon", menu.getIcon());
            }
            menu.setMetaObj(metaMap);
        }
    }

    @Override
    public SysMenu saveMenu(SysMenu menu) {
        // 生成菜单名称
        if (menu.getName() == null || menu.getName().isEmpty()) {
            menu.setName(generateMenuName(menu.getPath(), menu.getTitle()));
        }
        sysMenuRepository.insert(menu);
        return menu;
    }

    @Override
    public SysMenu updateMenu(SysMenu menu) {
        // 生成菜单名称
        if (menu.getName() == null || menu.getName().isEmpty()) {
            menu.setName(generateMenuName(menu.getPath(), menu.getTitle()));
        }
        sysMenuRepository.updateById(menu);
        return menu;
    }

    @Override
    public void deleteMenu(String id) {
        sysMenuRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<String> ids) {
        sysMenuRepository.deleteBatchIds(ids);
    }

    @Override
    public List<Map<String, Object>> getRoutesByUser(String userId) {
        // 暂时返回所有菜单，后续需要根据用户角色过滤
        List<SysMenu> menus = sysMenuRepository.selectList(null);
        List<Map<String, Object>> routes = convertMenusToRoutes(menus);
        return filterHiddenMenus(routes);
    }

    @Override
    public List<SysMenu> getUserMenus(String userId) {
        // 暂时返回所有菜单，后续需要根据用户角色过滤
        return sysMenuRepository.selectList(null);
    }

    @Override
    public List<Map<String, Object>> convertMenusToRoutes(List<SysMenu> menus) {
        // 将菜单转换为树形结构
        List<Map<String, Object>> treeMenu = buildMenuTree(menus);
        // 将树形菜单转换为路由格式
        return convertTreeToRoutes(treeMenu);
    }

    @Override
    public List<Map<String, Object>> filterHiddenMenus(List<Map<String, Object>> routes) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> route : routes) {
            if (isVisible(route)) {
                // 递归处理子路由
                List<Map<String, Object>> children = (List<Map<String, Object>>) route.get("children");
                if (children != null && !children.isEmpty()) {
                    List<Map<String, Object>> filteredChildren = filterHiddenMenus(children);
                    route.put("children", filteredChildren);
                    // 如果是目录类型且没有子路由，不显示
                    if (filteredChildren.isEmpty() && "directory".equals(route.get("type"))) {
                        continue;
                    }
                    // 处理目录的redirect
                    if ("directory".equals(route.get("type")) && route.get("redirect") == null) {
                        Map<String, Object> firstChild = filteredChildren.get(0);
                        String redirectPath = (String) firstChild.get("path");
                        if (redirectPath != null) {
                            route.put("redirect", redirectPath);
                        }
                    }
                } else {
                    // 处理目录的redirect（没有子路由的情况）
                    if ("directory".equals(route.get("type")) && route.get("redirect") == null) {
                        // 如果没有子路由，不需要设置redirect
                    }
                }
                result.add(route);
            }
        }
        return result;
    }

    @Override
    public String generateMenuName(String path, String title) {
        if (path == null || path.isEmpty()) {
            return title != null ? title.replaceAll("\\s+", "") : "";
        }
        // 从路径生成菜单名称，例如 /system/user -> SystemUser
        String[] parts = path.split("/");
        StringBuilder nameBuilder = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                nameBuilder.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1));
            }
        }
        return nameBuilder.toString();
    }

    /**
     * 构建菜单树形结构
     */
    private List<Map<String, Object>> buildMenuTree(List<SysMenu> menus) {
        Map<String, Map<String, Object>> menuMap = new HashMap<>();
        List<Map<String, Object>> rootMenus = new ArrayList<>();

        // 将所有菜单转换为Map并放入menuMap
        for (SysMenu menu : menus) {
            Map<String, Object> menuNode = convertMenuToMap(menu);
            menuMap.put(menu.getId(), menuNode);
        }

        // 构建树形结构
        for (Map.Entry<String, Map<String, Object>> entry : menuMap.entrySet()) {
            Map<String, Object> menuNode = entry.getValue();
            String parentId = (String) menuNode.get("parentId");
            if (parentId == null || parentId.isEmpty() || "0".equals(parentId)) {
                // 根菜单
                rootMenus.add(menuNode);
            } else {
                // 子菜单，添加到父菜单的children中
                Map<String, Object> parentMenu = menuMap.get(parentId);
                if (parentMenu != null) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) parentMenu
                            .computeIfAbsent("children", k -> new ArrayList<>());
                    children.add(menuNode);
                }
            }
        }

        // 按sort字段排序
        sortMenus(rootMenus);

        return rootMenus;
    }

    /**
     * 将菜单转换为Map格式
     */
    private Map<String, Object> convertMenuToMap(SysMenu menu) {
        Map<String, Object> menuMap = new HashMap<>();
        menuMap.put("id", menu.getId());
        menuMap.put("path", menu.getPath());
        menuMap.put("component", menu.getComponent());
        menuMap.put("redirect", menu.getRedirect());
        menuMap.put("name", menu.getName());
        menuMap.put("title", menu.getTitle());
        menuMap.put("icon", menu.getIcon());
        menuMap.put("type", getMenuType(menu.getType()));
        menuMap.put("status", menu.getStatus());
        menuMap.put("permission", menu.getPermission());
        menuMap.put("sort", menu.getSort());
        menuMap.put("parentId", menu.getParentId());

        // 处理meta属性
        Map<String, Object> metaMap = new HashMap<>();
        metaMap.put("title", menu.getTitle());
        metaMap.put("icon", menu.getIcon());
        if (menu.getPermission() != null) {
            metaMap.put("permission", menu.getPermission());
        }
        // 从menu.meta JSON字符串中获取其他属性
        if (menu.getMeta() != null && !menu.getMeta().isEmpty()) {
            try {
                Map<String, Object> additionalMeta = objectMapper.readValue(menu.getMeta(), Map.class);
                metaMap.putAll(additionalMeta);
            } catch (Exception e) {
                // 解析失败，忽略
                e.printStackTrace();
            }
        }
        menuMap.put("meta", metaMap);

        return menuMap;
    }

    /**
     * 将菜单类型转换为字符串
     */
    private String getMenuType(Integer type) {
        if (type == null) {
            return "menu";
        }
        switch (type) {
            case 0:
                return "directory";
            case 1:
                return "menu";
            case 2:
                return "button";
            default:
                return "menu";
        }
    }

    /**
     * 按sort字段排序菜单
     */
    private void sortMenus(List<Map<String, Object>> menus) {
        menus.sort(Comparator.comparingInt(menu -> {
            Object sortObj = menu.get("sort");
            return sortObj instanceof Integer ? (Integer) sortObj : 0;
        }));
        // 递归排序子菜单
        for (Map<String, Object> menu : menus) {
            List<Map<String, Object>> children = (List<Map<String, Object>>) menu.get("children");
            if (children != null && !children.isEmpty()) {
                sortMenus(children);
            }
        }
    }

    /**
     * 将树形菜单转换为路由格式
     */
    private List<Map<String, Object>> convertTreeToRoutes(List<Map<String, Object>> treeMenus) {
        List<Map<String, Object>> routes = new ArrayList<>();
        for (Map<String, Object> menu : treeMenus) {
            Map<String, Object> route = new HashMap<>();
            route.put("path", menu.get("path"));
            route.put("name", menu.get("name"));
            route.put("component", menu.get("component"));
            route.put("redirect", menu.get("redirect"));
            route.put("meta", menu.get("meta"));
            route.put("type", menu.get("type"));

            // 处理子路由
            List<Map<String, Object>> children = (List<Map<String, Object>>) menu.get("children");
            if (children != null && !children.isEmpty()) {
                List<Map<String, Object>> childRoutes = convertTreeToRoutes(children);
                route.put("children", childRoutes);
            }

            routes.add(route);
        }
        return routes;
    }

    /**
     * 判断菜单是否可见
     */
    private boolean isVisible(Map<String, Object> route) {
        // 检查status字段
        Object statusObj = route.get("status");
        if (statusObj instanceof Integer && (Integer) statusObj == 0) {
            return false;
        }

        // 检查meta中的hidden字段
        Map<String, Object> meta = (Map<String, Object>) route.get("meta");
        if (meta != null && Boolean.TRUE.equals(meta.get("hidden"))) {
            return false;
        }

        return true;
    }

}