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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description 文书实现类
 * @author zl
 */
@Service
public class InstrumentServiceImpl implements InstrumentService {

    @Autowired
    private LawRecordMapper lawRecordMapper;

    @Autowired
    private AgencyService agencyService;

    @Value("${FILE_UPLOAD_PATH_FILE}")
    private String path;

    private String modelPath=FileUtils.getStaticPath()+"\\lawrecord\\instrument\\";

    /**
     * 获取所有参数
     * @param id
     * @return
     */
    public Map<String, String> getParam(String id) {
        Map<String,String> res=new HashMap<>();
        Map<String,String> agency = agencyService.getParams(id);
        if(Objects.nonNull(agency)){
            res.putAll(agency);
        }
        return res;
    }

    public String getGeneratePath(){
        return path+ FileUtils.file_sep+"lawrecord"+FileUtils.file_sep;
    }

    /**
     * 生成系统文书
     */
    @Override
    public void generateSystemInstrument(String id){
        LawTypeDTO lawType = lawRecordMapper.findLawType(id);
        Boolean writFlag = lawType.getWritFlag();
        if(Objects.isNull(writFlag) || !writFlag){
            Map<String, String> param = getParam(id);
            List<InstrumentEnum> instruments = InstrumentEnum.getSystem(lawType);
            for (InstrumentEnum instrument : instruments) {
                new InstrumentModelData(modelPath+instrument.getMessage(),param)
                        .export(getGeneratePath()+lawType.getYear()
                                +FileUtils.file_sep+lawType.getLawCaseCode()
                                +FileUtils.file_sep+instrument.getMessage());
            }
        }

    }

}
