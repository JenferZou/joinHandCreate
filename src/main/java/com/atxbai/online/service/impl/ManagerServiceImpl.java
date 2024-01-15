package com.atxbai.online.service.impl;

import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.mapper.LoginMapper;
import com.atxbai.online.mapper.ManagerMapper;
import com.atxbai.online.mapper.StudentMapper;
import com.atxbai.online.mapper.TeacherMapper;
import com.atxbai.online.model.pojo.Login;
import com.atxbai.online.model.pojo.Manager;
import com.atxbai.online.model.pojo.Project;
import com.atxbai.online.model.vo.admin.DAURspVO;
import com.atxbai.online.model.vo.admin.STFRspVO;
import com.atxbai.online.service.ManagerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

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

    @Override
    public Response dauList() {
        // 获取当前时间
        LocalDate now = LocalDate.now();
        DAURspVO dauRspVO = new DAURspVO();
        // 使用循环
        for (int i = 6;i >= 0;i--){
            LocalDate date = now.minusDays(i);
            // 设置数据
            dauRspVO.getLocalDates().add(date);
            // 查询数据
            Long aLong = loginMapper.selectCount(Wrappers.<Login>lambdaQuery().eq(Login::getLoginTime, date));
            // 设置数据
            dauRspVO.getNums().add(aLong);
        }
        return Response.success(dauRspVO);
    }

    @Override
    public Response stfList() {
        // 查询学生数量
        Long studentNum = studentMapper.selectCount(Wrappers.emptyWrapper());
        // 查询教师数量
        Long teacherNum = teacherMapper.selectCount(Wrappers.emptyWrapper());
        // 返回
        return Response.success(STFRspVO.builder().studentNum(studentNum).teacherNum(teacherNum).build());
    }
}
