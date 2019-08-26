package com.qbk.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 菜单实体
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = -7136172804056389982L;
    /**
     *   * 
     */
    private Integer id;

    /**
     *   * 菜单名称
     */
    private String name;

    /**
     *   * 父菜单
     */
    private Integer parentId;

    /**
     *   * 菜单层级
     */
    private Integer level;

    /**
     *   * 路径
     */
    private String paths;

    /**
     *   * 排序
     */
    private Integer sort;

    /**
     *   * 
     */
    private LocalDateTime createDate;

    /**
     *   * 
     */
    private LocalDateTime updateDate;

    /**
     *   * 是否删除
     */
    private Boolean isDel;

}