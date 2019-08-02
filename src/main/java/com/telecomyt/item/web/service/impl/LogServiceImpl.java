package com.telecomyt.item.web.service.impl;

import com.telecomyt.item.entity.log.SysLog;
import com.telecomyt.item.web.mapper.LogMapper;
import com.telecomyt.item.web.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: gx_electrocar_ga
 * @Package: com.telecomyt.electrocar.web.service.impl
 * @ClassName: LogServiceImpl
 * @Description: 日志服务
 * @Author: zhoupengbing
 * @CreateDate: 2019/4/9 10:27
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/4/9 10:27
 * @UpdateRemark:
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 添加日志信息
     *
     * @param sysLog
     * @return
     */
    @Override
    public int add(SysLog sysLog) throws Exception{
        return logMapper.add(sysLog);
    }
}
