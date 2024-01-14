package com.atxbai.online.service.impl;

import com.atxbai.online.mapper.ManagerMapper;
import com.atxbai.online.model.pojo.Manager;
import com.atxbai.online.model.pojo.Project;
import com.atxbai.online.service.ManagerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public Map<String, Object> listManager(int page, int limit, String keyword) {
        QueryWrapper<Manager> wrapper = new QueryWrapper<>();
        if (keyword != null && !"".equals(keyword) && keyword.length() > 0) {
            wrapper.like("name", keyword);
        }
        Long count = managerMapper.selectCount(wrapper);
        // 创建分页对象
        Page<Manager> p = new Page<>(page, limit, count);
        // 执行分页查询
        IPage<Manager> managerIPage = managerMapper.selectPage(p, wrapper);
        // 获取查询结果
        List<Manager> managers = managerIPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("managers", managers);
        map.put("total", managerIPage.getTotal());
        map.put("size", managerIPage.getSize());
        map.put("page", managerIPage.getPages());
        return map;
    }

    @Override
    public boolean deleteManager(Integer id) {
        return managerMapper.deleteById(id) >= 0;
    }
}
