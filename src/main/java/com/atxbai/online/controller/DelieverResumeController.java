package com.atxbai.online.controller;

import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.model.pojo.DelieverResume;
import com.atxbai.online.service.DelieverResumeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delieverResume")
@Api(tags = "投递简历模块")
public class DelieverResumeController {

    @Autowired
    private DelieverResumeService delieverResumeService;

    @ApiOperation(value = "根据pid获取投递的简历")
    @GetMapping("/getdelieverResumeByPid")
    //{pid}/{pageNo}/{pageSize}
    public PageResponse<DelieverResume> getdelieverResumeByPid(@RequestParam("pid") Integer pid,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        Page<DelieverResume> delieverResumePage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        LambdaQueryWrapper<DelieverResume> resumeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        resumeLambdaQueryWrapper.eq(DelieverResume::getPid, pid);
        IPage<DelieverResume> page = delieverResumeService.page(delieverResumePage, resumeLambdaQueryWrapper);
        return PageResponse.success(page, page.getRecords());


    }
    @ApiOperation(value = "根据tno获取投递的简历")
    @GetMapping("/getdelieverResumeBytno")
    ///{tno}/{pageNo}/{pageSize}
    public PageResponse<DelieverResume> getdelieverResumeBytno(@RequestParam("tno") Integer tno,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        Page<DelieverResume> delieverResumePage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        LambdaQueryWrapper<DelieverResume> resumeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        resumeLambdaQueryWrapper.eq(DelieverResume::getTno,tno);
        IPage<DelieverResume> page = delieverResumeService.page(delieverResumePage, resumeLambdaQueryWrapper);
        return PageResponse.success(page, page.getRecords());
    }

    @ApiOperation(value = "投递简历")
    @PostMapping("/addDelieverResume")
    public Response addDelieverResume(@RequestBody @Validated DelieverResume delieverResume){
        boolean save = delieverResumeService.save(delieverResume);
        if(save){
            return Response.success();
        }
        else {
            return Response.fail();
        }

    }

    @ApiOperation(value = "根据sno获取投递的简历")
    @GetMapping("/getdelieverResumeBysno")
    ///{sno}/{pageNo}/{pageSize}
    public PageResponse<DelieverResume> getdelieverResumeBysno(@RequestParam("sno") String sno,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        Page<DelieverResume> delieverResumePage = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        LambdaQueryWrapper<DelieverResume> resumeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        resumeLambdaQueryWrapper.eq(DelieverResume::getSno,sno);
        IPage<DelieverResume> page = delieverResumeService.page(delieverResumePage, resumeLambdaQueryWrapper);
        return PageResponse.success(page, page.getRecords());

    }


    @ApiOperation(value = "根据id删除投递的简历")
    @GetMapping("/deleteDelieverResumeById")
    public Response deleteDelieverResumeById(@RequestParam("id") Integer id){
        boolean remove = delieverResumeService.removeById(id);
        if(remove){
            return Response.success();
        }
        else {
            return Response.fail();
        }
    }



}
