package com.telecomyt.item.web.service;

import com.telecomyt.item.dto.BaseResp;
import com.telecomyt.item.dto.ScheduleDto;
import com.telecomyt.item.dto.ScheduleListQuery;

public interface ScheduleService {

    /**
     * 新增日程
     */
    BaseResp<String> addSchedule(ScheduleDto scheduleDto);

    /**
     * 查询日程列表
     */
    BaseResp<String> queryScheduleList(ScheduleListQuery scheduleListQuery);
}
