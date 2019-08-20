package com.telecomyt.item.config;

import com.telecomyt.item.constant.CommonConstant;
import com.telecomyt.item.util.FileUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(15000);
        requestFactory.setReadTimeout(25000);
        return new RestTemplate(requestFactory);
    }

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String homePath = FileUtil.getHomePath();
        //映射路径上传文件的
        registry.addResourceHandler(CommonConstant.REPORTING_PATH + "**")
                .addResourceLocations("file:"+ homePath + CommonConstant.REPORTING_PATH );
    }

}
