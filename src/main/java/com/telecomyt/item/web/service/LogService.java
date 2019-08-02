package com.telecomyt.item.web.service;


import com.telecomyt.item.entity.log.SysLog;

/**
 * @ProjectName: gx_electrocar_ga
 * @Package: com.telecomyt.electrocar.web.service
 * @ClassName: LogService
 * @Description: 日志服务接口
 * @Author: zhoupengbing
 * @CreateDate: 2019/4/9 10:26
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/4/9 10:26
 * @UpdateRemark:
 */
public interface LogService {

    /**
     * 添加日志信息
     * @param sysLog
     * @return
     */
    int add(SysLog sysLog)throws Exception;

}
