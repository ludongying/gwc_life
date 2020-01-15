package com.seven.gwc.config.generator.action.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.seven.gwc.config.generator.action.model.GenQo;
import com.seven.gwc.core.util.ToolUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认的代码生成的配置
 */
public class WebGeneratorConfig extends AbstractGeneratorConfig {


    public WebGeneratorConfig(GenQo genQo) {
        this.genQo = genQo;
    }

    @Override
    protected void config() {
        /**
         * 全局配置
         */
        globalConfig.setOutputDir(genQo.getProjectPath() + File.separator + "src" + File.separator + "main" + File.separator + "java");//生成文件的输出目录
        globalConfig.setFileOverride(true);                 //是否覆盖已有文件
        globalConfig.setBaseResultMap(true);                //开启 BaseResultMap
        globalConfig.setBaseColumnList(true);               //开启 baseColumnList
        globalConfig.setOpen(false);                        //是否打开输出目录
        globalConfig.setEnableCache(false);                 //是否在xml中添加二级缓存配置
        globalConfig.setAuthor(genQo.getAuthor());          //开发人员
        globalConfig.setEntityName("%sEntity");             //实体命名方式
        globalConfig.setControllerName("%sController");     //controller 命名方式
        globalConfig.setServiceName("%sService");           //service 命名方式
        globalConfig.setServiceImplName("%sServiceImpl");   //serviceImpl 命名方式
        globalConfig.setMapperName("%sMapper");             //mapper命名方式
        globalConfig.setXmlName("%sMapper");                //mapper xml 命名方式
        globalConfig.setDateType(DateType.ONLY_DATE);       //时间类型转换
//        globalConfig.setSwagger2(false);                  //开启swagger
//        globalConfig.setIdType(IdType.AUTO);              //Id生成策略

        /**
         * 数据库连接
         */
        dataSourceConfig.setDbType(DbType.MYSQL);           //数据库类型
        dataSourceConfig.setDriverName(genQo.getDriver());  //驱动名称
        dataSourceConfig.setUsername(genQo.getUserName());  //数据库连接用户名
        dataSourceConfig.setPassword(genQo.getPassword());  //数据库连接密码
        dataSourceConfig.setUrl(genQo.getUrl());            //驱动连接的URL


        /**
         * 数据库表配置
         */
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));

        strategyConfig.setNaming(NamingStrategy.underline_to_camel);                            // 表名生成策略
        strategyConfig.setEntityLombokModel(true);                                              // lombok模型
        strategyConfig.setRestControllerStyle(false);                                           // 开启Restful
        if (genQo.getIgnoreTabelPrefix() != null) {
            strategyConfig.setTablePrefix(new String[]{genQo.getIgnoreTabelPrefix()});          // 此处可以修改为您的表前缀
        }
        strategyConfig.setInclude(new String[]{genQo.getTableName()});                          // 需要包含的表名
        strategyConfig.setTableFillList(tableFillList);                                         // 表填充字段
        strategyConfig.setSuperControllerClass("com.seven.gwc.core.base.BaseController");  // 自定义 controller 父类


        /**
         * 包配置
         */
        packageConfig.setParent(null);
        packageConfig.setEntity(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".entity");
        packageConfig.setController(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".controller");
        packageConfig.setService(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".service");
        packageConfig.setServiceImpl(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".service.impl");
        packageConfig.setMapper(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".dao");
        packageConfig.setXml(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".dao.mapper");


        /**
         * 业务代码配置
         */
        String entiyName = null;
        if (ToolUtil.isEmpty(genQo.getIgnoreTabelPrefix())) {
            entiyName = ToolUtil.toCamelCase(genQo.getTableName());
        } else {
            entiyName = ToolUtil.toCamelCase(ToolUtil.removePrefix(genQo.getTableName(), genQo.getIgnoreTabelPrefix()));
        }
        genQo.setEntityName(ToolUtil.firstCharToUpperCase(entiyName));
        genQo.setBizEnName(ToolUtil.firstCharToLowerCase(entiyName));
        genQo.setBizEnBigName(ToolUtil.firstCharToUpperCase(entiyName));


        /**
         * 模版
         */
        templateConfig.setEntity("/templates/entity.java");
        templateConfig.setController("/templates/controller.java");
        templateConfig.setService("/templates/service.java");
        templateConfig.setServiceImpl("/templates/serviceImpl.java");
        templateConfig.setMapper("/templates/mapper.java");
        templateConfig.setXml("/templates/mapper.xml");


    }
}
