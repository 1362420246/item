package com.telecomyt.item.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日程详情传输对象
 */
@Data
public class ScheduleInfoDto implements Serializable {

    private static final long serialVersionUID = -5203140905131856487L;
    /**
     * 日程id
     */
    private Integer id ;
    /**
     * 日程组id
     */
    private Integer groupId ;
    /**
     * 创建者身份证号
     */
    private String creatorCardid ;
    /**
     * 身份证号
     */
    private String cardid ;
    /**
     * 标题
     */
    private String title ;
    /**
     * 日程描述
     */
    private String scheduleDescribe ;
    /**
     * 开始时间
     */
    private LocalDateTime startTime ;
    /**
     * 结束时间
     */
    private LocalDateTime endTime ;

    /**
     * 是否重复
     */
    private Boolean isRepeat ;
    /**
     * 重复规则 0:不重复 1每天 2每周 3每月
     */
    private Integer repeatRules ;

    /**
     *   * 开始时间的星期
     */
    private Integer startWeek;

    /**
     *   * 结束时间的星期
     */
    private Integer endWeek;

    /**
     *   * 开始时间的日
     */
    private Integer startDayMonth;

    /**
     *   * 结束时间的日
     */
    private Integer endDayMonth;


}
