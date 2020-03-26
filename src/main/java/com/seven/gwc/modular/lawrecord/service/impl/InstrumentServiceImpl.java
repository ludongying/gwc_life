package com.seven.gwc.modular.lawrecord.service.impl;

import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.lawrecord.data.instrument.InstrumentModelData;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.ReasonDO;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.RecordDO;
import com.seven.gwc.modular.lawrecord.dto.LawTypeDTO;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.enums.InstrumentEnum;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 文书实现类
 * @author zl
 */
@Service
@Slf4j
public class InstrumentServiceImpl implements InstrumentService {

    @Autowired
    private LawRecordService lawRecordService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private InquireService inquireService;

    @Autowired
    private InquireSafeService inquireSafeService;

    @Autowired
    private InquisitionService inquisitionService;

    @Autowired
    private DecisionSafeService decisionSafeService;

    @Autowired
    private DecisionService decisionService;

    @Value("${FILE_UPLOAD_PATH_FILE}")
    private String path;

    private String modelPath=FileUtils.getStaticPath()+"\\lawrecord\\instrument\\";

    /**
     * 获取所有参数
     * @param id
     * @return
     */
     Map<String, String> getParam(String id) {
        Map<String,String> res=new HashMap<>(32);
        LawRecordEntity lawRecordEntity = lawRecordService.getById(id);
        Integer lawType = lawRecordEntity.getLawType();
        if(LawTypeEnum.PRODUCE.getCode().equals(lawType)){
            //询问笔录

            //勘验笔录
            merge(res,inquisitionService.getParams(id));
            //决定
            merge(res,decisionService.getParams(id));
        }else{
            //笔录
            merge(res,inquireSafeService.getParams(id));
            //决定
            merge(res,decisionSafeService.getParams(id));
        }
        //办案机关
        merge(res,agencyService.getParams(id));
        //案由
        merge(res,new ReasonDO(lawRecordEntity).toMap());
        //固定内容
        merge(res,new RecordDO().toMap());
        return res;
    }

    private void merge(Map<String,String> res,Map<String,String> map){
        if(Objects.nonNull(map)){
            res.putAll(map);
        }
    }

    private String getGeneratePath(LawTypeDTO lawType){
        return path+ FileUtils.file_sep+"lawrecord"+FileUtils.file_sep+lawType.getYear() +FileUtils.file_sep+lawType.getLawCaseCode() +FileUtils.file_sep;
    }

    private String saveFiles(LawTypeDTO lawType,Map<String,String> param,List<InstrumentEnum> list){
        List<String> res=new ArrayList<>();
        for (InstrumentEnum instrument : list) {
            String exportPath=getGeneratePath(lawType)+instrument.getMessage();
            new InstrumentModelData(modelPath+instrument.getMessage(),param).export(exportPath);
            res.add(exportPath);
        }
        return res.stream().collect(Collectors.joining(","));
    }

    /**
     * 生成系统文书-全部
     */
    @Override
    public void generateSystemInstrument(String id){
        if(true){return;}
        LawRecordEntity lawRecordEntity = lawRecordService.getById(id);
        LawTypeDTO lawType = lawRecordService.findLawType(id);
        Boolean writFlag = lawType.getWritFlag();
        if(Objects.isNull(writFlag) || !writFlag){
            Map<String, String> param = getParam(id);
            List<InstrumentEnum> autos = InstrumentEnum.getAuto(lawType);
            String autoPath = saveFiles(lawType, param, autos);
            lawRecordEntity.setAutoWritFilePath(autoPath);
            List<InstrumentEnum> manually = InstrumentEnum.getAutoManually(lawType);
            String  manuallyPath= saveFiles(lawType, param, manually);
            lawRecordEntity.setManualWritFilePath(manuallyPath);
            lawRecordEntity.setWritFlag(true);
            lawRecordService.updateById(lawRecordEntity);
        }
    }

    /**
     * 根据修改类型更新文书
     * @param id
     * @param clazz
     */
    @Override
    public void generateInstrument(String id,Class<?> clazz){
        if(true){return;}
        LawRecordEntity lawRecordEntity = lawRecordService.getById(id);
        LawTypeDTO lawType = lawRecordService.findLawType(id);
        Boolean writFlag = lawType.getWritFlag();
        if(Objects.isNull(writFlag) || !writFlag){
            String autoWritFilePath = lawRecordEntity.getAutoWritFilePath();
            String manualWritFilePath = lawRecordEntity.getManualWritFilePath();
            Map<String, String> param = getParam(id);
            List<InstrumentEnum> as = InstrumentEnum.getAutoByClass(lawType,clazz);
            String aPath = saveFiles(lawType, param, as);
            if(!autoWritFilePath.contains(aPath)){
                log.info(">>存储文件硬盘路径有变更,重新生成");
                List<InstrumentEnum> autos = InstrumentEnum.getAuto(lawType);
                String autoPath = saveFiles(lawType, param, autos);
                lawRecordEntity.setAutoWritFilePath(autoPath);
                lawRecordService.updateById(lawRecordEntity);
            }
            List<InstrumentEnum> ms = InstrumentEnum.getAutoManuallyByClass(lawType,clazz);
            String mPath = saveFiles(lawType, param, ms);
            if(!manualWritFilePath.contains(mPath)){
                log.info(">>存储文件硬盘路径有变更，重新生成");
                List<InstrumentEnum> manually = InstrumentEnum.getAutoManually(lawType);
                String  manuallyPath= saveFiles(lawType, param, manually);
                lawRecordEntity.setManualWritFilePath(manuallyPath);
                lawRecordService.updateById(lawRecordEntity);
            }
        }
    }

    public List getInstrument(){
      return null;
    }

}
