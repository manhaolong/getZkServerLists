package com.ismarthealth.notice.component.server.exception;


import com.ismarthealth.notice.component.server.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕捉处理
 * ExceptionHandle
 *
 * @author Yong
 * 创建日期：2018年6月30日
 * Description:
 */
@ControllerAdvice
public class ExceptionHandle {

    private Logger errorLogger = LoggerFactory.getLogger("errorLog");

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 全局异常捕捉处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        // 捕获手动抛出的异常
        if (e instanceof BaseException) {
            e.printStackTrace();
            BaseException baseException = (BaseException) e;
            return new Result(baseException.getReturnCode(), baseException.getMessage());
        }
        e.printStackTrace();
        errorLogger.error("errorMessage", e);
        return new Result(3590000, e.getMessage());
    }
}