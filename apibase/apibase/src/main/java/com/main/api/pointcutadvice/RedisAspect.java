package com.main.api.pointcutadvice;

import com.base.common.payload.LoginRequest;
import com.base.common.util.DateUtil;
import com.main.api.model.BlackListRedis;
import com.main.api.service.BlackListRedisService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
public class RedisAspect {
    private static final Logger logger = LoggerFactory.getLogger(RedisAspect.class);
    private ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("["+ DateUtil.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS +"]");
        }
    };
    @Autowired
    private BlackListRedisService blackListRedisService;

    @Pointcut("within(com.main.api.service.impl.AuthServiceImpl*) && @annotation(com.main.api.pointcutadvice.annotations.RedisCache)")
    public void cacheRedisMethods() {
    }

    @AfterReturning(pointcut = "cacheRedisMethods()", returning = "returnObject")
    public void putInCache(final JoinPoint jp, final Object returnObject) {
        String methodName = jp.getSignature().getName();
        logger.info("Executing method \"" + methodName + "\" at time " + sdf.get().format(new Date()));
        if (returnObject != null) {
            String token = returnObject.toString();
            for (Object o: jp.getArgs()) {
                if (o instanceof LoginRequest) {
                    LoginRequest loginRequest = (LoginRequest)o;
                    BlackListRedis blackListRedis = new BlackListRedis();
                    blackListRedis.setUserName(loginRequest.getEmail());
                    blackListRedis.setToken(token);
                    blackListRedisService.addBlackList(blackListRedis);
                }
            }
        }
    }
}
