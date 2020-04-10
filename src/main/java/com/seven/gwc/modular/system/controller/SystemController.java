package com.seven.gwc.modular.system.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.exception.BusinessException;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.util.FileUtil;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * description : 通用控制器
 *
 * @author : GD
 * @date : 2019-11-28
 */
@Controller
@RequestMapping("system")
public class SystemController extends BaseController {
    @Autowired
    private UserService userService;
    @Value("${FILE_UPLOAD_PATH_FILE}")
    private String uploadPathFile;
    @Value("${FILE_UPLOAD_PATH_IMAGE}")
    private String uploadPathImage;
    @Value("${server.ip}")
    private String ip;


    /**
     * 主页面
     */
    @RequestMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("lawType", "10");
        return "modular/frame/welcome";
    }

    @RequestMapping("/map")
    public String map() {
        return "modular/map/map";
    }

    /**
     * 主题页面
     */
    @RequestMapping("/theme")
    public String theme() {
        return "modular/frame/theme";
    }

    /**
     * 项目介绍
     */
    @RequestMapping("/console")
    public String console() {
        return "modular/frame/console";
    }

    /**
     * 跳转到查看用户详情页面
     */
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        ShiroUser user = ShiroKit.getUser();
        UserEntity userEntity = this.userService.getById(user.getId());

        model.addAllAttributes(BeanUtil.beanToMap(userEntity));
        model.addAttribute("roleName", CacheFactory.me().getRoleName(userEntity.getRoleId()));
        model.addAttribute("deptName", CacheFactory.me().getDeptName(userEntity.getDeptId()));
        return "modular/frame/user_info";
    }

    /**
     * 获取当前用户详情
     */
    @RequestMapping("/currentUserInfo")
    @ResponseBody
    public BaseResult getUserInfo() {
        ShiroUser currentUser = ShiroKit.getUser();

        UserEntity userEntity = userService.getById(currentUser.getId());

        HashMap<Object, Object> hashMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        if (userEntity != null) {
            map = BeanUtil.beanToMap(userEntity);
            map.remove("password");
            map.remove("salt");
            map.put("birthday", DateUtil.formatDate(userEntity.getBirthday()));
        }

        hashMap.putAll(map);
        hashMap.put("roleName", CacheFactory.me().getRoleName(userEntity.getRoleId()));
        hashMap.put("deptName", CacheFactory.me().getDeptName(userEntity.getDeptId()));

        return new BaseResult(true, 200, "请求成功", hashMap);
    }

    /**
     * 跳转到修改密码界面
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return "modular/frame/password";
    }

    /**
     * 个人消息列表
     */
    @RequestMapping("/message")
    public String message() {
        return "modular/frame/message";
    }


    @PostMapping(path = "/updateAvatar")
    @ResponseBody
    public BaseResult updateAvatar(String portraitUrl) {
        ShiroUser user = ShiroKit.getUserNotNull();
        user.setAvatar(portraitUrl);
        userService.changeAvatar(portraitUrl);
        return SUCCESS;
    }


    /**
     * 通用的树列表选择器
     */
    @RequestMapping("/commonTree")
    public String deptTreeList(String formName, String formId, String treeUrl, Model model) {
        if (ToolUtil.isOneEmpty(formName, formId, treeUrl)) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        try {
            model.addAttribute("formName", URLDecoder.decode(formName, "UTF-8"));
            model.addAttribute("formId", URLDecoder.decode(formId, "UTF-8"));
            model.addAttribute("treeUrl", URLDecoder.decode(treeUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }

        return "/common/tree_dlg";
    }


    /**
     * layui上传组件 单个图片上传
     */
    @PostMapping(path = "/uploadImage")
    @ResponseBody
    public BaseResult layuiUpload(@RequestPart("file") MultipartFile picture) {
        String pictureName = UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(picture.getOriginalFilename());
        FileUtil.fileIsExist(uploadPathImage);
        try {
            picture.transferTo(new File(uploadPathImage + File.separator + pictureName));
        } catch (Exception e) {
            throw new BusinessException(ErrorEnum.UPLOAD_ERROR);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("pictureName", pictureName);
        return new BaseResult().content("上传生成", map);
    }


    /**
     * 附件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public JSONObject uploadFile(@RequestParam("file") MultipartFile file) {
        return FileUtil.uploadFile(uploadPathFile, file, ip, "picture");
    }

    /**
     * 视频上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadVideo")
    @ResponseBody
    public JSONObject uploadVideo(@RequestParam("file") MultipartFile file) {
        return FileUtil.uploadFile(uploadPathFile, file, ip, "video");
    }

    /**
     * 文件下载
     * @param response
     * @param fileName
     */
    @RequestMapping(value = "downloadFile")
    public void downloadFile(HttpServletResponse response, @RequestParam String fileName) {
        FileUtil.download(uploadPathFile, fileName, response);
    }

    /**
     * 删除文件
     * @param fileName 文件名
     * @return
     */
    @RequestMapping(value = "deleteFile")
    @ResponseBody
    public JSONObject deleteFile(String fileName) {
        return FileUtil.deleteFile(uploadPathFile + "/" + fileName, fileName);
    }

    @RequestMapping(value = "previewPdf")
    public String previewPdf(@RequestParam String fileName, Model model) {
        String file = ToolUtil.getFileName(fileName);
        model.addAttribute("file", file);
        model.addAttribute("fileName", fileName);
        return "/common/previewPdf";
    }

    @GetMapping("/previewFile")
    @ResponseBody
    @ApiOperation(value = "预览文件")
    public ResponseEntity<Resource> previewFile(HttpServletResponse response,
                                                @ApiParam(name = "filePath", value = "文件路径") String filePath) {
        return FileUtil.previewFile(filePath, response);
    }

    /**
     * 跳转到视频播放页面
     */
    @RequestMapping("/videoPlayback")
    public String videoPlayback(String videoName, Model model) {
        model.addAttribute("videoName", videoName);
        return "/common/videoPlayback";
    }
}
