package com.atxbai.online.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.atxbai.online.common.responseUtils.PageResponse;
import com.atxbai.online.common.responseUtils.Response;
import com.atxbai.online.common.responseUtils.ResponseCodeEnum;
import com.atxbai.online.common.securityUtils.JwtTokenHelper;
import com.atxbai.online.config.security.PasswordEncoderConfig;
import com.atxbai.online.exception.BizException;
import com.atxbai.online.mapper.*;
import com.atxbai.online.model.pojo.*;
import com.atxbai.online.model.vo.EditPasswordVo;
import com.atxbai.online.model.vo.teacher.*;
import com.atxbai.online.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.atxbai.online.common.constants.Constant.BATCH_COUNT;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-11 16:15
 * @content:
 */
@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private DelieverResumeMapper delieverResumeMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ResumeMapper resumeMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @Transactional(rollbackFor = Exception.class)
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
        // 如果为 1 ，给消息表中添加数据，表示学生通过项目的进度
        Message message = Message.builder()
                .tno(Integer.parseInt(tno))
                .sno(sno).pid(pid)
                .createDateTime(LocalDateTime.now())
                .content("学生" + delieverResume.getSName() + "被拒绝加入" + delieverResume.getProjectName() + "项目组!")
                .build();
        // 插入数据
        int insert = messageMapper.insert(message);
        // 返回
        return update == 1 ? Response.success() : Response.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

    @Override
    public Map<String, Object> listTeacher(int page, int limit, String keyword) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if (keyword != null && !"".equals(keyword) && keyword.length() > 0) {
            wrapper.like("name", keyword).or().eq("no", keyword);
        }
        Long count = teacherMapper.selectCount(wrapper);
        // 创建分页对象
        Page<Teacher> p = new Page<>(page, limit, count);
        // 执行分页查询
        IPage<Teacher> teacherIPage = teacherMapper.selectPage(p, wrapper);
        // 获取查询结果
        List<Teacher> teachers = teacherIPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("teachers", teachers);
        map.put("total", teacherIPage.getTotal());
        map.put("size", teacherIPage.getSize());
        map.put("page", teacherIPage.getPages());
        return map;
    }

    @Override
    public Teacher getTeacherByNo(Integer no) {
        return teacherMapper.selectById(no);
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }

    @Override
    public boolean deleteTeacher(Integer id) {
        return teacherMapper.deleteById(id) >=1;
    }

    @Override
    public boolean resetPassword(Integer id) {
        String password =passwordEncoder.encode("888888");
        return  teacherMapper.resetPassword(password,id);
    }

    @Override
    public boolean addTeacher(Teacher teacher) {
        return teacherMapper.insert(teacher)>=1;
    }

    @Override
    public Response lookStudentResume(LookStudentResumeReqVO lookStudentResumeReqVO) {
        String sno = lookStudentResumeReqVO.getSno();
        Student student = studentMapper.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getSno, sno));
        Resume resume = resumeMapper.selectOne(Wrappers.<Resume>lambdaQuery().eq(Resume::getSno, sno));
        LookStudentResumeRspVO build = LookStudentResumeRspVO.builder().studentInfo(student).resumeInfo(resume).build();
        return Response.success(build);
    }

    @Override
    public Response selectByInfo(String header) {
        // 解析 token
        String token = StringUtils.substring(header, 7);
        String tno = jwtTokenHelper.getUsernameByToken(token);
        // 返回教师信息
        Teacher teacher = teacherMapper.selectById(tno);
        return Response.success(teacher);
    }

    @Override
    public Response editMessage(EditMessageRspVO editMessageRspVO) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(editMessageRspVO,teacher);
        teacherMapper.updateById(teacher);
        return Response.success();
    }

    @Override
    public Response updatePassword(String header, EditPasswordVo editPasswordVo) {
        // 解析 token
        String token = StringUtils.substring(header, 7);
        String tno = jwtTokenHelper.getUsernameByToken(token);
        // 获取传递的密码
        String oldPassword = editPasswordVo.getOldPassword();
        // 查询用户的密码
        Teacher teacher = teacherMapper.selectById(tno);
        if (passwordEncoder.matches(oldPassword,teacher.getPassword())){
            // 修改密码
            String newPassword = editPasswordVo.getNewPassword();
            newPassword = passwordEncoder.encode(newPassword);
            teacher.setPassword(newPassword);
            teacherMapper.updateById(teacher);
            return Response.success("修改成功!");
        }
        return Response.fail("原密码不正确!");
    }

    @Override
    public void upload(InputStream inputStream) {
        //匿名内部类方法 不用额外写一个DemoDataListener
        EasyExcelFactory.read(inputStream, ExcelUploadVo.class, new ReadListener<ExcelUploadVo>() {
            //临时存储
            //每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
            private List<ExcelUploadVo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
                if (!headMap.containsKey(0) || !headMap.containsKey(1) || !headMap.containsKey(2) || !headMap.containsKey(3)
                        || !headMap.get(0).getStringValue().equals("姓名") || !headMap.get(1).getStringValue().equals("专业")
                        || !headMap.get(2).getStringValue().equals("电话") || !headMap.get(3).getStringValue().equals("所属部门") ) {
                    // 这里给data加1条空数据，是因为doAfterAllAnalysed方法最后有判是否是空列表
                    cachedDataList.add(new ExcelUploadVo());
                    throw new BizException(ResponseCodeEnum.EXCEL_HEAD_INCORRECT);
                }
                // 这里给data加1条空数据，是因为doAfterAllAnalysed方法最后有判是否是空列表

            }

            //用于处理Excel中一行解析形成的POJO对象，解析过程由EasyExcel根据POJO字段上的注解自动完成。
            @Override
            public void invoke(ExcelUploadVo excelUploadVo, AnalysisContext context) {
                cachedDataList.add(excelUploadVo);
                // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            /**
             * 所有数据解析完成了 都会来调用
             *
             * @param context
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 这里也要保存数据，确保最后遗留的数据也存储到数据库
                saveData();
                log.info("所有数据解析完成！");
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                for (ExcelUploadVo excelUploadVo : cachedDataList) {
                    Teacher teacher = new Teacher();
                    BeanUtils.copyProperties(excelUploadVo,teacher);
                    teacher.setPassword("$2a$10$pSYaCr4ItzTJasjwI2N6Hu0bPh5VOlZCqScR6tzmDVMF6oe3ftN5u");
                    teacherMapper.insert(teacher);
                }
                log.info("存储数据库成功！");
            }

        }).sheet().doRead();

    }

    @Override
    public void export() {
        List<Teacher> list = teacherMapper.selectList(null);
        //获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyyMMdd HHmmss");
        Date date = new Date();// 获取当前时间
        //编写输出路径，固定输出到桌面
        String fileName = com.getPath()+"/老师数据导出列表" + sdf.format(date)+".xlsx";
        //输出
        EasyExcel.write(fileName, ExcelDownloadVo.class)
                .autoCloseStream(Boolean.FALSE)
                .sheet("导出列表")
                .doWrite(list);
    }
}
