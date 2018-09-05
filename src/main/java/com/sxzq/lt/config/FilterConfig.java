package com.sxzq.lt.config;

import com.sxzq.lt.filter.ElapsedGatewayFilterFactory;
import com.sxzq.lt.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by liutao on 2018/9/3.
 */
@Configuration
public class FilterConfig {

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }

    @Bean
    public ElapsedGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new ElapsedGatewayFilterFactory();
    }
}
