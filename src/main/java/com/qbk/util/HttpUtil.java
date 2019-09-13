package com.qbk.util;

import com.alibaba.fastjson.JSON;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletResponse;

@UtilityClass
public class HttpUtil {

    /**
     * 发送错误json消息
     */
    public void responseSendErrorJson( HttpServletResponse httpServletResponse,String msg){
        responseSendJson(httpServletResponse,BaseResultGenerator.error(msg));
    }
    /**
     * 发送成功json消息
     */
    public void responseSendsuccessJson( HttpServletResponse httpServletResponse,String msg){
        responseSendJson(httpServletResponse,BaseResultGenerator.success(msg));
    }
    public void responseSendJson(HttpServletResponse httpServletResponse, BaseResult baseResult ){
        try {
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setStatus(200);
            httpServletResponse.getWriter().write(JSON.toJSONString(baseResult));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
