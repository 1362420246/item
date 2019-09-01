package com.qbk.entity.param;

import com.battcn.boot.swagger.model.DataType;
import com.qbk.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 参数对象
 */
@Data
public class UserAddParam implements Serializable{

    private static final long serialVersionUID = -5165545511105897591L;
    /**
     *   * 登录名
     */
    @ApiModelProperty(value = "登录名",required = true ,dataType = DataType.STRING)
    @Length(max = 10,min = 6, message = "username长度6-10" )
    @NotBlank(message = "loginName不得为空")
    @Pattern(regexp = "^[\u4E00-\u9FA5A-Za-z0-9_]+$", message = "username只能是汉字、英文、数字或下划线")
    private String loginName;

    /**
     *   * 昵称
     */
    @NotBlank(message = "nickName不得为空")
    private String nickName;

    /**
     *   * 密码
     */
    @ApiModelProperty(value = "密码",required = true ,dataType = DataType.STRING)
    @Length(min = 8, max = 16, message = "password长度8-16")
    @NotBlank(message = "password不得为空")
    @Pattern(regexp = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须是8-16位的英文数字组合")
    private String password;

}
