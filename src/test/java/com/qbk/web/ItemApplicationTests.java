package com.qbk.web;

import com.google.common.collect.Maps;
import com.qbk.entity.param.TestRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemApplicationTests {

    @Autowired
    private RestTemplate restTemplate ;

    /**
     * post请求
     */
    private static <T> void post(RestTemplate restTemplate,String url,T body){
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        //HttpEntity
        HttpEntity<T> requestEntity = new HttpEntity<>(body, requestHeaders);
        //post
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        //断言
        Assert.notNull(responseEntity, "response is null");
        System.out.println(responseEntity.getBody());
    }
    /**
     * get请求
     */
    private static void get(RestTemplate restTemplate,String url,Map<String,Object> params){
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        //requestHeaders.add(Authorization, token);
        //HttpEntity
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(null, requestHeaders);
        //get
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class, params);
        //断言
        Assert.notNull(responseEntity, "response is null");
        System.out.println(responseEntity.getBody());
    }
    @Test
    public void httpTest() {
        String url = "http://localhost:8080/download";
        Map<String,Object> params = Maps.newHashMap();
        get(restTemplate,url,params);
    }

    @Test
    public void httpTest1() {
        String url1 = "http://localhost:8080/test/v1.1/param_test";
        String url3 = "http://localhost:8080/test/v1.3/param_test";
        TestRequest testRequest = TestRequest.builder()
                .username("qbk123")
                .password("123456abc")
                .tel("13333333333")
                .mail("133@133.com")
                .build();
        post(restTemplate,url3,testRequest);
    }

    @Test
    public void httpTest2() {
        String url2 = "http://localhost:8080/test/v1.2/param_test";
        String url4 = "http://localhost:8080/test/v1.4/param_test";
        TestRequest testRequest = TestRequest.builder()
                .username("qbk123")
                .password("123456abc")
                .tel("13333333333")
                .mail("133@133.com")
                .build();
        TestRequest testRequest2 = TestRequest.builder()
                .username("qbk456")
                .password("123456dfc")
                .tel("13222222222")
                .mail("122@122.com")
                .build();
        List<TestRequest> list = Arrays.asList(testRequest,testRequest2);
        post(restTemplate,url4,list);
    }

}
