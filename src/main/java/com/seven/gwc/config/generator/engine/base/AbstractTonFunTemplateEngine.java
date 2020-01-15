package com.seven.gwc.config.generator.engine.base;

import com.seven.gwc.core.util.ToolUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ADI项目模板生成 引擎
 */
public abstract class AbstractTonFunTemplateEngine extends AbstractTemplateEngine {
    private static final Logger log = LoggerFactory.getLogger(AbstractTonFunTemplateEngine.class);

    private GroupTemplate groupTemplate;

    public AbstractTonFunTemplateEngine() {
        initBeetlEngine();
    }

    /**
     * 自定义标签
     */
    protected void initBeetlEngine() {
        Properties properties = new Properties();
        properties.put("RESOURCE.root", "");
        properties.put("DELIMITER_STATEMENT_START", "<%");
        properties.put("DELIMITER_STATEMENT_END", "%>");
        properties.put("HTML_TAG_FLAG", "##");
        Configuration cfg = null;
        try {
            cfg = new Configuration(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
        groupTemplate = new GroupTemplate(resourceLoader, cfg);
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
    }

    /**
     * 获取不同模版配置
     */
    protected void configTemplate(Template template) {
        template.binding("genQo", super.genQo);
        template.binding("global", super.injectionConfig);
        template.binding("controller", super.controllerConfig);
        template.binding("service", super.serviceConfig);
        template.binding("dao", super.mapperConfig);
        template.binding("sqls", super.sqlConfig);
        template.binding("table", super.tableInfo);
    }

    /**
     * 根据模版进行生成
     */
    protected void generateFile(String template, String filePath) {
        Template pageTemplate = groupTemplate.getTemplate(template);
        configTemplate(pageTemplate);
        filePath = filePath.replaceAll("/+|\\\\+", "\\\\");
        File file = new File(filePath);
        File parentFile = file.getParentFile();

        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            pageTemplate.renderTo(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void start() {
        log.debug("==========================TonFun代码生成器准备生成...==========================");
        //配置之间的相互依赖
        super.initConfig();
        if (super.genQo.getIndexPageSwitch()) {
            generatePageHtml();
        }
        if (super.genQo.getIndexJsSwitch()) {
            generatePageJs();
        }
        if (super.genQo.getAddPageSwitch()) {
            generatePageAddHtml();
        }
        if (super.genQo.getAddJsSwitch()) {
            generatePageAddJs();
        }
        if (super.genQo.getEditPageSwitch()) {
            generatePageEditHtml();
        }
        if (super.genQo.getEditJsSwitch()) {
            generatePageEditJs();
        }
        if (super.genQo.getDetailPageSwitch()) {
            generatePageDetailHtml();
        }
        if (super.genQo.getDetailJsSwitch()) {
            generatePageDetailJs();
        }
        if (super.genQo.getSqlSwitch()) {
            generateSqls();
        }

        log.debug("==========================TonFun代码生成器生成完成...==========================");
    }

    /**
     * TonFun 生成
     */
    protected abstract void generatePageHtml();

    protected abstract void generatePageJs();

    protected abstract void generatePageAddHtml();

    protected abstract void generatePageAddJs();

    protected abstract void generatePageEditHtml();

    protected abstract void generatePageEditJs();

    protected abstract void generatePageDetailHtml();

    protected abstract void generatePageDetailJs();

    protected abstract void generateSqls();

    /** myBatis-plus 生成 */
//    protected abstract void generateEntity();
//    protected abstract void generateController();
//    protected abstract void generateIService();
//    protected abstract void generateService();
//    protected abstract void generateMapper();
//    protected abstract void generateXml();

}
