/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */

package com.telecomyt.item.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskLog implements Serializable {
    private static final long serialVersionUID = -2244507047106889578L;
    private Integer logId;
    private Integer groupId;
    private Date logTime;
    private String logCardId;
    private  Integer logType;
    private String filePath;
    private  String fileUrl;
    private  String fileTagging;

}
