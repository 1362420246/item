package com.telecomyt.item.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 日程详情实体
 */
@Data
public class ScheduleInfo implements Serializable {
    private static final long serialVersionUID = -4220731920111379303L;
    /**
     *   * 
     */
    private Integer id;

    /**
     *   * 日程组id
     */
    private Integer groupId;

    /**
     *   * 创建人身份证
     */
    private String creatorCardid;

    /**
     *   * 关联人身份证
     */
    private String affiliatedCardid;

    /**
     *   * 1 表示删除，0 表示未删除
     */
    private Boolean isDelete;

}