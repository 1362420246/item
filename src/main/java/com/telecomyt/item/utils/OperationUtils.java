package com.telecomyt.item.utils;

import cn.hutool.json.JSONUtil;
import com.telecomyt.item.constant.CommonConstant;
import com.telecomyt.item.dto.TaskIdState;
import com.telecomyt.item.dto.UserVo;
import com.telecomyt.item.entity.CacheUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务工具
 */
@Component
public class OperationUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    private static StringRedisTemplate redisTemplate ;

    @PostConstruct
    public void init(){
        redisTemplate = this.stringRedisTemplate;
    }

    /**
     * 通过身份证号获取用户信息
     */
    public static UserVo getUserByCardId(String cardId){
        String cacheUserStr = (String)redisTemplate.opsForHash().get(CommonConstant.CACHE_USER_KEY, cardId);
        CacheUser cacheUser = JSONUtil.toBean(cacheUserStr, CacheUser.class);
        return UserVo.builder().cardId(cardId).name(cacheUser.getName()).headPortrait(cacheUser.getAvatar()).build();
    }

    /**
     * 通过身份证号获取用户信息
     */
    public static CacheUser getCacheUserrByCardId(String cardId){
        String cacheUserStr = (String)redisTemplate.opsForHash().get(CommonConstant.CACHE_USER_KEY, cardId);
        return JSONUtil.toBean(cacheUserStr, CacheUser.class);
    }



    /**
     * 通过身份证号获取用户信息
     */
    public static List<UserVo> getUsersByCardIds(List<String> cardIds){
        return cardIds.stream().map(cardId -> {
            String cacheUserStr = (String) redisTemplate.opsForHash().get(CommonConstant.CACHE_USER_KEY, cardId);
            CacheUser cacheUser = JSONUtil.toBean(cacheUserStr, CacheUser.class);
            return UserVo.builder().cardId(cardId).name(cacheUser.getName()).headPortrait(cacheUser.getAvatar()).build();
        }).collect(Collectors.toList());
    }

    /**
     * 通过身份证号获取用户信息 ,并带状态
     */
    public static List<UserVo> getUsersByTaskIdState(List<TaskIdState> taskIdStates ){
        return taskIdStates.stream().map( taskIdState -> {
            String cardId = taskIdState.getCardId();
            Integer taskState = taskIdState.getTaskState();
            String cacheUserStr = (String) redisTemplate.opsForHash().get(CommonConstant.CACHE_USER_KEY, cardId);
            CacheUser cacheUser = JSONUtil.toBean(cacheUserStr, CacheUser.class);
            return UserVo.builder().cardId(cardId).name(cacheUser.getName()).headPortrait(cacheUser.getAvatar()).taskState(taskState).build();
        }).collect(Collectors.toList());
    }
}
