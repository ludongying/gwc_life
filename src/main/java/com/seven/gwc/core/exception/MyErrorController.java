package com.seven.gwc.core.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * description : TODO
 * @date : 2020/2/18 15:19
 * @author : GD
 * @version : 1.0
 */
@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 500){
            return "/500";
        }else if(statusCode == 404){
            return "/404";
        }else{
            return "/500";
        }

    }

    /**
     * 跳转到session超时页面
     */
    @RequestMapping(path = "/sessionError")
    public String errorPageInfo(Model model) {
        model.addAttribute("tips", "session超时");
        return "/login";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
