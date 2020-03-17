package com.seven.gwc.modular.lawrecord.service;


import java.util.Map;

public interface InstrumentService {

    /**
     * 获取执法记录数据
     * @param recordId
     * @return
     */
    Map<String,String> getData(String recordId);

}
