package com.sxzq.lt.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by liutao on 2018/9/5.
 * 用于限流的键的解析器
 */
public class RemoteAddrKeyResolver implements KeyResolver {

    public static final String BEAN_NAME = "remoteAddrKeyResolver";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
