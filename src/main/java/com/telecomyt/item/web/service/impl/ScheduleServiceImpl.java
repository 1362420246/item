package com.telecomyt.item.web.service.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.telecomyt.item.dto.*;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.ScheduleGroup;
import com.telecomyt.item.entity.ScheduleInfoDo;
import com.telecomyt.item.entity.ScheduleLog;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.utils.DateUtil;
import com.telecomyt.item.utils.converter.ScheduleInfoVoConverter;
import com.telecomyt.item.web.mapper.ScheduleGroupMapper;
import com.telecomyt.item.web.mapper.ScheduleInfoMapper;
import com.telecomyt.item.web.mapper.ScheduleLogMapper;
import com.telecomyt.item.web.mapper.TaskMapper;
import com.telecomyt.item.web.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 日程业务层
 */
@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleGroupMapper scheduleGroupMapper ;

    @Autowired
    private ScheduleInfoMapper scheduleInfoMapper ;

    @Autowired
    private ScheduleLogMapper scheduleLogMapper ;

    @Autowired
    private TaskMapper taskMapper ;

    /**
     * 新增日程
     */
    @Override
    @Transactional(transactionManager = "transactionManager" ,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
                log.info("新增日程详情失败。事务回滚");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResp<>(ResultStatus.FAIL);
            }
            //TODO  日程的创建 不是日程的开始 ， 日程的开始和结束日志先不考虑
            return new BaseResp<>(ResultStatus.SUCCESS);
        }else {
            log.info("新增日程组失败。");
            return new BaseResp<>(ResultStatus.FAIL);
        }
    }

    /**
     * 查询日程列表
     * TODO 存在问题先使用不重复
     * TODO 1.周重复跨多周  月重复跨多月  即 周一到下下下周一   1号到下下下月1号
     * TODO 2.日重复 记录开始时间 + 执行时长 + 结束时间
     * TODO 3.月重复1-31号 2月咋办  是丢弃每个月没有的日期  还是加到最后一天
     * TODO 4.重复的时候 也要到开始时间才开始重复  ，不论日重复 周重复 月重复
     */
    @Override
    public BaseResp<Map> queryScheduleList(ScheduleListQuery scheduleListQuery) {
        Map<String, Collection> resultMap = Maps.newHashMap();
        //查询任务
        List<TaskVo> taskList = taskMapper.getTaskByCardIdAndDate(scheduleListQuery.getCardid(),scheduleListQuery.getStartTime(),scheduleListQuery.getEndTime());
        resultMap.put("taskList",taskList);
        //不重复的  必查 按时间查
        List<ScheduleInfoDto> noRepeatList = scheduleInfoMapper.queryScheduleListByNoRepeat(scheduleListQuery);
        resultMap.put("noRepeatList",noRepeatList);
        //日重复的  必查 直接查
        scheduleListQuery.setRepeatRules(1);
        List<ScheduleInfoDto> dayRepeatList = scheduleInfoMapper.queryScheduleListByRepeat(scheduleListQuery);
        resultMap.put("dayRepeatList",dayRepeatList);
        //周重复的  日查询时 要算开始时间是周几 、结束时间是周几
        scheduleListQuery.setRepeatRules(2);
        if(scheduleListQuery.getDateType() == 1){
            scheduleListQuery.setStartWeek(scheduleListQuery.getStartTime().getDayOfWeek().getValue());
            scheduleListQuery.setEndWeek(scheduleListQuery.getEndTime().getDayOfWeek().getValue());
        }
        List<ScheduleInfoDto> weekRepeatList = scheduleInfoMapper.queryScheduleListByRepeat(scheduleListQuery);
        resultMap.put("weekRepeatList",weekRepeatList);
        //月重复的  日查询时 要算开始时间是几号 ； 周查询时候 要算 开始时间 和结束时间 都是几号  取范围值
        scheduleListQuery.setRepeatRules(3);
        if(scheduleListQuery.getDateType() == 1 || scheduleListQuery.getDateType() == 2){
            scheduleListQuery.setStartDayMonth(scheduleListQuery.getStartTime().getDayOfMonth());
            scheduleListQuery.setEndDayMonth(scheduleListQuery.getEndTime().getDayOfMonth());
        }
        List<ScheduleInfoDto> monthRepeatList = scheduleInfoMapper.queryScheduleListByRepeat(scheduleListQuery);
        resultMap.put("monthRepeatList",monthRepeatList);
        //哪些日子有  ： 只有月查询时候
        Set<String> calendarSet = Sets.newTreeSet();
        if(scheduleListQuery.getDateType() == 3){
            //查询的范围
            List<String> daysByDifference = DateUtil.getDaysByDifference(scheduleListQuery.getStartTime(), scheduleListQuery.getEndTime());
            //如果有日重复  那返回全部日期
            if(dayRepeatList.size() > 0){
                calendarSet.addAll(daysByDifference);
            }else {
                //如果有周重复 就算出周任务对应的日期
                if(weekRepeatList.size() > 0){
                    Set<Integer> weekSet = Sets.newHashSet();
                    weekRepeatList.forEach(info -> {
                        List<Integer> weeksByDifference = DateUtil.getWeeksByDifference(info.getStartWeek(), info.getEndWeek());
                        weekSet.addAll(weeksByDifference);
                    });
                    // startTime 前端传入固定周日  endTime 前端传入固定周六
                    for (int i = 0; i < daysByDifference.size(); i++) {
                        int week = i % 7 ;
                        if(week == 0 ){
                            week = 7;
                        }
                        if(weekSet.contains(week)){
                            calendarSet.add(daysByDifference.get(i));
                        }
                    }
                }
                //如果有月重复
                if(monthRepeatList.size() > 0){
                    Set<Integer> daysOfMonthSets = Sets.newHashSet();
                    monthRepeatList.forEach(info -> {
                        List<Integer> daysOfMonth = DateUtil.getDaysOfMonthByDifference(info.getStartDayMonth(), info.getEndDayMonth(),
                                info.getStartTime().with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
                        daysOfMonthSets.addAll(daysOfMonth);
                    });
                    for (String day : daysByDifference) {
                        Integer dayByString = DateUtil.getDayByString(day);
                        if (daysOfMonthSets.contains(dayByString)) {
                            calendarSet.add(day);
                        }
                    }
                }
                //如果有不重复
                if(noRepeatList.size() > 0){
                    Set<String> dnoRepeatSets = Sets.newHashSet();
                    noRepeatList.forEach( info -> {
                        LocalDateTime startTime = info.getStartTime();
                        LocalDateTime endTime = info.getEndTime();
                        List<String> noRepeatDates = DateUtil.getDaysByDifference(startTime, endTime);
                        dnoRepeatSets.addAll(noRepeatDates);
                    });
                    for (String day : daysByDifference) {
                        if (dnoRepeatSets.contains(day)) {
                            calendarSet.add(day);
                        }
                    }
                }
                //任务
                if(taskList.size() > 0){
                    taskList.forEach(taskVo -> {
                        LocalDateTime taskEndtime = taskVo.getTaskEndtime();
                        String endStr = taskEndtime.toLocalDate().toString();
                        calendarSet.add(endStr);
                    });
                }
            }
        }
        resultMap.put("calendar",calendarSet);
        return new BaseResp<>(ResultStatus.SUCCESS,resultMap);
    }



    /**
     * 查询日程列表V2
     * TODO 要考虑重复
     * TODO 1.周重复跨多周  月重复跨多月  即 周一到下下下周一   1号到下下下月1号
     * TODO 2.日重复 记录开始时间 + 执行时长 + 结束时间
     * TODO 3.月重复1-31号 2月咋办  是丢弃每个月没有的日期  还是加到最后一天
     * TODO 4.重复的时候 也要到开始时间才开始重复  ，不论日重复 周重复 月重复
     *
     *
     * 关于重复： 记录 开始时间  + 时长
     *
     */





    /**
     * 查询日程详情 （包含日志）
     * @param groupId 组id
     */
    @Override
    public BaseResp<ScheduleInfoVo> queryScheduleInfo(Integer groupId) {
        ScheduleGroup scheduleGroup = scheduleGroupMapper.selectByPrimaryKey(groupId);
        if(scheduleGroup == null){
            return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"日程不存在");
        }
        ScheduleInfoVo scheduleInfoVo = ScheduleInfoVoConverter.INSTANCE.scheduleGrouToVo(scheduleGroup);
        List<String> affiliatedCardids = scheduleInfoMapper.queryAffiliatedCardids(groupId);
        scheduleInfoVo.setAffiliatedCardids(affiliatedCardids);
        List<ScheduleLog> scheduleLogs = scheduleLogMapper.queryByGroupId(groupId);
        scheduleInfoVo.setScheduleLogs(scheduleLogs);
        return new BaseResp<>(ResultStatus.SUCCESS,scheduleInfoVo);
    }

    /**
     * 添加日程日志
     */
    @Override
    @Transactional(transactionManager = "transactionManager" ,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResp<Object> addScheduleLog(ScheduleLog scheduleLog) {
        int result = scheduleLogMapper.insertSelective(scheduleLog);
        if(result > 0){
            return new BaseResp<>(ResultStatus.SUCCESS);
        }
        return new BaseResp<>(ResultStatus.FAIL);
    }

    /**
     *  修改日程
     */
    @Override
    @Transactional(transactionManager = "transactionManager" ,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResp<Object> updateSchedule(ScheduleUpdateParam scheduleUpdateParam) {
        ScheduleGroup scheduleGroup = new ScheduleGroup(scheduleUpdateParam);
        int result = scheduleGroupMapper.updateByPrimaryKeySelective(scheduleGroup);
        if(result > 0){
            return new BaseResp<>(ResultStatus.SUCCESS);
        }
        return new BaseResp<>(ResultStatus.FAIL);
    }

    /**
     * 删除日程
     * @param groupId 组id
     * @param cardid 身份证号
     */
    @Override
    @Transactional(transactionManager = "transactionManager" ,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResp<Object> deleteSchedule(Integer groupId, String cardid) {
        int result = scheduleInfoMapper.deleteByGroupIdAndCardid(groupId,cardid);
        if(result > 0){
            ScheduleLog scheduleLog = ScheduleLog.builder().groupId(groupId).operationCardid(cardid).logType(5).build();
            int addLogResult = scheduleLogMapper.insertSelective(scheduleLog);
            if(addLogResult == 0){
                log.info("新增日程日志失败。事务回滚");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResp<>(ResultStatus.FAIL);
            }
            return new BaseResp<>(ResultStatus.SUCCESS);
        }
        return new BaseResp<>(ResultStatus.FAIL);
    }
}
