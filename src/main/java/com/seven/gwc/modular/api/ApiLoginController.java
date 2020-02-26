package com.seven.gwc.modular.api;


import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.service.UserService;
import com.seven.gwc.modular.system.vo.GetTokenVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: GD
 * @Description: 登录控制器
 * @Date: 2019/12/06 13:58
 */
@RestController
@Api(tags = "登录接口")
@RequestMapping("/gwcApi/")
public class ApiLoginController extends BaseController {
    @Autowired
    private UserService userService;

    @PostMapping("/apiLogin")
    public BaseResult login(GetTokenVO getTokenVO) {
        if(ToolUtil.isEmpty(getTokenVO.getAccount()) && ToolUtil.isEmpty(getTokenVO.getPassword())){
            return new BaseResult().failure(ErrorEnum.ERROR_USER_BLANK);
        }
        return userService.login(getTokenVO);
    }

    /**
     * 测试接口是否走鉴权
     */
    @GetMapping("/test")
    public Object test() {
        ShiroUser user = ShiroKit.getUser();
        System.out.println(user.getId());
        System.out.println(user.getName());
        return SUCCESS;
    }
}
