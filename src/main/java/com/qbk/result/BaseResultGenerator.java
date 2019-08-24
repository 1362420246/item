package com.qbk.result;


/**
 * BaseResult生成器
 */
public class BaseResultGenerator {

    public static <T> BaseResult<T> generate(int code, String msg, T data) {
        return new BaseResult<>(code, msg, data);
    }
    public static <T> BaseResult<T> generate(int code, String msg) {
        return new BaseResult<>(code, msg, null);
    }

    public static <T> BaseResult<T> generate(ResultStatus status , T data) {
        return new BaseResult<>(status.getErrorCode(), status.getErrorMsg(), data);
    }

    public static <T> BaseResult<T> generate(ResultStatus status) {
        return generate(status,null);
    }

    /**
     * 成功
     */
    public static <T> BaseResult<T> success(String msg) {
        return generate(ResultStatus.SUCCESS.getErrorCode(), msg, null);
    }
    public static <T> BaseResult<T> success(String msg, T data) {
        return generate(ResultStatus.SUCCESS.getErrorCode(), msg, data);
    }
    public static <T> BaseResult<T> success() {
        return generate(ResultStatus.SUCCESS, null);
    }
    public static <T> BaseResult<T> success(T data) {
        return generate(ResultStatus.SUCCESS, data);
    }

    /**
     * 错误
     */
    public static <T> BaseResult<T> error(String msg) {
        return generate(ResultStatus.FAIL.getErrorCode(), msg, null);
    }
    public static <T> BaseResult<T> error(String msg, T data) {
        return generate(ResultStatus.FAIL.getErrorCode(), msg, data);
    }
    public static <T> BaseResult<T> error() {
        return generate(ResultStatus.FAIL, null);
    }
    public static <T> BaseResult<T> error(T data) {
        return generate(ResultStatus.FAIL, data);
    }


}
