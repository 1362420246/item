package com.telecomyt.item.web.service.impl;

import com.telecomyt.item.dto.BaseResp;
import com.telecomyt.item.dto.ScheduleDto;
import com.telecomyt.item.entity.ScheduleGroup;
import com.telecomyt.item.entity.ScheduleInfoDo;
import com.telecomyt.item.entity.ScheduleLog;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.web.mapper.ScheduleGroupMapper;
import com.telecomyt.item.web.mapper.ScheduleInfoMapper;
import com.telecomyt.item.web.mapper.ScheduleLogMapper;
import com.telecomyt.item.web.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleGroupMapper scheduleGroupMapper ;

    @Autowired
    private ScheduleInfoMapper scheduleInfoMapper ;

    @Autowired
    private ScheduleLogMapper scheduleLogMapper ;

    /**
     * 新增日程
     */
    @Override
    public BaseResp<String> addSchedule(ScheduleDto scheduleDto) {
        ScheduleGroup scheduleGroup = new ScheduleGroup(scheduleDto);
        int addGroupResult = scheduleGroupMapper.insertSelective(scheduleGroup);
        if(addGroupResult > 0){
            String creatorCardid = scheduleDto.getCreatorCardid();
            Integer groupId = scheduleGroup.getId();
            List<String> affiliatedCardids = scheduleDto.getAffiliatedCardids();
            if(affiliatedCardids == null){
                affiliatedCardids = new ArrayList<>();
            }
            affiliatedCardids.add(creatorCardid);
            ScheduleInfoDo scheduleInfoDo = ScheduleInfoDo.builder().groupId(groupId)
                    .creatorCardid(creatorCardid).affiliatedCardids(affiliatedCardids).build();
            int addInfoResult = scheduleInfoMapper.insertList(scheduleInfoDo);
            if(addInfoResult == 0){
                //TODO 回滚
                log.info("新增日程详情失败。");
                return new BaseResp<>(ResultStatus.FAIL);
            }
            //TODO 是否补日志中文描述
            ScheduleLog scheduleLog = ScheduleLog.builder().groupId(groupId).operationCardid(creatorCardid).logType(1).build();
            int addLogResult = scheduleLogMapper.insertSelective(scheduleLog);
            if(addLogResult == 0){
                //TODO 回滚
                log.info("新增日程日志失败。");
                return new BaseResp<>(ResultStatus.FAIL);
            }
            return new BaseResp<>(ResultStatus.SUCCESS);
        }else {
            log.info("新增日程组失败。");
            return new BaseResp<>(ResultStatus.FAIL);
        }
    }
}