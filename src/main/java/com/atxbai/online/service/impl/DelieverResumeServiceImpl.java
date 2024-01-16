package com.atxbai.online.service.impl;

import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.common.textUtils.HtmlFilterHelper;
import com.atxbai.online.mapper.DelieverResumeMapper;
import com.atxbai.online.model.pojo.DelieverResume;
import com.atxbai.online.service.DelieverResumeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DelieverResumeServiceImpl extends ServiceImpl<DelieverResumeMapper, DelieverResume> implements DelieverResumeService {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public PageResponse<DelieverResume> getdelieverResumeBysno(String userToken, Integer pageNo, Integer pageSize) {
        String token = StringUtils.substring(userToken, 7);
        String sno = jwtTokenHelper.getUsernameByToken(token);
        Page<DelieverResume> delieverResumePage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        LambdaQueryWrapper<DelieverResume> resumeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        resumeLambdaQueryWrapper.eq(DelieverResume::getSno,sno).orderByDesc(DelieverResume::getId);
        IPage<DelieverResume> pageInfo = page(delieverResumePage, resumeLambdaQueryWrapper);
        List<DelieverResume> records = pageInfo.getRecords();
        records = records.stream().map(delieverResume -> {
            String content = HtmlFilterHelper.getContent(delieverResume.getContent());
            delieverResume.setContent(content);
            return delieverResume;
        }).collect(Collectors.toList());
        return PageResponse.success(pageInfo, records);
    }
}
