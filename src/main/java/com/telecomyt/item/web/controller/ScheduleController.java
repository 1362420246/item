package com.telecomyt.item.web.controller;

import com.telecomyt.item.annotation.ServiceLog;
import com.telecomyt.item.constant.CommonConstants;
import com.telecomyt.item.dto.*;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.ScheduleLog;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.utils.BeanValidator;
import com.telecomyt.item.utils.FileUtil;
import com.telecomyt.item.utils.ImageUtils;
import com.telecomyt.item.web.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * 日程控制层
 */
@Slf4j
@RequestMapping("/schedule")
@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService ;

    /**
     * 新增日程
     */
    @ServiceLog("添加日程")
    @PostMapping("/add")
    public BaseResp<String> addSchedule(@RequestBody ScheduleDto scheduleDto){
        BeanValidator.check(scheduleDto);
        return scheduleService.addSchedule(scheduleDto);
    }

    /**
     * 查询日程列表
     */
    @ServiceLog("查询日程列表")
    @GetMapping("/query")
    public BaseResp<Map> queryScheduleList(ScheduleListQuery scheduleListQuery){
        BeanValidator.check(scheduleListQuery);
        if( scheduleListQuery.getStartTime().isAfter(scheduleListQuery.getEndTime()) ){
            return new BaseResp<>( ResultStatus.INVALID_PARAM );
        }
        return scheduleService.queryScheduleList(scheduleListQuery);
    }

    /**
     * 查询日程详情 （包含日志）
     * @param groupId 组id
     */
    @ServiceLog("查询日程详情")
    @GetMapping("/query/{groupId}")
    public BaseResp<ScheduleInfoVo> queryScheduleInfo(@PathVariable Integer groupId){
        return scheduleService.queryScheduleInfo(groupId);
    }

    /**
     * 上报
     * @param logType 1:开始  2:上传照片 3:上传附件 4:任务标注 5:删除 6:结束
     * @param logRemarks 标注内容
     * @param groupId 组id
     * @param operationCardid 操作日身份证
     */
    @ServiceLog("上报日程日志")
    @PostMapping("/reporting")
    public BaseResp<Object>  reporting(
            @RequestParam(value = "logType") Integer logType ,
            @RequestParam(value = "file" ,required = false) MultipartFile file ,
            @RequestParam("groupId") Integer groupId ,
            @RequestParam("operationCardid") String operationCardid ,
            String logRemarks ) throws Exception {
        if(logType == null || groupId == null || operationCardid == null ){
            return new BaseResp<>(ResultStatus.INVALID_PARAM);
        }
        ScheduleLog scheduleLog = ScheduleLog.builder().groupId(groupId).operationCardid(operationCardid).logType(logType).build();
        if(logType == 2 || logType == 3){
            //如果文件不为空，写入上传路径
            if(file.isEmpty()) {
                return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"上报失败，请选择文件");
            }
            //上传文件路径
            String path = FileUtil.getHomePath() + CommonConstants.REPORTING_PATH ;
            //上传文件名
            String filename = file.getOriginalFilename();
            if(StringUtils.isEmpty(filename)){
                filename = UUID.randomUUID().toString();
            }
            File filepath = new File(path,filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                boolean mkResult = filepath.getParentFile().mkdirs();
                if(!mkResult){
                    log.info("路径[{}]创建失败",filepath.getParentFile().toString());
                    return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"上报失败，上传路径不存在");
                }
            }
            //将上传文件保存到一个目标文件当中
            File saveFile = new File(path + filename);
            file.transferTo(saveFile);
            //存储的路径（相对路径）
            scheduleLog.setFilePath(CommonConstants.REPORTING_PATH + filename);
            //访问路径（uri）
            scheduleLog.setFileUri(CommonConstants.REPORTING_PATH + filename);
            scheduleLog.setFileName(filename);
            log.info("上报文件保存路径："+saveFile.getAbsolutePath());
            log.info("上报文件访问uri："+ CommonConstants.REPORTING_PATH + filename);
            if(logType ==2){
                String zoomPath = FileUtil.getHomePath() + CommonConstants.REPORTING_PICTURE_ZOOM_PATH ;
                File zoomFile = new File(zoomPath);
                //判断路径是否存在，如果不存在就创建一个
                if (!zoomFile.exists()) {
                    zoomFile.mkdirs();
                }
                ImageUtils.zoomFixedSize(saveFile.getAbsolutePath() ,zoomPath , filename);
                scheduleLog.setFileZoomPath(CommonConstants.REPORTING_PICTURE_ZOOM_PATH  + filename);
                scheduleLog.setFileZoomUrl(CommonConstants.REPORTING_PICTURE_ZOOM_PATH  + filename );
            }

        }else if(logType == 4){
            scheduleLog.setLogRemarks(logRemarks);
        }else{
            return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"上报失败，上报类型错误");
        }
        return scheduleService.addScheduleLog(scheduleLog);
    }

    /**
     * 修改日程
     */
    @ServiceLog("修改日程")
    @PutMapping("/update")
    public BaseResp<Object> updateSchedule(@RequestBody ScheduleUpdateParam scheduleUpdateParam){
        BeanValidator.check(scheduleUpdateParam);
        return scheduleService.updateSchedule(scheduleUpdateParam);
    }

    /**
     * 删除日程
     * @param groupId 组id
     * @param cardid 身份证号
     */
    @ServiceLog("删除日程")
   @DeleteMapping("/delete")
    public BaseResp<Object> deleteSchedule(
            @RequestParam("groupId")Integer groupId ,
            @RequestParam("cardid")String cardid ){
        return scheduleService.deleteSchedule(groupId,cardid);
    }
}
