package com.telecomyt.item.web;

import com.telecomyt.item.dto.TaskVo;
import com.telecomyt.item.entity.Task;
import com.telecomyt.item.web.mapper.TaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemApplicationTests {

    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    private TaskMapper taskMapper ;

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
//        http://127.0.0.1:8083/schedule/query?cardid=22222&startTime=2019-07-29%2013:13:13&endTime=2019-08-30%2013:13:13&dateType=3
        List<TaskVo> tasks = taskMapper.getTaskByCardIdAndDate("22222",
                LocalDateTime.of(2019, 7,29,0,0,0),LocalDateTime.now());
        System.out.println(tasks);


    }

}
