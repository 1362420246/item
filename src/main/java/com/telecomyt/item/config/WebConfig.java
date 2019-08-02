package com.telecomyt.item.config;

import com.telecomyt.item.constant.CommonConstants;
import com.telecomyt.item.utils.FileUtil;
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
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String homePath = FileUtil.getHomePath();
        registry.addResourceHandler(CommonConstants.REPORTING_PATH + "**")
                .addResourceLocations(homePath + CommonConstants.REPORTING_PATH );
    }

}
