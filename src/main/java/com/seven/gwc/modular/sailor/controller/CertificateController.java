package com.seven.gwc.modular.sailor.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.seven.gwc.modular.sailor.service.CertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;

/**
 * description : 证书信息控制器
 *
 * @author : LDY
 * @date : 2020-02-28
 */
@Controller
@RequestMapping("certificate")
public class CertificateController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/sailor/certificate/";

    @Autowired
    private CertificateService certificateService;

    /**
     * 跳转到证书信息首页
     */
    @RequestMapping("")
//    public String index(@RequestParam("ids") String ids,@RequestParam("personId") String personId, Model model) {
    public String index(@RequestParam("personId") String personId, Model model) {
//        model.addAttribute("ids",ids);
        model.addAttribute("personId",personId);
//        ShiroUser user = ShiroKit.getUser();
//        model.addAttribute("userId",user.getId());
//        model.addAttribute("roleNames", user.getRoleNames());
        return PREFIX + "certificate";
    }

    /**
     * 跳转到添加证书信息
     */
    @RequestMapping("/certificate_add")
    public String certificateAdd(@RequestParam("personId") String personId, Model model) {
        model.addAttribute("personId",personId);
        return PREFIX + "certificate_add";
    }

    /**
     * 跳转到修改证书信息
     */
    @RequestMapping("/certificate_edit")
    public String certificateUpdate(String certificateId) {
        return PREFIX + "certificate_edit";
    }

    /**
     * 跳转到查看证书信息
     */
    @RequestMapping("/certificate_detail")
    public String certificateDetail(String certificateId) {
        return PREFIX + "certificate_detail";
    }

    /**
     * 跳转到查看证书列表页面
     */
    @RequestMapping("certificate_list")
     public String certificateList(String id,String htmltype){return PREFIX + "certificate";}

    /**
     * 获取证书信息列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<CertificateEntity> list(CertificateEntity certificate, String ids, String personId) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<CertificateEntity> certificates = certificateService.selectCertificate(certificate,personId,(int)(page.getCurrent() - 1) * (int)page.getSize(), (int)page.getSize());
        PageInfo pageInfo = new PageInfo<>(certificates);
        Integer size = certificateService.getListSize(certificate,personId);
        pageInfo.setPages((int)Math.ceil((float)size / (float) page.getSize()));
        pageInfo.setTotal(size);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加证书信息
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(CertificateEntity certificate, String personId) throws ParseException {
        ShiroUser user = ShiroKit.getUser();
        if(!certificateService.addCertificate(certificate, user, personId)){
            return new BaseResult().failure((ErrorEnum.ERROR_ONLY_CERTIFICATE_ID));
        }
        return SUCCESS;
    }

    /**
     * 删除证书信息
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String certificateId, String personId) {
        ShiroUser user = ShiroKit.getUser();
        certificateService.deleteCertificate(certificateId, user, personId);
        return SUCCESS;
    }

    /**
     * 编辑证书信息
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(CertificateEntity certificate) throws ParseException {
        ShiroUser user = ShiroKit.getUser();
        if(!certificateService.editCertificate(certificate, user)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_CERTIFICATE_ID);
        }
        return SUCCESS;
    }

    /**
     * 证书信息详情
     */
    @RequestMapping("/detail/{certificateId}")
    @ResponseBody
    public CertificateEntity detail(@PathVariable String certificateId) {
        return certificateService.getById(certificateId);
    }

//    /**
//     * 通过船员id获取其执法证编号
//     * @param personId 船员id
//     * @return
//     */
//    @RequestMapping("/getLawCode/{personId}")
//    @ResponseBody
//    public String  getLawCode(@PathVariable String personId){
//        return certificateService.getLawCode(personId);
//    }

}

