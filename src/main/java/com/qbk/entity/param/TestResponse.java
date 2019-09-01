package com.qbk.entity.param;

import com.battcn.boot.swagger.model.DataType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 测试参数响应
 */
@Data
@ApiModel(value = "测试参数响应实体", description = "Test Response Entity")
public class TestResponse implements Serializable {

    private static final long serialVersionUID = -4836204119504454820L;

    /** 登录名 */
    @ApiModelProperty(value = "登录名",required = true ,dataType = DataType.STRING)
    private String username;

    /** 登录密码 */
    @JsonIgnore
    private String password;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话",required = true ,dataType = DataType.STRING)
    private String phone;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱",required = true ,dataType = DataType.STRING)
    private String mail;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "当前时间1",required = false ,dataType = DataType.DATETIME)
    private LocalDateTime time = LocalDateTime.now();

    @ApiModelProperty(value = "当前时间2",required = true ,dataType = DataType.DATETIME)
    private Date date = new Date();

}
