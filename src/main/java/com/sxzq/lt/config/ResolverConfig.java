package com.sxzq.lt.config;

import com.sxzq.lt.resolver.RemoteAddrKeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by liutao on 2018/9/5.
 */
@Configuration
public class ResolverConfig {

    @Bean(name = RemoteAddrKeyResolver.BEAN_NAME)
    public RemoteAddrKeyResolver remoteAddrKeyResolver() {
        return new RemoteAddrKeyResolver();
    }
}
