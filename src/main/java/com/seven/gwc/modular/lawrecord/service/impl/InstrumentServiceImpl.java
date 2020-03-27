package com.seven.gwc.modular.lawrecord.service.impl;

import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.lawrecord.data.instrument.InstrumentModelData;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.FilePathDO;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.ReasonDO;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.RecordDO;
import com.seven.gwc.modular.lawrecord.data.instrument.dto.FilePathDTO;
import com.seven.gwc.modular.lawrecord.dto.LawTypeDTO;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.enums.InstrumentEnum;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    private String getGeneratePath(LawTypeDTO lawType){
        return path+ FileUtils.file_sep+"lawrecord"+FileUtils.file_sep+lawType.getYear()
                +FileUtils.file_sep+lawType.getLawCaseCode() +FileUtils.file_sep;
    }

    /**
     * 获取所有参数
     * @param id
     * @return
     */
    private Map<String, String> getParam(String id) {
        Map<String,String> res=new HashMap<>(32);
        LawRecordEntity lawRecordEntity = lawRecordService.getById(id);
        Integer lawType = lawRecordEntity.getLawType();
        if(LawTypeEnum.PRODUCE.getCode().equals(lawType)){
            //询问笔录
            merge(res,inquireService.getParams(id));
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


    /**
     * 获取补录内容
     * @param id
     * @param inquireEntity
     * @return
     */
    private Map<String, String> getSupplementParam(String id,InquireEntity inquireEntity){

       
        return null;
    }

    /**
     * 生成询问笔录补录内容
     * @param id
     */
    public List<FilePathDO> generateInquireSupplement(String id,List<FilePathDO> files){
        List<InquireEntity> supplement = inquireService.getSupplement(id);
        if(Objects.nonNull(supplement) && !supplement.isEmpty()){
            String fileName = InstrumentEnum.INSTRUMENT_INQUIRE.getMessage();
            LawTypeDTO lawType = lawRecordService.findLawType(id);
            for (InquireEntity inquireEntity : supplement) {
                //获取参数
                Map<String, String> supplementParam = getSupplementParam(id, inquireEntity);
                String exportPath=getGeneratePath(lawType)+fileName + inquireEntity.getInvestigatePosition();
                new InstrumentModelData(InstrumentEnum.getPath()+fileName,supplementParam).export(exportPath);
                files.add(new FilePathDO(InstrumentEnum.INSTRUMENT_INQUIRE.getCode(),exportPath));
            }

        }
        return files;
    }

    private void merge(Map<String,String> res,Map<String,String> map){
        if(Objects.nonNull(map)){
            res.putAll(map);
        }
    }

    private List<FilePathDO> saveFile(LawTypeDTO lawType,Map<String,String> param,List<InstrumentEnum> list){
        List<FilePathDO> res=new ArrayList<>();
        if(Objects.nonNull(list) && !list.isEmpty()){
            for (InstrumentEnum instrument : list) {
                String exportPath=getGeneratePath(lawType)+instrument.getMessage();
                new InstrumentModelData(InstrumentEnum.getPath()+instrument.getMessage(),param).export(exportPath);
                res.add(new FilePathDO(instrument.getCode(),exportPath));
            }
        }
        return res;
    }



    private String saveFiles(LawTypeDTO lawType,Map<String,String> param,List<InstrumentEnum> list){
        List<FilePathDO> filePathDOS = saveFile(lawType, param, list);
        return FilePathDO.getJson(filePathDOS);
    }

    /**
     * 生成系统文书-全部
     */
    @Override
    public void generateSystemInstrument(String id){
        LawRecordEntity lawRecordEntity = lawRecordService.getById(id);
        LawTypeDTO lawType = lawRecordService.findLawType(id);
        Boolean writFlag = lawType.getWritFlag();
        if(Objects.isNull(writFlag) || !writFlag){
            Map<String, String> param = getParam(id);
            List<InstrumentEnum> autos = InstrumentEnum.getAuto(lawType);
            String autoPath = saveFiles(lawType, param, autos);
            lawRecordEntity.setAutoWritFilePath(autoPath);
            List<InstrumentEnum> manually = InstrumentEnum.getAutoManually(lawType);
            List<FilePathDO> filePathDOS = saveFile(lawType, param, manually);
            filePathDOS = generateInquireSupplement(id, filePathDOS);
            lawRecordEntity.setManualWritFilePath(FilePathDO.getJson(filePathDOS));
//            lawRecordEntity.setWritFlag(true);
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
            if(!existFile(autoWritFilePath,aPath)){
                log.info(">>存储文件硬盘路径有变更,重新生成");
                List<InstrumentEnum> autos = InstrumentEnum.getAuto(lawType);
                String autoPath = saveFiles(lawType, param, autos);
                lawRecordEntity.setAutoWritFilePath(autoPath);
                lawRecordService.updateById(lawRecordEntity);
            }
            List<InstrumentEnum> ms = InstrumentEnum.getAutoManuallyByClass(lawType,clazz);
            String mPath = saveFiles(lawType, param, ms);
            if(!existFile(manualWritFilePath,mPath)){
                log.info(">>存储文件硬盘路径有变更，重新生成");
                List<InstrumentEnum> manually = InstrumentEnum.getAutoManually(lawType);
                String  manuallyPath= saveFiles(lawType, param, manually);
                lawRecordEntity.setManualWritFilePath(manuallyPath);
                lawRecordService.updateById(lawRecordEntity);
            }
        }
    }

    @Override
    public void generateInstrument(String id) {

    }

    private boolean existFile(String original,String file){
        List<FilePathDO> originals = FilePathDO.getFiles(original);
        List<FilePathDO> files = FilePathDO.getFiles(file);
        if(Objects.nonNull(originals) && Objects.nonNull(files)){
            List<String> orgList = originals.stream().map(FilePathDO::getPath).collect(Collectors.toList());
            List<String> fList = originals.stream().map(FilePathDO::getPath).collect(Collectors.toList());
            for (String f : fList) {
                if(!orgList.contains(f)){
                    return false;
                }
            }
            return true;
        }
        return false;


    }


    @Override
    public List<FilePathDTO> getInstrument(String id){
        LawRecordEntity lawRecordEntity = lawRecordService.getById(id);
        LawTypeDTO lawType = lawRecordService.findLawType(id);
        String manualWritFilePath = lawRecordEntity.getManualWritFilePath();
        List<FilePathDO>  autoManually= FilePathDO.getFiles(manualWritFilePath);
        List<FilePathDTO> res=new ArrayList<>();
        if(Objects.nonNull(autoManually)){
            for (FilePathDO filePathDO : autoManually) {
                FilePathDTO filePathDTO=new FilePathDTO();
                BeanUtils.copyProperties(filePathDO,filePathDTO);
                filePathDTO.setName(InstrumentEnum.findByCode(filePathDO.getCode()).getMessage());
                res.add(filePathDTO);
            }
            res.addAll(InstrumentEnum.getManually(lawType));
            //设置文件--用户上传的文件路径 todo

            res.sort(FilePathDTO::compareTo);
        }

        return res;
    }



}
