package com.example.admin.service;

import com.example.admin.entity.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * 菜单服务接口
 *
 * @author example
 */
public interface ISysMenuService {

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return SysMenu
     */
    SysMenu findById(String id);

    /**
     * 查询所有菜单
     *
     * @return List<SysMenu>
     */
    List<SysMenu> findAll();

    /**
     * 保存菜单
     *
     * @param menu 菜单信息
     * @return SysMenu
     */
    SysMenu saveMenu(SysMenu menu);

    /**
     * 更新菜单
     *
     * @param menu 菜单信息
     * @return SysMenu
     */
    SysMenu updateMenu(SysMenu menu);

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     */
    void deleteMenu(String id);

    /**
     * 批量删除菜单
     *
     * @param ids 菜单ID列表
     */
    void deleteBatch(List<String> ids);

    /**
     * 根据用户ID获取路由
     *
     * @param userId 用户ID
     * @return 路由列表
     */
    List<Map<String, Object>> getRoutesByUser(String userId);

    /**
     * 将菜单转换为路由格式
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<Map<String, Object>> convertMenusToRoutes(List<SysMenu> menus);

    /**
     * 过滤隐藏菜单
     *
     * @param routes 路由列表
     * @return 过滤后的路由列表
     */
    List<Map<String, Object>> filterHiddenMenus(List<Map<String, Object>> routes);

    /**
     * 生成菜单名称
     *
     * @param path 路径
     * @param title 标题
     * @return 菜单名称
     */
    String generateMenuName(String path, String title);

}