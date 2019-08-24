package com.qbk.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> implements Serializable {
    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "信息")
    private String message;
    @ApiModelProperty(value = "数据对象")
    private T data;

}
