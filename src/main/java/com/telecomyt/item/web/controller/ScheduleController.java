package com.telecomyt.item.web.controller;

import com.telecomyt.item.dto.BaseResp;
import com.telecomyt.item.dto.ScheduleDto;
import com.telecomyt.item.dto.ScheduleInfoVo;
import com.telecomyt.item.dto.ScheduleListQuery;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.utils.BeanValidator;
import com.telecomyt.item.web.service.ScheduleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/schedule")
@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService ;

    /**
     * 新增日程
     */
    @PostMapping("/add")
    public BaseResp<String> addSchedule(@RequestBody ScheduleDto scheduleDto){
        BeanValidator.check(scheduleDto);
        return scheduleService.addSchedule(scheduleDto);
    }

    /**
     * 查询日程列表
     */
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
    @GetMapping("/query/{groupId}")
    public BaseResp<ScheduleInfoVo> queryScheduleInfo(@PathVariable Integer groupId){
        return scheduleService.queryScheduleInfo(groupId);
    }
}
