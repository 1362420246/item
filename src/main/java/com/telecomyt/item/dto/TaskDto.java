package com.telecomyt.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author ZhangSF
 * @Date 2019/8/3 14:11
 * @Version 1.0
 */
@Data
public class TaskDto implements Serializable {
    private static final long serialVersionUID = -1920361582855456009L;

   @NotNull(message = "创建人id不能为空")
   private String creatorCardId;

   @NotNull(message = "任务标题不能为空")
   private String sheetTitle;

   @NotNull(message = "任务描述不能为空")
   private String sheetDescribe;

   @NotNull (message = "任务结束时间不能为空")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime taskEndTime;

    /**
     *   * 关联人身份证
     */
    @NotNull(message = "任务执行人不能为空")
    private List<String> taskCardIds;
    @NotNull(message = "任务执行人不能为空")
    private List<String> taskCopierIds;
    @NotNull(message = "任务类型不能为空")
    private Integer taskType;
    @NotNull(message = "任务状态不能为空")
    private Integer taskState;
    @NotNull(message = "任务主从关系不能为空")
    private Integer taskMain;


}
