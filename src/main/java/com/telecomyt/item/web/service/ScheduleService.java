package com.telecomyt.item.web.service;

import com.telecomyt.item.dto.*;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.ScheduleLog;

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

    /**
     * 查询日程详情 （包含日志）
     * @param groupId 组id
     */
    BaseResp<ScheduleInfoVo> queryScheduleInfo(Integer groupId);

    /**
     * 添加日程日志
     */
    BaseResp<Object> addScheduleLog(ScheduleLog scheduleLog);

    /**
     * 修改日程
     */
    BaseResp<Object> updateSchedule(ScheduleUpdateParam scheduleUpdateParam);

    /**
     * 删除日程
     * @param groupId 组id
     * @param cardid 身份证号
     */
    BaseResp<Object> deleteSchedule(Integer groupId, String cardid);
}
