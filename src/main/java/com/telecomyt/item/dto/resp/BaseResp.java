package com.telecomyt.item.dto.resp;

import com.telecomyt.item.enums.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回信息实体类
 * @param <T>
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class BaseResp<T> implements Serializable{

    private static final long serialVersionUID = -7067272353892052297L;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息描述
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;
    private long currentTime;

    public BaseResp(){}

    /**
     *
     * @param code 错误码
     * @param message 信息
     * @param data 数据
     */
    public BaseResp(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.currentTime = System.currentTimeMillis();
    }

    public BaseResp(int code, String message) {
        this.code = code;
        this.message = message;
        this.currentTime = System.currentTimeMillis();
    }

    /**
     * 不带数据的返回结果
     * @param resultStatus
     */
    public BaseResp(ResultStatus resultStatus) {
        this.code = resultStatus.getErrorCode();
        this.message = resultStatus.getErrorMsg();
        this.currentTime = System.currentTimeMillis();
    }

    /**
     * 带数据的返回结果
     * @param resultStatus
     * @param data
     */
    public BaseResp(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getErrorCode();
        this.message = resultStatus.getErrorMsg();
        this.data = data;
        this.currentTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "BaseResp{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", currentTime=" + currentTime +
                '}';
    }
}

