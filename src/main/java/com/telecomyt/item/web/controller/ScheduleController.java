package com.telecomyt.item.web.controller;

import com.telecomyt.item.dto.BaseResp;
import com.telecomyt.item.dto.ScheduleDto;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.utils.BeanValidator;
import com.telecomyt.item.web.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
