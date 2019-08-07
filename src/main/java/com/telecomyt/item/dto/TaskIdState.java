package com.telecomyt.item.dto;

import com.telecomyt.item.entity.Task;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author ZhangSF
 * @Date 2019/8/7 11:36
 * @Version 1.0
 */

@Data
public class TaskIdState implements Serializable {

    private String taskCardId;
    private String taskCopierId;
}
