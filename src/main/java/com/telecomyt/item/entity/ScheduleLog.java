package com.telecomyt.item.entity;

import com.telecomyt.item.dto.UserVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日程日志实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleLog implements Serializable {
    private static final long serialVersionUID = -6192533262984824634L;
    /**
     *   * 
     */
    private Integer id;

    /**
     *   * 日程组id
     */
    private Integer groupId;

    /**
     *   * 操作日身份证
     */
    private String operationCardid;

    /**
     *   * 1:开始  2:上传照片 3:上传附件 4:任务标注 5:删除 6:结束
     */
    private Integer logType;

    /**
     *   * 操作日志
     */
    private String logDetails;

    /**
     *   * 任务标注
     */
    private String logRemarks;

    /**
     *   * 文件存储路径（相对路径）
     */
    private String filePath;

    /**
     *   * 文件访问路径（相对路径）
     */
    private String fileUri;

    /**
     *   * 文件名称
     */
    private String fileName;

    /**
     *   * 
     */
    private LocalDateTime createTime;

    /**
     *   * 
     */
    private LocalDateTime updateTime;

    /**
     * 操作人的用户信息
     */
    private UserVo operationUser;
}