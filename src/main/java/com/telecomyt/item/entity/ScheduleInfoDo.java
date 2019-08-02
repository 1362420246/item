package com.telecomyt.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 日程详情数据层对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleInfoDo implements Serializable {

    private static final long serialVersionUID = 2141895934462636067L;

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
    private List<String> affiliatedCardids;

    /**
     *   * 1 表示删除，0 表示未删除
     */
    @Builder.Default
    private Boolean isDelete = false;

}