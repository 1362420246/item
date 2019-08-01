package com.telecomyt.item.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

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
