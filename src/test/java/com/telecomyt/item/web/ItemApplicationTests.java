package com.telecomyt.item.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemApplicationTests {

    @Autowired
    private RestTemplate restTemplate ;

    @Test
    public void contextLoads() {
//        //headers
//        HttpHeaders requestHeaders = new HttpHeaders();
//        //HttpEntity
//        HttpEntity<Map> requestEntity = new HttpEntity<>(null, requestHeaders);
//        //参数
//        Map<String,Object> params = new HashMap<>();
//        params.put("cardid",139.23);
//        params.put("startTime",139.43);
//        params.put("startLat",123.3);
//        params.put("endLat",123.3);
//        params.put("unitId","077100");
//        params.put("alarmType",2);
//        String quote2 = restTemplate.exchange("http://localhost:8083/schedule/query" +
//                "?startLng={startLng}&endLng={endLng}&startLat={startLat}&endLat={endLat}&unitId={unitId}&alarmType={alarmType}", HttpMethod.GET,requestEntity,String.class,params).getBody();
//        System.out.println(quote2);

    }

}
