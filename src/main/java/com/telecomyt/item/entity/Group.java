
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

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    private int groupId;
    private String creatorCardid;
    private String sheetTitle;
    private String sheetDescribe;
    private Date endTime;


}
