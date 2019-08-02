
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
public class Group implements Serializable {
    private static final long serialVersionUID = 474985485361705501L;
    private int groupId;
    private String creatorCardid;
    private String sheetTitle;
    private String sheetDescribe;
    private Date endTime;
}
