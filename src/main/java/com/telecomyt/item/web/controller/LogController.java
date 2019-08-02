package com.telecomyt.item.web.controller;

import cn.hutool.json.JSONUtil;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.log.SysLog;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.web.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: gx_electrocar_ga
 * @Package: com.telecomyt.electrocar.web.controller
 * @ClassName: LogController
 * @Description: 日志管理
 * @Author: zhoupengbing
 * @CreateDate: 2019/4/9 10:20
 * @UpdateUser: zhoupengbing
 * @UpdateDate: 2019/4/9 10:20
 * @UpdateRemark:
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/add")
    public BaseResp add(@RequestBody SysLog sysLog)throws Exception{
        log.info("添加日志信息,请求参数是：{}", JSONUtil.toJsonStr(sysLog));
        logService.add(sysLog);
        return new BaseResp(ResultStatus.SUCCESS);
    }

}
