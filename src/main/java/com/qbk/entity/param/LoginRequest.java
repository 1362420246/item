package com.qbk.entity.param;

import com.battcn.boot.swagger.model.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登陆请求
 */
@Data
@ApiModel(value = "登陆请求", description = "登陆请求参数")
public class LoginRequest {

    @ApiModelProperty(value = "用户名",required = true ,dataType = DataType.STRING)
    @NotBlank(message = "username不得为空")
    private String username ;

    @ApiModelProperty(value = "密码",required = true ,dataType = DataType.STRING)
    @NotBlank(message = "password不得为空")
    private String password ;
}
