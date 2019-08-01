package com.telecomyt.item.web.service;

import com.telecomyt.item.dto.BaseResp;
import com.telecomyt.item.dto.ScheduleDto;
import com.telecomyt.item.dto.ScheduleListQuery;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

    /**
     * 新增日程
     */
    BaseResp<String> addSchedule(ScheduleDto scheduleDto);

    /**
     * 查询日程列表
     */
    BaseResp<Map> queryScheduleList(ScheduleListQuery scheduleListQuery);
}
