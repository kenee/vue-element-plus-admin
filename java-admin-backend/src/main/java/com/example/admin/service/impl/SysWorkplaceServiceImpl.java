package com.example.admin.service.impl;

import com.example.admin.service.ISysWorkplaceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作台服务实现类
 *
 * @author example
 */
@Service
public class SysWorkplaceServiceImpl implements ISysWorkplaceService {

    @Override
    public Map<String, Object> getTotal() {
        Map<String, Object> result = new HashMap<>();
        result.put("project", 40);
        result.put("access", 2340);
        result.put("todo", 10);
        return result;
    }

    @Override
    public List<Map<String, Object>> getProject() {
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(createProjectMap("Github", "akar-icons:github-fill", "workplace.introduction", "Archer"));
        result.add(createProjectMap("Vue", "logos:vue", "workplace.introduction", "Archer"));
        result.add(createProjectMap("Angular", "logos:angular-icon", "workplace.introduction", "Archer"));
        result.add(createProjectMap("React", "logos:react", "workplace.introduction", "Archer"));
        result.add(createProjectMap("Webpack", "logos:webpack", "workplace.introduction", "Archer"));
        result.add(createProjectMap("Vite", "vscode-icons:file-type-vite", "workplace.introduction", "Archer"));
        return result;
    }

    @Override
    public List<Map<String, Object>> getDynamic() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Map<String, Object> dynamic = new HashMap<>();
            List<String> keys = new ArrayList<>();
            keys.add("workplace.push");
            keys.add("Github");
            dynamic.put("keys", keys);
            dynamic.put("time", LocalDateTime.now());
            result.add(dynamic);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getTeam() {
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(createTeamMap("Github", "akar-icons:github-fill"));
        result.add(createTeamMap("Vue", "logos:vue"));
        result.add(createTeamMap("Angular", "logos:angular-icon"));
        result.add(createTeamMap("React", "logos:react"));
        result.add(createTeamMap("Webpack", "logos:webpack"));
        result.add(createTeamMap("Vite", "vscode-icons:file-type-vite"));
        return result;
    }

    @Override
    public List<Map<String, Object>> getRadar() {
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(createRadarMap("workplace.quote", 65, 42, 50));
        result.add(createRadarMap("workplace.contribution", 160, 30, 140));
        result.add(createRadarMap("workplace.hot", 300, 20, 28));
        result.add(createRadarMap("workplace.yield", 130, 35, 35));
        result.add(createRadarMap("workplace.follow", 100, 80, 90));
        return result;
    }

    /**
     * 创建项目Map
     */
    private Map<String, Object> createProjectMap(String name, String icon, String message, String personal) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("icon", icon);
        map.put("message", message);
        map.put("personal", personal);
        map.put("time", LocalDateTime.now());
        return map;
    }

    /**
     * 创建团队Map
     */
    private Map<String, Object> createTeamMap(String name, String icon) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("icon", icon);
        return map;
    }

    /**
     * 创建雷达图数据Map
     */
    private Map<String, Object> createRadarMap(String name, int max, int personal, int team) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("max", max);
        map.put("personal", personal);
        map.put("team", team);
        return map;
    }

}