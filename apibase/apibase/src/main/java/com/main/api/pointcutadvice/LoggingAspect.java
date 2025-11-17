package com.main.api.pointcutadvice;

import com.base.common.util.DateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("["+ DateUtil.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS +"]");
        }
    };

    @Pointcut("execution(* *(..)) && @annotation(com.main.api.pointcutadvice.annotations.Loggable)")
    public void loggableMethods() {
    }

    @Before("loggableMethods()")
    public void logMethodBefore(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        logger.info("START - Executing method \"" + methodName + "\" at time " + sdf.get().format(new Date()));
    }

    @After("loggableMethods()")
    public void logMethodAfter(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        logger.info("END - Executing method \"" + methodName + "\" at time " + sdf.get().format(new Date()));
    }

    @AfterThrowing("loggableMethods()")
    public void logMethodAfterThrowing(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        logger.error("Executing method \"" + methodName + "\" at time " + sdf.get().format(new Date()));
    }
}
