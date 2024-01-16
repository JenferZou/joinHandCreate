package com.atxbai.online.service;

import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.model.pojo.DelieverResume;
import com.atxbai.online.model.vo.UpdateResumeMarkReqVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DelieverResumeService extends IService<DelieverResume> {

    PageResponse<DelieverResume> getdelieverResumeBysno(String userToken, Integer pageNo, Integer pageSize);


    Integer updateMakrById(UpdateResumeMarkReqVo updateResumeMarkReqVo);
}
