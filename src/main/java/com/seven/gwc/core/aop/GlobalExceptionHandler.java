package com.seven.gwc.core.aop;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.exception.ServiceException;
import com.seven.gwc.core.state.LogTypeEnum;
import com.seven.gwc.modular.system.service.LoginLogService;
import com.seven.gwc.core.util.HttpContext;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 */
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    @Autowired
    private LoginLogService loginLogService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 业务异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResult exceptionHandler(Exception ex) {
        if (ex instanceof ServiceException){
            ServiceException serverException = (ServiceException) ex;
            return new BaseResult(serverException.getErrorEnum());
        }else {
            logger.error(ex.getMessage(), ex);
            return new BaseResult(ErrorEnum.ERROR_SYSTEM);
        }
    }

    /**
     * 用户未登录异常
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unAuth(AuthenticationException e) {
        logger.error("用户未登陆：", e);
        return "/login";
    }

    /**
     * 账号被冻结异常
     */
    @ExceptionHandler(DisabledAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String accountLocked(Model model) {
        String username = HttpContext.getRequest().getParameter("username");
        loginLogService.insert(LogTypeEnum.LOGIN_FAIL, null, "帐号：" + username + "账号被冻结", HttpContext.getIp());
        model.addAttribute("tips", "账号被冻结");
        return "/login";
    }

    /**
     * 账号密码错误异常
     */
    @ExceptionHandler(CredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String credentials(Model model) {
        String username = HttpContext.getRequest().getParameter("username");
        loginLogService.insert(LogTypeEnum.LOGIN_FAIL, null, "帐号：" + username + "账号密码错误", HttpContext.getIp());
        model.addAttribute("tips", "账号密码错误");
        return "/login";
    }

    /**
     * 无权访问该资源异常
     */
    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public BaseResult credentials(UndeclaredThrowableException e) {
        logger.error("权限异常", e);
        return new BaseResult(ErrorEnum.NO_POWER_TO_RESOURCES);
    }

}
