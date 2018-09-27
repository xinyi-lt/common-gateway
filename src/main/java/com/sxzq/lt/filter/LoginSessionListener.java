package com.sxzq.lt.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @desc session监听器
 * @date 2018/7/17 11:16
 **/
public class LoginSessionListener implements SessionListener {

    private final static Logger logger = LoggerFactory.getLogger(LoginSessionListener.class);

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
        logger.info("登录+1=="+sessionCount.get());
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
        logger.info("登录退出-1=="+sessionCount.get());
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
        logger.info("登录过期-1=="+sessionCount.get());
    }
}
