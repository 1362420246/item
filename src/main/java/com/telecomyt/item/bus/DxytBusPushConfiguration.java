package com.telecomyt.item.bus;

import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author zhoupengbing
 * @packageName com.telecomyt.forum.component
 * @email zhoupengbing@telecomyt.com.cn
 * @description 总线推送配置类
 * @createTime 2019年07月17日 12:35:00
 * @Version v1.0
 */
public class DxytBusPushConfiguration {

    @Bean("dxytBusPush")
    public DxytBusPush dxytBusPush(){
        return new DxytBusPush();
    }

}
