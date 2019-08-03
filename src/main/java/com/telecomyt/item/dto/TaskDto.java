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
   private String creator_CardId;
   @NotNull(message = "任务标题不能为空")
   private String sheet_Title;
   @NotNull(message = "任务描述不能为空")
   private String sheet_Describe;
   @NotNull (message = "任务结束时间不能为空")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime End_time;

    /**
     *   * 关联人身份证
     */
    private List<String> taskCardId;

}
