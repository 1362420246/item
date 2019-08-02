package com.telecomyt.item.entity.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ProjectName: gx-electrocar-ga
 * @Package: com.telecomyt.electrocar.dto
 * @ClassName: SysLog
 * @Description:
 * @Author: zhoupengbing
 * @CreateDate: 2019/4/2 14:30
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/4/2 14:30
 * @UpdateRemark:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLog implements Serializable {

    private static final long serialVersionUID = -5219408738169689511L;

    private Long id;

    private String type;

    private String title;

    private String createUser;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String remoteAddr;

    private String userAgent;

    private String requestUri;

    private String params;

    private Long time;

    private String returnMsg;

    private String exception;


}

