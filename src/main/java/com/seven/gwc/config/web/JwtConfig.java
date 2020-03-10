package com.seven.gwc.config.web;

import com.seven.gwc.config.constant.JwtConstants;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.jwt.JwtTokenUtil;
import com.seven.gwc.core.util.RenderUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Rest Api接口鉴权
 *
 * @author stylefeng
 * @date 2020/2/26 15:11
 */
public class JwtConfig extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
            return true;
        }
        return check(request, response);
    }

    private boolean check(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getServletPath());
        if (request.getServletPath().equals(JwtConstants.AUTH_PATH)) {
            return true;
        }
        if (request.getServletPath().equals(JwtConstants.PDF_PATH)) {
            return true;
        }
        final String requestHeader = request.getHeader(JwtConstants.AUTH_HEADER);
        String authToken;
        if (requestHeader != null && requestHeader.startsWith("bearer;")) {
            authToken = requestHeader.substring(7);

            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = JwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    RenderUtil.renderJson(response, ErrorEnum.TOKEN_EXPIRED);
                    return false;
                } else {
                    Claims claims = JwtTokenUtil.getClaimFromToken(authToken);
                    request.setAttribute("userId", claims.get("userId").toString());
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                RenderUtil.renderJson(response, ErrorEnum.TOKEN_ERROR);
                return false;
            }
        } else {
            //header没有带bearer;字段
            RenderUtil.renderJson(response, ErrorEnum.TOKEN_ERROR);
            return false;
        }
        return true;
    }

}
