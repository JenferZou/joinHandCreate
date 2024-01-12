package com.atxbai.online.service;

import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.model.vo.teacher.AgreeDelieverReqVO;
import com.atxbai.online.model.vo.teacher.GetDelieverReqVO;
import com.atxbai.online.model.vo.teacher.RefuseDelieverReqVO;
import com.atxbai.online.model.vo.teacher.SearchNameRDReqVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-11 16:07
 * @content:
 */
public interface TeacherService {
    PageResponse getDeliever(GetDelieverReqVO getDelieverReqVO, String header);

    PageResponse searchRefuseDeliever(SearchNameRDReqVO searchNameRDReqVO, String header);

    /**
     * 拒绝学生申请
     * @param refuseDelieverReqVO
     * @param header
     * @return
     */
    Response refuseDeliever(RefuseDelieverReqVO refuseDelieverReqVO, String header);

    Response agreeDeliever(AgreeDelieverReqVO agreeDelieverReqVO, String header);
}
