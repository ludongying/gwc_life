package com.seven.gwc.modular.api;

import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.exception.BusinessException;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.modular.system.dto.UserDTO;
import com.seven.gwc.modular.system.service.DeptService;
import com.seven.gwc.modular.system.service.UserService;
import com.seven.gwc.modular.system.vo.UserUpdatePsdVO;
import com.seven.gwc.modular.system.vo.UserUpdateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用戶控制器
 *
 * @author : GD
 * @date : 2019-09-02
 */
@RestController
@Api(tags = "用戶接口")
@RequestMapping("gwcApi/user")
public class UserApi extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;
    @Value("${server.ip}")
    private String ip;

    @GetMapping(value = "/getUser")
    @ApiOperation(value = "获取用户(SHQ)")
    public BaseResult<UserDTO> getUser(HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        UserDTO user = userService.getUser(userId);
        user.setDept(deptService.getDept(userId));
        user.setAvatar(ip + user.getAvatar());
        return new BaseResult().content(user);
    }

    @PutMapping(value = "/updatePassword")
    @ApiOperation(value = "修改密码(SHQ)")
    public BaseResult<UserDTO> updatePassword(HttpServletRequest request, UserUpdatePsdVO userUpdatePsdVO) {
        String userId = request.getAttribute("userId").toString();
        if (!userService.changePwd(userUpdatePsdVO.getOldPassword(), userUpdatePsdVO.getNewPassword(), userId)) {
            throw new BusinessException(ErrorEnum.OLD_PWD_NOT_RIGHT);
        }
        return SUCCESS;
    }

    @PutMapping(value = "/updateUser")
    @ApiOperation(value = "修改用戶信息(SHQ)")
    public BaseResult<UserDTO> updateUser(HttpServletRequest request, UserUpdateVO userUpdateVO) {
        String userId = request.getAttribute("userId").toString();
        if (!userService.changeUser(userUpdateVO, userId)) {
            throw new BusinessException(ErrorEnum.ERROR_UPDATE);
        }
        return SUCCESS;
    }

}

