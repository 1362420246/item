package com.telecomyt.item.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ScheduleGroup implements Serializable {
    private static final long serialVersionUID = -8795693489279315839L;
    /**
     *   * 
     */
    private Integer id;

    /**
     *   * 创建人身份证
     */
    private String creatorCardid;

    /**
     *   * 日程标题
     */
    private String scheduleTitle;

    /**
     *   * 日程描述
     */
    private String scheduleDescribe;

    /**
     *   * 开始时间
     */
    private Date startTime;

    /**
     *   * 结束时间
     */
    private Date endTime;

    /**
     *   * 是否重复  0：不重复  1：重复
     */
    private Boolean isRepeat;

    /**
     *   * 重复规则 0:不重复 1每天 2每周 3每月
     */
    private Boolean repeatRules;

    /**
     *   * 开始时间的星期
     */
    private Boolean startWeek;

    /**
     *   * 结束时间的星期
     */
    private Boolean endWeek;

    /**
     *   * 开始时间的日
     */
    private Byte startDayMonth;

    /**
     *   * 结束时间的日
     */
    private Byte endDayMonth;

    /**
     *   * 
     */
    private Date createTime;

    /**
     *   * 
     */
    private Date updateTime;

}