package com.qbk.result;

import com.battcn.boot.swagger.model.DataType;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "通用API接口返回", description = "Common Api Response")
public class BaseResult<T> implements Serializable {
    @ApiModelProperty(value = "状态码",required = true ,dataType = DataType.INT)
    private int code;
    @ApiModelProperty(value = "信息" ,required = true ,dataType = DataType.STRING)
    private String message;
    @ApiModelProperty(value = "数据对象" ,required = false ,dataType = "data")
    private T data;
}
