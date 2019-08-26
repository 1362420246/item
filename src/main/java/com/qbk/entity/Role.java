package com.qbk.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 角色实体
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 4265816818093608320L;
    /**
     *   * 
     */
    private Integer id;

    /**
     *   * 角色名称
     */
    private String name;

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

    /**
     * 菜单
     */
    private List<Menu>  menuList ;

}