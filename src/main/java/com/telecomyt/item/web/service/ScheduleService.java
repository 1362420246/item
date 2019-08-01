package com.telecomyt.item.web.service;

import com.telecomyt.item.dto.BaseResp;
import com.telecomyt.item.dto.ScheduleDto;

public interface ScheduleService {

    /**
     * 新增日程
     */
    BaseResp<String> addSchedule(ScheduleDto scheduleDto);
}
