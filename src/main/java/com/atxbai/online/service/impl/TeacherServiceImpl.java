package com.atxbai.online.service.impl;

import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.mapper.DelieverResumeMapper;
import com.atxbai.online.mapper.TeacherMapper;
import com.atxbai.online.model.pojo.DelieverResume;
import com.atxbai.online.model.vo.teacher.*;
import com.atxbai.online.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-11 16:15
 * @content:
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private DelieverResumeMapper delieverResumeMapper;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    /**
     * 查询全部申请
     * @param getDelieverReqVO
     * @param header
     * @return
     */
    @Override
    public PageResponse getDeliever(GetDelieverReqVO getDelieverReqVO, String header) {
        // 解析 token
        String token = StringUtils.substring(header, 7);
        String tno = jwtTokenHelper.getUsernameByToken(token);
        // 获取 分页数据
        String page = getDelieverReqVO.getPage();
        String limit = getDelieverReqVO.getLimit();
        Page<DelieverResume> pageObj = new Page<>(Long.parseLong(page), Long.parseLong(limit));
        // 设置条件
        LambdaQueryWrapper<DelieverResume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DelieverResume::getTno,tno);
        wrapper.eq(DelieverResume::getMark,0);
        // 查询
        Page<DelieverResume> delieverResumePage = delieverResumeMapper.selectPage(pageObj, wrapper);
        List<DelieverResume> list = delieverResumePage.getRecords();
        // 将 DO --> VO
        List<GetDelieverRspVO> getDelieverRspVOS = null;
        // 判断 list 是否为空
        if (Objects.nonNull(list)){
            getDelieverRspVOS = list.stream().map(rd -> GetDelieverRspVO
                    .builder()
                    .pid(rd.getPid())
                    .sno(rd.getSno())
                    .sName(rd.getSName())
                    .projectName(rd.getProjectName())
                    .smajor(rd.getSMajor())
                    .content(rd.getContent())
                    .build()
            ).collect(Collectors.toList());
        }
        return PageResponse.success(delieverResumePage, getDelieverRspVOS);
    }

    /**
     * 根据学生姓名查询申请简历
     * @param searchNameRDReqVO
     * @param header
     * @return
     */
    @Override
    public PageResponse searchRefuseDeliever(SearchNameRDReqVO searchNameRDReqVO, String header) {
        // 解析 token
        String token = StringUtils.substring(header, 7);
        String tno = jwtTokenHelper.getUsernameByToken(token);
        // 获取 分页数据
        String page = searchNameRDReqVO.getPage();
        String limit = searchNameRDReqVO.getLimit();
        String searchName = searchNameRDReqVO.getSearchName();
        Page<DelieverResume> pageObj = new Page<>(Long.parseLong(page), Long.parseLong(limit));
        // 设置条件
        LambdaQueryWrapper<DelieverResume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DelieverResume::getTno,tno);
        wrapper.eq(DelieverResume::getMark,0);
        // 设置模糊查询
        wrapper.like(StringUtils.isNotBlank(searchName.trim()),DelieverResume::getSName,searchName);
        // 查询
        Page<DelieverResume> delieverResumePage = delieverResumeMapper.selectPage(pageObj, wrapper);
        List<DelieverResume> list = delieverResumePage.getRecords();
        // 将 DO --> VO
        List<GetDelieverRspVO> getDelieverRspVOS = null;
        // 判断 list 是否为空
        if (Objects.nonNull(list)){
            getDelieverRspVOS = list.stream().map(rd -> GetDelieverRspVO
                    .builder()
                    .pid(rd.getPid())
                    .sno(rd.getSno())
                    .sName(rd.getSName())
                    .projectName(rd.getProjectName())
                    .smajor(rd.getSMajor())
                    .content(rd.getContent())
                    .build()
            ).collect(Collectors.toList());
        }
        return PageResponse.success(delieverResumePage, getDelieverRspVOS);
    }

    @Override
    public Response refuseDeliever(RefuseDelieverReqVO refuseDelieverReqVO, String header) {
        // 解析 token
        String token = StringUtils.substring(header, 7);
        String tno = jwtTokenHelper.getUsernameByToken(token);
        // 获取参数
        int pid = refuseDelieverReqVO.getPid();
        String sno = refuseDelieverReqVO.getSno();
        // 查询对象
        LambdaQueryWrapper<DelieverResume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DelieverResume::getTno,tno);
        wrapper.eq(DelieverResume::getPid,pid);
        wrapper.eq(DelieverResume::getSno,sno);
        // 要判断一下是没有审批的对象
        wrapper.ge(DelieverResume::getMark,0);
        // 查询对象
        DelieverResume delieverResume = delieverResumeMapper.selectOne(wrapper);
        // 设置拒绝
         delieverResume.setMark(-1);
        // 更新数据,同时设置更新条件
        int update = delieverResumeMapper.update(delieverResume, wrapper);
        // 返回
        return update == 1 ? Response.success() : Response.fail();
    }

    @Override
    public Response agreeDeliever(AgreeDelieverReqVO agreeDelieverReqVO, String header) {
        // 解析 token
        String token = StringUtils.substring(header, 7);
        String tno = jwtTokenHelper.getUsernameByToken(token);
        // 获取参数
        int pid = agreeDelieverReqVO.getPid();
        String sno = agreeDelieverReqVO.getSno();
        // 查询对象
        LambdaQueryWrapper<DelieverResume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DelieverResume::getTno,tno);
        wrapper.eq(DelieverResume::getPid,pid);
        wrapper.eq(DelieverResume::getSno,sno);
        // 要判断一下是没有审批的对象
        wrapper.ge(DelieverResume::getMark,0);
        // 查询对象
        DelieverResume delieverResume = delieverResumeMapper.selectOne(wrapper);
        // 设置拒绝
        delieverResume.setMark(1);
        // 更新数据,同时设置更新条件
        int update = delieverResumeMapper.update(delieverResume, wrapper);
        // 返回
        return update == 1 ? Response.success() : Response.fail();
    }
}
