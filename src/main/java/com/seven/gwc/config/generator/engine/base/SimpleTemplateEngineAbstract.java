package com.seven.gwc.config.generator.engine.base;


import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用的模板生成引擎
 */
public class SimpleTemplateEngineAbstract extends AbstractTonFunTemplateEngine {
    private static final Logger log = LoggerFactory.getLogger(SimpleTemplateEngineAbstract.class);

    @Override
    protected void generatePageHtml() {
        String path = ToolUtil.format(super.getGenQo().getProjectPath() + getPageConfig().getPagePathTemplate(),
                super.getGenQo().getBizEnName(), super.getGenQo().getBizEnName());
        generateFile("templates/page.html.btl", path);
        log.debug("结果:生成列表页面成功;  文件:"+path);
    }

    @Override
    protected void generatePageJs() {
        String path = ToolUtil.format(super.getGenQo().getProjectPath() + getPageConfig().getPageJsPathTemplate(),
                super.getGenQo().getBizEnName(), super.getGenQo().getBizEnName());
        generateFile("templates/page.js.btl", path);
        log.debug("结果:生成列表页面js成功;  文件:"+path);
    }

    @Override
    protected void generatePageAddHtml() {
        String path = ToolUtil.format(super.getGenQo().getProjectPath() + getPageConfig().getPageAddPathTemplate(),
                super.getGenQo().getBizEnName(), super.getGenQo().getBizEnName());
        generateFile("templates/page_add.html.btl", path);
        log.debug("结果:生成添加页面成功;  文件:"+path);
    }

    @Override
    protected void generatePageAddJs() {
        String path = ToolUtil.format(super.getGenQo().getProjectPath() + getPageConfig().getPageAddJsPathTemplate(),
                super.getGenQo().getBizEnName(), super.getGenQo().getBizEnName());
        generateFile("templates/page_add.js.btl", path);
        log.debug("结果:生成添加页面js成功;  文件:"+path);
    }

    @Override
    protected void generatePageEditHtml() {
        String path = ToolUtil.format(super.getGenQo().getProjectPath() + getPageConfig().getPageEditPathTemplate(),
                super.getGenQo().getBizEnName(), super.getGenQo().getBizEnName());
        generateFile("templates/page_edit.html.btl", path);
        log.debug("结果:生成编辑页面成功;  文件:"+path);
    }

    @Override
    protected void generatePageEditJs() {
        String path = ToolUtil.format(super.getGenQo().getProjectPath() + getPageConfig().getPageEditJsPathTemplate(),
                super.getGenQo().getBizEnName(), super.getGenQo().getBizEnName());
        generateFile("templates/page_edit.js.btl", path);
        log.debug("结果:生成编辑页面js成功;  文件:"+path);
    }

    @Override
    protected void generatePageDetailHtml() {
        String path = ToolUtil.format(super.getGenQo().getProjectPath() + getPageConfig().getPageDetailPathTemplate(),
                super.getGenQo().getBizEnName(), super.getGenQo().getBizEnName());
        generateFile("templates/page_detail.html.btl", path);
        log.debug("结果:生成详情页面成功;  文件:"+path);
    }

    @Override
    protected void generatePageDetailJs() {
        String path = ToolUtil.format(super.getGenQo().getProjectPath() + getPageConfig().getPageDetailJsPathTemplate(),
                super.getGenQo().getBizEnName(), super.getGenQo().getBizEnName());
        generateFile("templates/page_detail.js.btl", path);
        log.debug("结果:生成详情页面js成功;  文件:"+path);
    }

    @Override
    protected void generateSqls() {
        log.debug("结果:生成菜单sql成功;");
    }


    /** myBatis-plus 生成 */
//    @Override
//    protected void generateEntity() {
//        String path = ToolUtil.format(super.getGenQo().getProjectPath() + super.getEntityConfig().getEntityPathTemplate(),
//                ToolUtil.firstCharToUpperCase(super.getGenQo().getBizEnName()));
//        generateFile("templates/entity.java.btl", path);
//        log.debug("结果:生成Entity成功;  文件:"+path);
//    }
//
//    @Override
//    protected void generateController() {
//        String path = ToolUtil.format(super.getGenQo().getProjectPath() + super.getControllerConfig().getControllerPathTemplate(),
//                ToolUtil.firstCharToUpperCase(super.getGenQo().getBizEnName()));
//        generateFile("templates/controller.java.btl", path);
//        log.debug("结果:生成Controller成功;  文件:"+path);
//    }
//
//    @Override
//    protected void generateIService() {
//        String path = ToolUtil.format(super.getGenQo().getProjectPath() + super.getServiceConfig().getServicePathTemplate(),
//                ToolUtil.firstCharToUpperCase(super.getGenQo().getBizEnName()));
//        generateFile("Template/service.java.btl", path);
//        log.debug("结果:生成Service类成功;  文件:"+path);
//    }
//
//    @Override
//    protected void generateService() {
//        String path = ToolUtil.format(super.getGenQo().getProjectPath() + super.getServiceConfig().getServiceImplPathTemplate(),
//                ToolUtil.firstCharToUpperCase(super.getGenQo().getBizEnName()));
//        generateFile("templates/serviceImpl.java.btl", path);
//        log.debug("结果:生成Service实现类成功;  文件:"+path);
//    }
//
//    @Override
//    protected void generateMapper() {
//        String path = ToolUtil.format(super.getGenQo().getProjectPath() + super.getMapperConfig().getDaoPathTemplate(),
//                ToolUtil.firstCharToUpperCase(super.getGenQo().getBizEnName()));
//        generateFile("templates/mapper.java.btl", path);
//        log.debug("结果:生成Mapper接口成功;  文件:"+path);
//    }
//
//    @Override
//    protected void generateXml() {
//        String path = ToolUtil.format(super.getGenQo().getProjectPath() + super.getMapperConfig().getXmlPathTemplate(),
//                ToolUtil.firstCharToUpperCase(super.getGenQo().getBizEnName()));
//        generateFile("templates/mapper.xml.btl", path);
//        log.debug("结果:生成XML成功;  文件:"+path);
//    }

}
