package com.telecomyt.item.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemApplicationTests {

    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    private RedisTemplate<String,Object>  redisTemplate;

    @Test
    public void redisTest() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("1","aaa");

        redisTemplate.opsForValue().set("user:id:01",map );
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


}
