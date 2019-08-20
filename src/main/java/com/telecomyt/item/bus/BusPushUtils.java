package com.telecomyt.item.bus;


import com.telecomyt.item.bus.data.Creator;
import com.telecomyt.item.bus.data.SendNoticeRequestData;
import com.telecomyt.item.bus.data.SendNoticeRequestMessageData;
import com.telecomyt.item.bus.data.SendNoticeRequestParamData;
import com.telecomyt.item.constant.PushConstant;
import com.telecomyt.item.entity.CacheUser;
import com.telecomyt.item.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoupengbing
 * @packageName com.telecomyt.forum.utils
 * @email zhoupengbing@telecomyt.com.cn
 * @description 总先推送
 * @createTime 2019年07月17日 15:43:00
 * @Version v1.0
 */
@Service
@Slf4j
public class BusPushUtils {

    @Autowired
    private DxytBusPushConfig dxytBusPushConfig;

    /**
     * 总线统一推送（根据用户名进行推送）
     *
     * @methodName sendNoticeByUsername
     * @description 总线统一推送（根据用户名进行推送）
     * @author zhoupengbing
     * @param from 操作者信息
     * @param toUsers 接收者信息
     * @param message 通知具体内容
     * @updateTime 2019/7/17 16:06
     * @return java.lang.String
     * @throws
     */
    public String sendNoticeByUsername(CacheUser from, List<String>toUsers, SendNoticeRequestMessageData message){
        try{
            //TODO 默认用户信息
            if(ObjectUtils.isEmpty(from)){
                from = OperationUtils.getCacheUserrByCardId("110101199910101232") ;
                if(from != null){
                    Creator creator = Creator.builder().nickName(from.getName()).username(from.getUid()).build();
                    String notification = String.format(PushConstant.PUBLISHING_TASK , from.getName()) ;
                    message.getItem().setCreator(creator);
                    message.setNotification(notification);
                }
            }
            String sendNoticeByUsernameUrl = dxytBusPushConfig.getBASE_API_URL();
            //当前时间
            String date = DateUtil.getNow();
            String fwqqBwbh = "SR020001011091"+date+ ArrayUtil.correctNumber((int)(Math.random()*1000)+"");
            Map<String,Object> obj = new HashMap<>();
            obj.put("BWLYIPDZ",dxytBusPushConfig.getBWLYIPDZ());
            obj.put("FWQQ_BWBH",fwqqBwbh);
            obj.put("FWQQ_RQSJ",date);
            obj.put("BWLYDKH",dxytBusPushConfig.getBWLYDKH());
            obj.put("FWQQZ_ZCXX",dxytBusPushConfig.getFWQQZ_ZCXX());
            obj.put("FWBS",dxytBusPushConfig.getFWBS());
            obj.put("FFBS",dxytBusPushConfig.getFFBS());
            if(from != null){
                obj.put("XXCZRY_XM",from.getName());
                obj.put("XXCZRY_GMSFHM",from.getUid());
                obj.put("FWQQSB_BH",from.getImei());
                obj.put("XXCZRY_GAJGJGDM",from.getDeptNo());
            }
            obj.put("FWQQ_SJSJLX",dxytBusPushConfig.getFWQQ_SJSJLX());
            //设置请求的具体参数信息
            SendNoticeRequestData data = new SendNoticeRequestData();
            SendNoticeRequestParamData params = new SendNoticeRequestParamData();
            //通知内容
            params.setMessage(message);
            params.setPackageName(dxytBusPushConfig.getPACKAGE_NAME());
            if(!CollectionUtils.isEmpty(toUsers)){
                params.setUsernames(toUsers);
            }
            data.setMethod(dxytBusPushConfig.getFFMC());
            data.setParams(params);
            obj.put("FWQQ_NR",data);
            String paramsJson = GsonUtil.toJson(obj);
            log.info("推送总线参数:{}",paramsJson);
            String response = HttpPost.addJson(sendNoticeByUsernameUrl,paramsJson,"utf-8");
            log.info("推送总线成功，返回信息为:{}",response);
            return response;
        }catch(Exception e){
            log.error("总线推送出现异常",e.getCause());
        }
        return null;
    }

}
