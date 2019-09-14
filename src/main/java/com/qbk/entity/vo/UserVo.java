package com.qbk.entity.vo;

import com.battcn.boot.swagger.model.DataType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * VO（View Object）：显示层对象，通常是 Web 向模板渲染引擎层传输的对象。
 */
@Data
@ApiModel(value = "用户", description = "用户显示层对象")
public class UserVo implements Serializable {
    private static final long serialVersionUID = 4502386759914970087L;
    /**
     *   * 用户ID
     */
    @ApiModelProperty(value = "用户ID",required = true ,dataType = DataType.STRING)
    private Integer id;

    /**
     *   * 登录名
     */
    @ApiModelProperty(value = "用户名",required = true ,dataType = DataType.STRING)
    private String username;

    /**
     *   * 昵称
     */
    @ApiModelProperty(value = "用户昵称",required = true ,dataType = DataType.STRING)
    private String nickName;

    /**
     *   * 是否锁定
     */
    @ApiModelProperty(value = "用户锁定",required = true ,dataType = DataType.BOOLEAN)
    private Boolean isLock;

    /**
     *   *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间",required = true ,dataType = DataType.DATETIME)
    private LocalDateTime createDate;

    /**
     *   *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间",required = true ,dataType = DataType.DATETIME)
    private LocalDateTime updateDate;

}
