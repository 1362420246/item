package com.telecomyt.item.dto;

import com.telecomyt.item.entity.ScheduleLog;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程详情显示层对象
 */
@Data
public class ScheduleInfoVo implements Serializable {

    private static final long serialVersionUID = -2078135105826726377L;
    /**
     * 日程组id
     */
    private Integer groupId ;
    /**
     * 标题
     */
    private String scheduleTitle ;
    /**
     * 日程描述
     */
    private String scheduleDescribe ;
    /**
     *   * 创建人身份证
     */
    private String creatorCardid;

    /**
     *   * 关联人身份证
     */
    private List<String> affiliatedCardids;

    /**
     *   * 开始时间
     */
    private LocalDateTime startTime;

    /**
     *   * 结束时间
     */
    private LocalDateTime endTime;

    /**
     *   * 是否重复  0：不重复  1：重复
     */
    private Boolean isRepeat;

    /**
     *   * 重复规则 0:不重复 1每天 2每周 3每月
     */
    private Integer repeatRules;

    /**
     * 操作日志
     */
    private List<ScheduleLog> scheduleLogs;

    private LocalDateTime createTime;

    /**
     * 创建人用户信息
     */
    private UserVo creatorUser ;

    /**
     * 关联人的用户信息数据
     */
    private List<UserVo> affiliatedUsers ;



}
