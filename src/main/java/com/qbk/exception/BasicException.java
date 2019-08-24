package com.qbk.exception;


import com.qbk.result.ResultStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasicException extends RuntimeException{

    private static final long serialVersionUID = 5524588728666419128L;

    private Integer code;
    private String msg;

    /**
     * 继承exception，加入错误状态值
     */
    public BasicException(ResultStatus resultStatus) {
        this.msg = resultStatus.getErrorMsg();
        this.code = resultStatus.getErrorCode();
    }

    /**
     * 自定义错误信息
     */
    public BasicException(Integer code, String message) {
        this.msg = message;
        this.code = code;
    }

    /**
     * 自定义错误信息
     */
    public BasicException(String message) {
        this.code = ResultStatus.FAIL.getErrorCode();
        this.msg = message;
    }

}
