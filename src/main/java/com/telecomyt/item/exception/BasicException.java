package com.telecomyt.item.exception;


import com.telecomyt.item.enums.ResultStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ProjectName: gx-electrocar-ga
 * @Package: com.telecomyt.electrocar.exception
 * @ClassName: BasicException
 * @Description: 异常类
 * @Author: zhoupengbing
 * @CreateDate: 2019/4/2 15:37
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/4/2 15:37
 * @UpdateRemark:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasicException extends RuntimeException{

    private static final long serialVersionUID = 5524588728666419128L;

    private Integer code;
    private String msg;

    /**
     * 继承exception，加入错误状态值
     * @param resultStatus
     */
    public BasicException(ResultStatus resultStatus) {
        this.msg = resultStatus.getErrorMsg();
        this.code = resultStatus.getErrorCode();
    }

    /**
     * 自定义错误信息
     * @param message
     * @param code
     */
    public BasicException(Integer code, String message) {
        this.msg = message;
        this.code = code;
    }

    /**
     * 自定义错误信息
     * @param message
     */
    public BasicException(String message) {
        this.code = ResultStatus.FAIL.getErrorCode();
        this.msg = message;
    }

}
