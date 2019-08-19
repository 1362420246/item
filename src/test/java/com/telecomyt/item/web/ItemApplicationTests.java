package com.telecomyt.item.web;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.telecomyt.item.dto.UserVo;
import com.telecomyt.item.entity.CacheUser;
import com.telecomyt.item.utils.ImageUtils;
import com.telecomyt.item.utils.OperationUtils;
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
        String cacheUser =  (String)stringRedisTemplate.opsForHash().get("CacheUser", "13022119904140051");
        System.out.println(cacheUser);
        CacheUser cacheUser1 = JSONUtil.toBean(cacheUser, CacheUser.class);
        System.out.println(cacheUser1);
    }

    @Test
    public void operationUtils() {
        /*
        110101199910101239
        110110199910101237
        110226198909281411
        130425199712205532
        130828114211303796
         */
        UserVo userByCardId = OperationUtils.getUserByCardId("110226197908170010");
        CacheUser cacheUserrByCardId = OperationUtils.getCacheUserrByCardId("110226197908170010");
        System.out.println(userByCardId);
        System.out.println(cacheUserrByCardId);
    }



    @Test
    public void redisTest() {
        HashMap<String, Object> map = new HashMap<>();
        CacheUser cacheUser = CacheUser.builder().name("quboka").avatar("1111").uid("1111").build();
        CacheUser cacheUser2 = CacheUser.builder().name("zk").avatar("222").uid("222").build();
        map.put("1",cacheUser);
        map.put("2",cacheUser2);
        CacheUser cacheUser3 = CacheUser.builder().name("xm").avatar("333").uid("333").build();
        //kv存储
        redisTemplate.opsForValue().set("user:id:01",cacheUser  );
        redisTemplate.opsForValue().set("user:id:02",map  );
        //hash存储
        redisTemplate.opsForHash().putAll("user:id:03",map);
        redisTemplate.opsForHash().put("user:id:04", "3", cacheUser3);
        //set存储
        redisTemplate.opsForSet().add("user:id:05",cacheUser);
        redisTemplate.opsForSet().add("user:id:05",cacheUser);
        //list存储
        redisTemplate.opsForList().rightPush("user:id:06",cacheUser);
        redisTemplate.opsForList().rightPush("user:id:06",cacheUser2);
        redisTemplate.opsForList().leftPush("user:id:06",cacheUser3);
        redisTemplate.opsForList().rightPushAll("user:id:07",Arrays.asList(cacheUser2,cacheUser3));

        //设置有效期 :
        redisTemplate.expire("user:id:01",10, TimeUnit.SECONDS);
        redisTemplate.expire("user:id:02",10, TimeUnit.SECONDS);
        redisTemplate.expire("user:id:03",10, TimeUnit.SECONDS);
        redisTemplate.expire("user:id:04",10, TimeUnit.SECONDS);
        redisTemplate.expire("user:id:05",10, TimeUnit.SECONDS);
        redisTemplate.expire("user:id:06",10, TimeUnit.SECONDS);
        redisTemplate.expire("user:id:07",10, TimeUnit.SECONDS);

        //kv取值
        CacheUser cacheUser11 = (CacheUser) redisTemplate.opsForValue().get("user:id:01");
        System.out.println(cacheUser11);
        HashMap map11 = (HashMap) redisTemplate.opsForValue().get("user:id:02");
        System.out.println(map11);
        //hash取值
        HashMap map22 = (HashMap) redisTemplate.opsForHash().entries("user:id:03");
        System.out.println(map22);
        CacheUser cacheUser22  = (CacheUser) redisTemplate.opsForHash().get("user:id:04","3");
        System.out.println(cacheUser22);
        //set取值
        Set<Object> members =  redisTemplate.opsForSet().members("user:id:05");
        System.out.println(members);
        //list取值
        Long size = redisTemplate.opsForList().size("user:id:06");
        List<Object> range = redisTemplate.opsForList().range("user:id:06", 0, size - 1);
        System.out.println(range);
        //队列取法
        CacheUser cacheUser33 = (CacheUser)redisTemplate.opsForList().rightPop("user:id:06");
        System.out.println(cacheUser33);
        CacheUser cacheUser44 = (CacheUser)redisTemplate.opsForList().leftPop("user:id:06");
        System.out.println(cacheUser44);
        CacheUser cacheUser55 = (CacheUser)redisTemplate.opsForList().leftPop("user:id:08",10,TimeUnit.SECONDS);
        System.out.println(cacheUser55);
    }
    @Test
    public void stringRedisTest(){
        //stringRedisTemplate  泛型为<String,String>
        CacheUser cacheUser = CacheUser.builder().name("qbk").avatar("1111").uid("1111").build();
        JSONObject jsonObject = JSONUtil.parseObj(cacheUser, false);
        String str = JSONUtil.toJsonStr(jsonObject);
        stringRedisTemplate.opsForHash().put("user:id:01", "1", str);
        stringRedisTemplate.opsForValue().set("user:id:02",str);

        String strJson = (String)stringRedisTemplate.opsForHash().get("user:id:01", "1");
        System.out.println(strJson);
        CacheUser cacheUser1 = JSONUtil.toBean(strJson, CacheUser.class);
        System.out.println(cacheUser1);
        String strJson2 = stringRedisTemplate.opsForValue().get("user:id:02");
        CacheUser cacheUser2 = JSONUtil.toBean(strJson2, CacheUser.class);
        System.out.println(cacheUser2);
    }
}
