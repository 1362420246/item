package com.qbk.web;

import com.qbk.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    @Test
    public void tTest(){
        User user1 = User.builder().id(1).build();
        User user2 = User.builder().id(2).build();
        User user3 = User.builder().id(3).build();

        //Redis操作的简单值(或在Redis术语“string”)。
        final ValueOperations<String, Object> kvString = redisTemplate.opsForValue();
        //kv存值
        kvString.set("1",user1);
        final User user = (User) kvString.get("1");
        System.out.println("普通string取值："+user);

        final HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        //hash存值
        hash.put("2","1",user1);
        hash.put("2","2",user2);
        final User user33 = (User)hash.get("2", "2");
        System.out.println("hash取值："+user33);
        final Map<Object, Object> entries = hash.entries("2");
        System.out.println("hash取值："+entries);

        final ListOperations<String, Object> list = redisTemplate.opsForList();
        //list存值
        list.leftPush("3",user1);
        list.leftPush("3",user2);
        //查询列表 key 中指定区间内的元素
        final List<Object> range = list.range("3", 0, -1);
        System.out.println("list取值："+range);

        final SetOperations<String, Object> set = redisTemplate.opsForSet();
        //set存值
        set.add("4",user1);
        set.add("4",user2);
        set.add("4",user3);
        //查询集合中的一个随机元素
        final User user4 = (User) set.randomMember("4");
        System.out.println("set取值："+user4);
        final Set<Object> users = set.distinctRandomMembers("4", 2);
        System.out.println("set取值："+users);

        final ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        //zset存值
        zSet.add("5",user1,2);
        zSet.add("5",user2,1);
        zSet.add("5",user3,3);
        //查询有序集 key 中，指定区间内的成员
        final Set<Object> range1 = zSet.range("5", 0, -1);
        System.out.println("zSet取值："+range1);

        final GeoOperations<String, Object> stringObjectGeoOperations = redisTemplate.opsForGeo();
        final ClusterOperations<String, Object> stringObjectClusterOperations = redisTemplate.opsForCluster();
        final HyperLogLogOperations<String, Object> stringObjectHyperLogLogOperations = redisTemplate.opsForHyperLogLog();
    }
}
