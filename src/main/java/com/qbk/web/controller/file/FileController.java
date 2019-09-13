package com.qbk.web.controller.file;

import com.battcn.boot.swagger.model.DataType;
import com.battcn.boot.swagger.model.ParamType;
import com.qbk.exception.BasicException;
import com.qbk.log.annotation.ServiceLog;
import com.qbk.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
//用于类
@Api(tags = "1.0", value = "文件控制器",description="文件控制器")
public class FileController {

    /**
     * 下载
     */
    @ServiceLog("下载")
    @GetMapping("/download")
    //用于方法
    @ApiOperation(value = "下载", notes = "成功无返回，失败有返回")
    /* 参数描述
        name–参数名
        value–参数说明
        dataType–数据类型
        paramType–参数类型
        example–举例说明
     */
    @ApiImplicitParams({@ApiImplicitParam(name = "url", value = "下载路径",
            dataType = DataType.STRING,required = true, paramType = ParamType.QUERY, defaultValue = "你自己找图片去")})
    public void test(@RequestParam("url")String url ,HttpServletResponse response ){
        boolean result = FileUtil.downLoadFile(url, response);
        if(!result){
            throw new BasicException(400,"下载错误");
        }
    }

}
