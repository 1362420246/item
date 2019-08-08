package com.telecomyt.item.dto;

import lombok.Data;
import java.io.Serializable;
/**
 * @Author ZhangSF
 * @Date 2019/8/7 11:36
 * @Version 1.0
 */

@Data
public class TaskIdState implements Serializable {
    private String cardId;
    private Integer taskState;
}
