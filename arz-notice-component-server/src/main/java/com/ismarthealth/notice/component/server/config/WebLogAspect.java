package com.ismarthealth.notice.component.server.config;

import com.ismarthealth.notice.component.server.common.Result;
import com.ismarthealth.notice.component.server.exception.BaseException;
import com.ismarthealth.osp.core.common.enums.ReturnCodeEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;


@Aspect
@Component
public class WebLogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.ismarthealth.notice.component.server.controller.*.*(..))")
    public void pointLog() {
    }

    @Around("pointLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        StringBuilder sb = new StringBuilder(512);
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = attributes.getRequest();
        sb.append("{\"REQUEST_TIME\":\"").append(LocalDate.now()).append(' ').append(LocalTime.now()).append("\",");
        sb.append("\"URL\":\"").append(request.getRequestURL().toString()).append("\",");
        sb.append("\"PARAMS\":\"").append(request.getQueryString()).append("\",");
        sb.append("\"IP\":\"").append(request.getRemoteAddr()).append("\",");
        sb.append("\"TOKEN\":\"").append(request.getHeader("token")).append("\",");
        sb.append("\"ARGS\":\"").append(Arrays.toString(pjp.getArgs())).append("\",");
        Object result;
        try {
            result = pjp.proceed();
        } catch (Exception e) {
            long executeTime = System.currentTimeMillis() - startTime;
            // 判断是业务异常还是系统异常
            if (e instanceof BaseException) {
                BaseException baseException = (BaseException) e;
                result = new Result(baseException.getReturnCode(), baseException.getMessage());
            } else {
                result = new Result(ReturnCodeEnum.CODE_3640000.getCode(), e.getMessage());
            }
            sb.append("\"EXECUTE_TIME\":").append(executeTime).append(',');
            sb.append("\"RESULT\":").append(result).append('}');
            if (e instanceof BaseException) {
                // 业务异常打info日志
                logger.info(sb.toString());
            } else {
                // 系统异常打Error日志
                logger.error(sb.toString());
            }
            // 将异常再次抛出
            throw e;
        }

        // 记录请求结果
        long executeTime = System.currentTimeMillis() - startTime;
        sb.append("\"EXECUTE_TIME\":").append(executeTime).append(',');
        sb.append("\"RESULT\":").append(result).append('}');
        logger.info(sb.toString());
        return result;
    }

}