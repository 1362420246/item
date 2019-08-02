package com.telecomyt.item.entity.log;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ProjectName: gx_electrocar_ga
 * @Package: com.telecomyt.electrocar.dto
 * @ClassName: ExceptionLog
 * @Description:
 * @Author: zhoupengbing
 * @CreateDate: 2019/4/9 12:00
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/4/9 12:00
 * @UpdateRemark:
 */
@Data
@Builder
@ToString
public class ExceptionLog implements Serializable {

    private static final long serialVersionUID = 2769311227617033438L;
    private String cause;
    private Object stackTrace;

}
