package com.qbk.entity.param;

import com.battcn.boot.swagger.model.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 测试参数请求
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "测试参数请求实体", description = "Test Request Entity")
public class TestRequest implements Serializable {

    private static final long serialVersionUID = 8617586773541843183L;

    /** 登录名 */
    @ApiModelProperty(value = "登录名",required = true ,dataType = DataType.STRING)
    @Length(max = 10,min = 6, message = "username长度6-10" )
    @NotBlank(message = "username不得为空")
    @Pattern(regexp = "^[\u4E00-\u9FA5A-Za-z0-9_]+$", message = "username只能是汉字、英文、数字或下划线")
    private String username;

    /** 登录密码 */
    @ApiModelProperty(value = "密码",required = true ,dataType = DataType.STRING)
    @Length(min = 8, max = 16, message = "password长度8-16")
    @NotBlank(message = "password不得为空")
    @Pattern(regexp = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须是8-16位的英文数字组合")
    private String password;

    /**
     * 电话
     * 15开头除 154
     * 18开头 180或者 185-9
     */
    @ApiModelProperty(value = "电话",required = true ,dataType = DataType.STRING)
    @NotBlank(message = "tel不得为空")
    @Pattern(regexp = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号格式不正确")
    private String tel;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱",required = true ,dataType = DataType.STRING)
    @NotBlank(message = "mail不得为空")
    @Email(message = "邮箱格式不正确")
    private String mail;
}
