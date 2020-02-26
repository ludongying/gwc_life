package com.seven.gwc.core.util;

import com.alibaba.fastjson.JSONObject;
import com.seven.gwc.core.exception.ServiceException;
import com.seven.gwc.core.state.ErrorEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: TODO
 * @Date: 2020/2/24 23:47
 * @Author: GD
 * @Version: 1.0
 */
public class RenderUtil {
    public RenderUtil() {
    }

    public static void renderJson(HttpServletResponse response, ErrorEnum errorEnum) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", "false");
            jsonObject.put("content", null);
            jsonObject.put("message", errorEnum.getMessage());
            jsonObject.put("code", errorEnum.getCode());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonObject.toJSONString());
        } catch (IOException var3) {
            throw new ServiceException(ErrorEnum.ERROR_SYSTEM);
        }
    }
}
