package com.seven.gwc.config.generator.action.config;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import com.seven.gwc.config.generator.action.model.GenQo;
import com.seven.gwc.config.generator.engine.base.SimpleTemplateEngineAbstract;
import com.seven.gwc.config.generator.engine.base.AbstractTonFunTemplateEngine;
import com.seven.gwc.config.generator.engine.config.SqlConfig;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.catalina.startup.ExpandWar.deleteDir;

/**
 * 代码生成的抽象配置
 *
 * @author : GD
 */
public abstract class AbstractGeneratorConfig {

    /**
     * mybatis-plus代码生成器配置
     */
    GlobalConfig globalConfig = new GlobalConfig();
    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    StrategyConfig strategyConfig = new StrategyConfig();
    PackageConfig packageConfig = new PackageConfig();
    TemplateConfig templateConfig = new TemplateConfig();
    protected InjectionConfig injectionConfig;
    TableInfo tableInfo = null;

    /**
     * TonFun代码生成器配置
     */
    GenQo genQo = new GenQo();
    SqlConfig sqlConfig = new SqlConfig();

    /**
     * 公用配置
     */
    protected abstract void config();

    public void init() {
        config();
        //controller不需要生成,生成之后会自动删掉
        if (!genQo.getEntitySwitch()) {
            packageConfig.setEntity("TTT");
        }
        if (!genQo.getControllerSwitch()) {
            packageConfig.setController("TTT");
        }
        if (!genQo.getServiceSwitch()) {
            packageConfig.setService("TTT");
        }
        if (!genQo.getServiceImplSwitch()) {
            packageConfig.setServiceImpl("TTT");
        }
        if (!genQo.getMapperSwitch()) {
            packageConfig.setMapper("TTT");
        }
        if (!genQo.getXmlSwitch()) {
            packageConfig.setXml("TTT");
        }
    }

    /**
     * 删除不必要的代码
     */
    public void destory() {
        deleteDir(new File(globalConfig.getOutputDir() + "/TTT"));
    }

    public AbstractGeneratorConfig() {
    }

    public void doMpGeneration() {
        init();
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.setTemplateEngine(new BeetlTemplateEngine());
        autoGenerator.setCfg(new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("genQo", genQo);
                this.setMap(map);
            }
        });

        autoGenerator.execute();
        destory();

        //获取table信息,用于TonFun代码生成
        List<TableInfo> tableInfoList = autoGenerator.getConfig().getTableInfoList();
        if (tableInfoList != null && tableInfoList.size() > 0) {
            this.tableInfo = tableInfoList.get(0);
        }
        injectionConfig = autoGenerator.getCfg();
    }

    public void doTonFunGeneration() {
        AbstractTonFunTemplateEngine abstractTonFunTemplateEngine = new SimpleTemplateEngineAbstract();
        abstractTonFunTemplateEngine.setGenQo(genQo);
        sqlConfig.setConnection(dataSourceConfig.getConn());
        abstractTonFunTemplateEngine.setSqlConfig(sqlConfig);
        abstractTonFunTemplateEngine.setTableInfo(tableInfo);
        abstractTonFunTemplateEngine.setInjectionConfig(injectionConfig);
        abstractTonFunTemplateEngine.start();
    }
}
