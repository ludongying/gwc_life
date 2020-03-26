package com.seven.gwc.modular.lawrecord.service;

import java.util.List;

/**
 * description : 文书录服务类
 *
 * @author : ZZL
 * @date : 2020-03-24
 */
public interface InstrumentService {

    /**
     * 生成文书-所有
     * @param id
     */
    void generateSystemInstrument(String id);


    /**
     * 根据修改内容修改部分文书
     * @param id
     * @param clazz
     */
    void generateInstrument(String id,Class<?> clazz);


    /**
     * 获取文书列表
     * @param id
     * @return
     */
    List getInstrument(String id);





}
