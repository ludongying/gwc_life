package com.seven.gwc.modular.api;


import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.service.UserService;
import com.seven.gwc.modular.system.vo.GetTokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器
 *
 * @author : GD
 * @date : 2020/02/26 20:00
 */
@RestController
@Api(tags = "登录接口")
@RequestMapping("gwcApi")
public class LoginApi extends BaseController {
    @Autowired
    private UserService userService;

    @PostMapping("/apiLogin")
    @ApiOperation(value = "登录验证(SHQ)")
    public BaseResult login(GetTokenVO getTokenVO) {
        if(ToolUtil.isEmpty(getTokenVO.getAccount()) && ToolUtil.isEmpty(getTokenVO.getPassword())){
            return new BaseResult().failure(ErrorEnum.ERROR_USER_BLANK);
        }
        return userService.login(getTokenVO);
    }
}
