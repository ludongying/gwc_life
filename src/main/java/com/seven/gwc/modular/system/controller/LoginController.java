package com.seven.gwc.modular.system.controller;

import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.node.MenuNode;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.LogTypeEnum;
import com.seven.gwc.core.util.SysUtil;
import com.seven.gwc.modular.system.service.LoginLogService;
import com.seven.gwc.modular.system.service.UserService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.seven.gwc.core.util.HttpContextUtil.getIp;

/**
 * 登录控制器
 *
 * @author : GD
 * @date : 2019-11-28
 */
@Controller
public class LoginController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private LoginLogService loginLogService;

    /**
     * 跳转到主页 头一级菜单+左侧二级和以下菜单
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, String pcode) {
        ShiroUser user = ShiroKit.getUserNotNull();
        List<Long> roleList = user.getRoleList();

        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            //登录失败，记录登录日志
            loginLogService.insert(LogTypeEnum.LOGIN_FAIL, user.getId(), "帐号：" + user.getAccount() + "->用户没有角色，无法登陆", getIp(), SysUtil.getmacAddress());
            return "/login";
        }
        //登录成功，记录登录日志
        loginLogService.insert(LogTypeEnum.LOGIN, user.getId(), "帐号：" + user.getAccount() + "->登录成功", getIp(), SysUtil.getmacAddress());

        List<MenuNode> menus = userService.getUserMenuNodes(roleList);
        model.addAttribute("menus", menus);
        model.addAttribute("userInfo", user);
        return "index";
    }

    @RequestMapping(value = "loadMenus", method = RequestMethod.GET)
    public String loadMenus(Model model, String pcode) {
        ShiroUser user = ShiroKit.getUserNotNull();
        List<Long> roleList = user.getRoleList();
        List<MenuNode> menus = userService.getUserMenuNodes(roleList, pcode);
        model.addAttribute("menus", menus);
        return "common/sidebar";
    }


    public List<MenuNode> returnChildren(List<MenuNode> children) {
        if (!children.isEmpty()) {
            children.get(0).setUrl("");
        }
        return children;
    }


    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return "redirect:/";
        } else {
            return "/login";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali(HttpServletRequest request) {

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
//        String remember = request.getParameter("remember");

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        //如果开启了记住我功能
//        if (SysConsts.STR_ON.equals(remember)) {
//            token.setRememberMe(true);
//        } else {
        token.setRememberMe(false);
//        }

        //执行shiro登录操作
        currentUser.login(token);

        return "redirect:/";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        //登录成功，记录登录日志
        ShiroUser user = ShiroKit.getUserNotNull();

        loginLogService.insert(LogTypeEnum.EXIT, user.getId(), "帐号：" + user.getAccount() + "->退出登录", getIp(), SysUtil.getmacAddress());
        ShiroKit.getSubject().logout();

        Cookie[] cookies = request.getCookies();
        Cookie[] var2 = cookies;
        int var3 = cookies.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Cookie cookie = var2[var4];
            Cookie temp = new Cookie(cookie.getName(), "");
            temp.setMaxAge(0);
            response.addCookie(temp);
        }
        return "redirect:/login";
    }
}