package com.telecomyt.item.web.mapper;

import com.telecomyt.item.entity.log.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ProjectName: gx_electrocar_ga
 * @Package: com.telecomyt.electrocar.web.mapper
 * @ClassName: LogMapper
 * @Description: 日志服务持久层接口
 * @Author: zhoupengbing
 * @CreateDate: 2019/4/9 10:27
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/4/9 10:27
 * @UpdateRemark:
 */
@Mapper
public interface LogMapper {

    int add(SysLog sysLog);

}
