package com.seven.gwc.modular.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.exception.BusinessException;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.util.FileUtil;
import com.seven.gwc.modular.system.dto.UserDTO;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.DeptService;
import com.seven.gwc.modular.system.service.UserService;
import com.seven.gwc.modular.system.vo.UserUpdatePsdVO;
import com.seven.gwc.modular.system.vo.UserUpdateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
    @Value("${FILE_UPLOAD_PATH_IMAGE}")
    private String uploadPathImage;

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


    @PostMapping("avatarUpload")
    @ApiOperation(value = "上传头像(SHQ)")
    public BaseResult avatarUpload(HttpServletRequest request, @RequestBody JSONObject avatar) {
        try {
            String userId = request.getAttribute("userId").toString();
            String avatarCode = URLDecoder.decode(avatar.get("avatar").toString(), "UTF-8");
            String path = FileUtil.base64ToFile(uploadPathImage,avatarCode);

            if (path == null) {
                return new BaseResult().failure(ErrorEnum.ERROR_ILLEGAL_PARAMS);
            }
            UserEntity entity = userService.getById(userId);
            entity.setAvatar(path);
            userService.updateById(entity);
            return new BaseResult().content(path);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new BaseResult().failure(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
    }

}

