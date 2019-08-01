package com.telecomyt.item.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.telecomyt.item.dto.ScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     *   * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     *   * 是否重复  0 false ：不重复  1 true：重复
     */
    private Boolean isRepeat;

    /**
     *   * 重复规则 0:不重复 1每天 2每周 3每月
     */
    private Integer repeatRules;

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

    /**
     *   * 
     */
    private LocalDateTime createTime;

    /**
     *   * 
     */
    private LocalDateTime updateTime;

    public ScheduleGroup(ScheduleDto scheduleDto) {
        this.creatorCardid = scheduleDto.getCreatorCardid();
        this.scheduleTitle = scheduleDto.getScheduleTitle();
        this.scheduleDescribe = scheduleDto.getScheduleDescribe();
        this.startTime = scheduleDto.getStartTime();
        this.endTime = scheduleDto.getEndTime();
        this.isRepeat = scheduleDto.getIsRepeat();
        this.repeatRules = scheduleDto.getRepeatRules();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        if(isRepeat){
            switch (repeatRules){
                case 2:
                    this.startWeek = startTime.getDayOfWeek().getValue() ;
                    this.endWeek = endTime.getDayOfWeek().getValue() ;
                    break;
                case 3:
                    this.startDayMonth = startTime.getDayOfMonth();
                    this.endDayMonth = endTime.getDayOfMonth();
                    break;
                default:
                    break;
            }
        }


    }
}