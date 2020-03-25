package com.seven.gwc.modular.lawrecord.service.impl;

import com.seven.gwc.modular.lawrecord.dao.LawRecordMapper;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.lawrecord.data.instrument.InstrumentModelData;
import com.seven.gwc.modular.lawrecord.dto.LawTypeDTO;
import com.seven.gwc.modular.lawrecord.enums.InstrumentEnum;
import com.seven.gwc.modular.lawrecord.service.AgencyService;
import com.seven.gwc.modular.lawrecord.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class InstrumentServiceImpl implements InstrumentService {

    @Autowired
    private LawRecordMapper lawRecordMapper;

    @Autowired
    private AgencyService agencyService;

    @Value("${FILE_UPLOAD_PATH_FILE}")
    private String path;

    private String modelPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"\\static\\lawrecord\\instrument\\";


    /**
     * 获取所有参数
     * @param id
     * @return
     */
    public Map<String, Object> getParam(String id) {
        Map<String,Object> res=new HashMap<>();
        Map<String, Object> agency = agencyService.getParams(id);
        if(Objects.nonNull(agency)){
            res.putAll(agency);
        }
        return res;
    }

    /**
     * 生成系统文书
     */
    @Override
    public void generateSystemInstrument(String id){
        LawTypeDTO lawType = lawRecordMapper.findLawType(id);
        Boolean writFlag = lawType.getWritFlag();
        if(Objects.isNull(writFlag) || !writFlag){
            Map<String, Object> param = getParam(id);
            List<InstrumentEnum> instruments = InstrumentEnum.getSystem(lawType);
            for (InstrumentEnum instrument : instruments) {
                System.out.println(modelPath+instrument.getMessage());
                System.out.println(new File(modelPath+instrument.getMessage()).isFile());
                new InstrumentModelData(modelPath+instrument.getMessage(),param)
                        .export(path+ FileUtils.file_sep+lawType.getLawCaseCode()
                                +FileUtils.file_sep+instrument.getMessage());
            }
        }

    }

}
