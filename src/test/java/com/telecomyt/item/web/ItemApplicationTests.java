package com.telecomyt.item.web;

import cn.hutool.json.JSONUtil;
import com.telecomyt.item.entity.CacheUser;
import com.telecomyt.item.utils.ImageUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemApplicationTests {

    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    @Test
    public void redisTest() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("1","aaa");

        redisTemplate.opsForValue().set("user:id:01",map  );
        redisTemplate.opsForHash().putAll("user:id:02",map);
        redisTemplate.opsForHash().put("user:id:03", "port", 222);
        System.out.println(11);
        //设置有效期 :
        redisTemplate.expire("user:id:01",10, TimeUnit.SECONDS);
        redisTemplate.expire("user:id:02",10, TimeUnit.SECONDS);
        redisTemplate.expire("user:id:03",10, TimeUnit.SECONDS);
    }

    @Test
    public void httpTest() {
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        //HttpEntity
        HttpEntity<Map> requestEntity = new HttpEntity<>(null, requestHeaders);
        //参数
        Map<String,Object> params = new HashMap<>();
        params.put("cardid",139.23);
        params.put("startTime",139.43);
        params.put("startLat",123.3);
        params.put("endLat",123.3);
        params.put("unitId","077100");
        params.put("alarmType",2);
        String quote2 = restTemplate.exchange("http://localhost:8083/schedule/query" +
                "?startLng={startLng}&endLng={endLng}&startLat={startLat}&endLat={endLat}&unitId={unitId}&alarmType={alarmType}", HttpMethod.GET,requestEntity,String.class,params).getBody();
        System.out.println(quote2);

    }


    /**
     * 生成假数据
     */
    @Test
    public void redisAdd() {
        /*
           13022119904140051 张凯
           13022119904140052 江滨
           13022119904140053 郭钊
           13022119904140054 张盛飞
           13022119904140055 曲博卡
         */
             /*
        http://39.106.33.10:8083/reporting/tx/qbk.png
        http://39.106.33.10:8083/reporting/tx/zk.png
        http://39.106.33.10:8083/reporting/tx/jb.png
        http://39.106.33.10:8083/reporting/tx/gz.png
        http://39.106.33.10:8083/reporting/tx/zsf.png
         */
        List<String> names = Arrays.asList("张凯","江滨","郭钊","张盛飞","曲博卡");
        List<String> ids = Arrays.asList("13022119904140051","13022119904140052","13022119904140053","13022119904140054","13022119904140055");
        List<String> urls = Arrays.asList(
                "http://39.106.33.10:8083/reporting/tx/zk.png"
                ,"http://39.106.33.10:8083/reporting/tx/jb.png"
                ,"http://39.106.33.10:8083/reporting/tx/gz.png"
                ,"http://39.106.33.10:8083/reporting/tx/zsf.png"
                ,"http://39.106.33.10:8083/reporting/tx/qbk.png");

        for (int i = 0; i < 5 ; i++) {
            CacheUser cacheUser = CacheUser.builder().name(names.get(i)).avatar(urls.get(i)).uid(ids.get(i)).build();
            stringRedisTemplate.opsForHash().put("CacheUser", ids.get(i), JSONUtil.toJsonStr(JSONUtil.parseObj( cacheUser, false))
            );
        }

//        List<CacheUser> cacheUserList = new ArrayList<>();
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        cacheUserList.stream().forEach( cacheUser -> {
//            stringStringHashMap.put( cacheUser.getUid(), JSONUtil.toJsonStr(JSONUtil.parseObj( cacheUser, false)));
//        } );
//        redisTemplate.opsForHash().putAll( "CacheUser", stringStringHashMap  );
    }

    @Test
    public void redisQuery() {
        String cacheUser = (String)stringRedisTemplate.opsForHash().get("CacheUser", "13022119904140051");
        CacheUser cacheUser1 = JSONUtil.toBean(cacheUser, CacheUser.class);
        System.out.println(cacheUser1);

    }

}
