package com.seven.gwc.modular.sync.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.core.util.SpringContextUtil;
import com.seven.gwc.modular.sync.config.MasterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author : zzl
 * @Date: 2020-03-15
 * @description :
 */
public class SyncService {

    @Autowired
    private MasterConfig masterConfig;

    public void readData(){
        ApplicationContext context = SpringContextUtil.getApplicationContext();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            if(beanDefinitionName.endsWith("Mapper")){
                Object bean = context.getBean(beanDefinitionName);
                if(bean instanceof BaseMapper){
                    BaseMapper mapper=(BaseMapper) bean;
                    try {
                        QueryWrapper wrapper = new QueryWrapper();
                        wrapper.eq("syn_flag",false);
                        List list = mapper.selectList(wrapper);
                        for (Object o : list) {
                            System.out.println(o);
                        }

                        System.out.println(">>>"+list.size());
                    }catch (Exception e){
                        System.out.println(beanDefinitionName+":error");
                    }



                }
            }
        }
    }
}
