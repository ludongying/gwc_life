package com.seven.gwc.modular.lawrecord.service.impl;

import com.seven.gwc.modular.lawrecord.data.word.config.InstrumentContrast;
import com.seven.gwc.modular.lawrecord.service.InstrumentService;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class InstrumentServiceImpl implements InstrumentService {

    @Autowired
    private LawRecordService lawRecordService;

    @Autowired
    private InstrumentContrast instrumentContrast;

    @Override
    public Map<String, String> getData(String recordId) {
        Map<String, String> map = instrumentContrast.getMap();

        return null;
    }
}
