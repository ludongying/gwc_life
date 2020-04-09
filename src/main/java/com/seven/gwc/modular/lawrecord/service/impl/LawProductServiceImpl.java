package com.seven.gwc.modular.lawrecord.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.util.FileUtil;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.dao.*;
import com.seven.gwc.modular.lawrecord.dto.*;
import com.seven.gwc.modular.lawrecord.entity.*;
import com.seven.gwc.modular.lawrecord.enums.*;
import com.seven.gwc.modular.lawrecord.service.*;
import com.seven.gwc.modular.lawrecord.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class LawProductServiceImpl implements LawProductService {
    @Autowired
    private LawRecordService lawRecordService;
    @Autowired
    private AgencyMapper agencyMapper;
    @Autowired
    private OperatorMapper operatorMapper;
    @Autowired
    private InquireMapper inquireMapper;
    @Autowired
    private InquisitionMapper inquisitionMapper;
    @Autowired
    private DecisionMapper decisionMapper;
    @Autowired
    private LawRecordMapper lawRecordMapper;
    @Autowired
    private InquireSafeMapper inquireSafeMapper;
    @Autowired
    private DecisionSafeMapper decisionSafeMapper;
    @Autowired
    private EvidenceMapper evidenceMapper;

    @Autowired
    private AgencyService agencyService;
    @Autowired
    private EvidenceService evidenceService;
    @Autowired
    private InquireService inquireService;
    @Autowired
    private InquisitionService inquisitionService;
    @Autowired
    private DecisionService decisionService;
    @Autowired
    private InquireSafeService inquireSafeService;
    @Autowired
    private DecisionSafeService decisionSafeService;
    @Value("${FILE_UPLOAD_PATH_FILE}")
    private String uploadPathFile;


    @Override
    public BaseResult addLawProduct(String personalId, AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO, AppInquisitionEntityVO appInquisitionEntityVO, AppDecisionVO appDecisionVO, AppReasonVO appReasonVO, String evidenceList) throws ParseException {
        LawRecordEntity lawRecordEntity = lawRecordService.createLawRecord(personalId, LawTypeEnum.PRODUCE.getCode());
        AgencyEntity agencyEntity = this.agencyVOToAgency(personalId, lawRecordEntity.getId(), appAgencyVO);
        List<EvidenceEntity> evidenceEntityList = this.getEvidenceEntityList(personalId, lawRecordEntity.getId(), evidenceList);
        OperatorEntity operatorEntity1 = this.operatorVOToOperator1(personalId, lawRecordEntity.getId(), appOperatorVO);
        OperatorEntity operatorEntity2 = this.operatorVOToOperator2(personalId, lawRecordEntity.getId(), appOperatorVO);
        List<InquireEntity> inquireEntityList = this.inquireEntityList(personalId, lawRecordEntity.getId(), null);
        InquisitionEntity inquisitionEntity = this.inquisitionVOToInquisition(personalId, lawRecordEntity.getId(), appInquisitionEntityVO);
        DecisionEntity decisionEntity = this.decisionVOToDecisionEntity(personalId, lawRecordEntity.getId(), appDecisionVO);
        LawRecordEntity lawRecord = this.lawRecordVOToLawRecord(lawRecordEntity, appReasonVO);

        for (EvidenceEntity evidenceEntity : evidenceEntityList) {
            evidenceMapper.insert(evidenceEntity);
        }
        for (InquireEntity inquireEntity : inquireEntityList) {
            inquireMapper.insert(inquireEntity);
        }
        agencyMapper.insert(agencyEntity);
        operatorMapper.insert(operatorEntity1);
        operatorMapper.insert(operatorEntity2);
        inquisitionMapper.insert(inquisitionEntity);
        decisionMapper.insert(decisionEntity);
        lawRecordMapper.updateById(lawRecord);
        return new BaseResult(true, 200, "操作成功");
    }

    @Override
    public BaseResult addLawSafe(String personalId, AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO, AppInquireSafeVO appInquireSafeVO, AppDecisionSafeVO appDecisionSafeVO, AppReasonVO appReasonVO, String evidenceList) throws ParseException {
        LawRecordEntity lawRecordEntity = lawRecordService.createLawRecord(personalId, LawTypeEnum.SAFE.getCode());
        AgencyEntity agencyEntity = this.agencyVOToAgency(personalId, lawRecordEntity.getId(), appAgencyVO);
        List<EvidenceEntity> evidenceEntityList = this.getEvidenceEntityList(personalId, lawRecordEntity.getId(), evidenceList);
        OperatorEntity operatorEntity1 = this.operatorVOToOperator1(personalId, lawRecordEntity.getId(), appOperatorVO);
        OperatorEntity operatorEntity2 = this.operatorVOToOperator2(personalId, lawRecordEntity.getId(), appOperatorVO);
        InquireSafeEntity inquireSafeEntity = this.inquireSafeVOToInquireSafe(personalId, lawRecordEntity.getId(), appInquireSafeVO);
        DecisionSafeEntity decisionSafeEntity = this.decisionSafeVOToDecisionSafe(personalId, lawRecordEntity.getId(), appDecisionSafeVO);
        LawRecordEntity lawRecord = this.lawRecordVOToLawRecord(lawRecordEntity, appReasonVO);
        for (EvidenceEntity evidenceEntity : evidenceEntityList) {
            evidenceMapper.insert(evidenceEntity);
        }
        lawRecordMapper.updateById(lawRecord);
        agencyMapper.insert(agencyEntity);
        operatorMapper.insert(operatorEntity1);
        operatorMapper.insert(operatorEntity2);
        inquireSafeMapper.insert(inquireSafeEntity);
        decisionSafeMapper.insert(decisionSafeEntity);
        return new BaseResult(true, 200, "操作成功");
    }

    @Override
    public List<AppLawRecordVO> getLawRecordList() {
        List<AppLawRecordVO> list = new ArrayList<>();
        LambdaQueryWrapper<LawRecordEntity> lawRecordVOLambdaQueryWrapper = Wrappers.lambdaQuery();
        lawRecordVOLambdaQueryWrapper.eq(LawRecordEntity::getDeleteFlag, true);
        List<LawRecordEntity> lawRecordEntityList = lawRecordMapper.selectList(lawRecordVOLambdaQueryWrapper);
        for (LawRecordEntity lawRecordEntity : lawRecordEntityList) {
            AppLawRecordVO lawRecordVO = new AppLawRecordVO();
            AgencyEntity agencyEntity = agencyMapper.selectById(lawRecordEntity.getId());
            if (ToolUtil.isNotEmpty(agencyEntity)) {
                lawRecordVO.setId(lawRecordEntity.getId());
                lawRecordVO.setLawCaseCode(agencyEntity.getLawCaseFineCode()+"" + agencyEntity.getLawCaseCode());
                lawRecordVO.setLawCaseSource(LawCaseSourceEnum.findByCode(agencyEntity.getLawCaseSource()).getMessage());
                lawRecordVO.setLawCaseLon(agencyEntity.getLawCaseLon());
                lawRecordVO.setLawCaseLat(agencyEntity.getLawCaseLat());
                lawRecordVO.setLawCaseDate(agencyEntity.getLawCaseDate());
            }
            list.add(lawRecordVO);
        }
        return list;
    }

    @Override
    public List<AppLawRecordVO> searchLawRecordList(String search) {
        List<AppLawRecordVO> list = new ArrayList<>();
        LambdaQueryWrapper<LawRecordEntity> lawRecordVOLambdaQueryWrapper = Wrappers.lambdaQuery();
        lawRecordVOLambdaQueryWrapper.eq(LawRecordEntity::getDeleteFlag, true);
        List<LawRecordEntity> lawRecordEntityList = lawRecordMapper.selectList(lawRecordVOLambdaQueryWrapper);
        if (ToolUtil.isNotEmpty(search)) {
            for (LawRecordEntity lawRecordEntity : lawRecordEntityList) {
                AgencyEntity agencyEntity = agencyMapper.selectById(lawRecordEntity.getId());
                if (ToolUtil.isNotEmpty(agencyEntity)) {
                    String code = agencyEntity.getLawCaseFineCode() +""+ agencyEntity.getLawCaseCode();
                    if (code.contains(search)){
                        AppLawRecordVO lawRecordVO = new AppLawRecordVO();
                        lawRecordVO.setLawCaseCode(agencyEntity.getLawCaseFineCode() +""+ agencyEntity.getLawCaseCode());
                        lawRecordVO.setLawCaseSource(LawCaseSourceEnum.findByCode(agencyEntity.getLawCaseSource()).getMessage());
                        lawRecordVO.setLawCaseLon(agencyEntity.getLawCaseLon());
                        lawRecordVO.setLawCaseLat(agencyEntity.getLawCaseLat());
                        lawRecordVO.setLawCaseDate(agencyEntity.getLawCaseDate());
                        lawRecordVO.setId(lawRecordEntity.getId());
                        list.add(lawRecordVO);
                    }
                }
            }
        } else {
            list = this.getLawRecordList();
        }
        return list;
    }

    @Override
    public AppRecordDetailVO getRecordDetail(String id) {
        AppRecordDetailVO appRecordDetailVO = new AppRecordDetailVO();

        LawRecordEntity lawRecordEntity = lawRecordMapper.selectById(id);
        Integer lawType = lawRecordEntity.getLawType();
        appRecordDetailVO.setLawType(lawType);

        AgencyDTO agencyDTO = agencyService.detail(id);
        if(Objects.nonNull(agencyDTO)){
            agencyDTO.setDetailContent();
        }
        appRecordDetailVO.setAgencyDTO(agencyDTO);

        List<EvidenceDTO> evidenceDTOList = evidenceService.evidenceDTODetail(id);
        appRecordDetailVO.setEvidenceDTOList(evidenceDTOList);
        if(LawTypeEnum.PRODUCE.getCode().equals(lawType)){
            List<InquireDTO> inquireDTOList = inquireService.inquireDTODetail(id);
            appRecordDetailVO.setInquireDTOList(inquireDTOList);
            InquisitionDTO inquisitionDTO = inquisitionService.detail(id);
            if(Objects.nonNull(inquisitionDTO)){
                inquisitionDTO.setDetailContent();
            }
            appRecordDetailVO.setInquisitionDTO(inquisitionDTO);
            ReasonProduceDTO reasonProduceDTO = new ReasonProduceDTO(lawRecordEntity);
            appRecordDetailVO.setReasonProduceDTO(reasonProduceDTO);
            DecisionDTO decisionDTO = decisionService.detail(id);
            if(Objects.nonNull(decisionDTO)){
                decisionDTO.setDetailContent();
            }
            appRecordDetailVO.setDecisionDTO(decisionDTO);
        } else {
            InquireSafeDTO inquire = inquireSafeService.detail(id);
            if(Objects.nonNull(inquire)){
                inquire.setDetailContent();
            }
            appRecordDetailVO.setInquireSafeDTO(inquire);
            ReasonSafeDTO reasonSafeDTO = new ReasonSafeDTO(lawRecordEntity);
            appRecordDetailVO.setReasonSafeDTO(reasonSafeDTO);
            DecisionSafeDTO decisionSafeDTO = decisionSafeService.detail(id);
            if(Objects.nonNull(decisionSafeDTO)){
                decisionSafeDTO.setDetailContent();
            }
            appRecordDetailVO.setDecisionSafeDTO(decisionSafeDTO);
        }
        return appRecordDetailVO;
    }

    @Override
    public BaseResult addEvidence(String personalId, String id, String evidenceList) throws ParseException {
        List<EvidenceEntity> evidenceEntityList = this.getEvidenceEntityList(personalId, id, evidenceList);
        for (EvidenceEntity evidenceEntity : evidenceEntityList) {
            evidenceMapper.insert(evidenceEntity);
        }
        return new BaseResult(true, 200, "操作成功");
    }

    @Override
    public List<EnumVO> getLawCaseSourceList() {
        List<EnumVO> list = new ArrayList<>();
        for (LawCaseSourceEnum vo : LawCaseSourceEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getLawTypeList() {
        List<EnumVO> list = new ArrayList<>();
        for (LawTypeEnum vo : LawTypeEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getPlotSeverityList() {
        List<EnumVO> list = new ArrayList<>();
        for (PlotSeverityEnum vo : PlotSeverityEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getProduceReasonList() {
        List<EnumVO> list = new ArrayList<>();
        for (ProduceReasonEnum vo : ProduceReasonEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getPunishmentTypeList() {
        List<EnumVO> list = new ArrayList<>();
        for (PunishmentTypeEnum vo : PunishmentTypeEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getRecordStatusList() {
        List<EnumVO> list = new ArrayList<>();
        for (RecordStatusEnum vo : RecordStatusEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getSafeReasonList() {
        List<EnumVO> list = new ArrayList<>();
        for (SafeReasonEnum vo : SafeReasonEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getShipCaseCardList() {
        List<EnumVO> list = new ArrayList<>();
        for (ShipCaseCardEnum vo : ShipCaseCardEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getShipCaseList() {
        List<EnumVO> list = new ArrayList<>();
        for (ShipCaseEnum vo : ShipCaseEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getShipRatedTypeList() {
        List<EnumVO> list = new ArrayList<>();
        for (ShipRatedTypeEnum vo : ShipRatedTypeEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getShipRealTypeList() {
        List<EnumVO> list = new ArrayList<>();
        for (ShipRealTypeEnum vo : ShipRealTypeEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getShipStatusList() {
        List<EnumVO> list = new ArrayList<>();
        for (ShipStatusEnum vo : ShipStatusEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    @Override
    public List<EnumVO> getInvestigatePositionList() {
        List<EnumVO> list = new ArrayList<>();
        for (InvestigatePositionEnum vo : InvestigatePositionEnum.values()) {
            EnumVO enumVO = new EnumVO();
            enumVO.setId(vo.getCode());
            enumVO.setName(vo.getMessage());
            list.add(enumVO);
        }
        return list;
    }

    private LawRecordEntity lawRecordVOToLawRecord(LawRecordEntity lawRecordEntity, AppReasonVO appReasonVO) {
        lawRecordEntity.setMainReason(appReasonVO.getMainReason());
        lawRecordEntity.setSecondReason(appReasonVO.getSecondReason());
        return lawRecordEntity;
    }

    private DecisionSafeEntity decisionSafeVOToDecisionSafe(String personalId, String id, AppDecisionSafeVO appDecisionSafeVO) {
        DecisionSafeEntity decisionSafeEntity = new DecisionSafeEntity();
        decisionSafeEntity.setId(id);
        decisionSafeEntity.setPunishPersonType(appDecisionSafeVO.getPunishPersonType());
        decisionSafeEntity.setPunishCompanyName(appDecisionSafeVO.getPunishCompanyName());
        decisionSafeEntity.setPunishPersonName(appDecisionSafeVO.getPunishPersonName());
        decisionSafeEntity.setPunishAge(appDecisionSafeVO.getPunishAge());
        decisionSafeEntity.setPunishSex(appDecisionSafeVO.getPunishSex());
        decisionSafeEntity.setPunishIdentityCard(appDecisionSafeVO.getPunishIdentityCard());
        decisionSafeEntity.setPunishTel(appDecisionSafeVO.getPunishTel());
        decisionSafeEntity.setPunishAddr(appDecisionSafeVO.getPunishAddr());
        decisionSafeEntity.setSeverity(appDecisionSafeVO.getSeverity());
        decisionSafeEntity.setFine(appDecisionSafeVO.getFine());
        decisionSafeEntity.setResourceCompensation(appDecisionSafeVO.getResourceCompensation());
        decisionSafeEntity.setDescription(appDecisionSafeVO.getDescription());
        decisionSafeEntity.setCaseViolenceLaw(appDecisionSafeVO.getCaseViolenceLaw());
        decisionSafeEntity.setFineUpper(appDecisionSafeVO.getFineUpper());
        decisionSafeEntity.setResourceCompensationUpper(appDecisionSafeVO.getResourceCompensationUpper());
        decisionSafeEntity.setDeleteFlag(true);
        decisionSafeEntity.setCreateDate(new Date());
        decisionSafeEntity.setCreatePerson(personalId);

        return decisionSafeEntity;
    }

    private InquireSafeEntity inquireSafeVOToInquireSafe(String personalId, String id, AppInquireSafeVO appInquireSafeVO) {
        InquireSafeEntity inquireSafeEntity = new InquireSafeEntity();
        inquireSafeEntity.setId(id);
        inquireSafeEntity.setInvestigateName(appInquireSafeVO.getInvestigateName());
        inquireSafeEntity.setIdentityCase(appInquireSafeVO.getIdentityCase());
        inquireSafeEntity.setInvestigateSex(appInquireSafeVO.getInvestigateSex());
        inquireSafeEntity.setInvestigateAge(appInquireSafeVO.getInvestigateAge());
        inquireSafeEntity.setInvestigatePosition(appInquireSafeVO.getInvestigatePosition());
        inquireSafeEntity.setInvestigateTel(appInquireSafeVO.getInvestigateTel());
        inquireSafeEntity.setInvestigateAddr(appInquireSafeVO.getInvestigateAddr());
        inquireSafeEntity.setIdentityCard(appInquireSafeVO.getIdentityCard());
        inquireSafeEntity.setShipName(appInquireSafeVO.getShipName());
        inquireSafeEntity.setShipOwner(appInquireSafeVO.getShipOwner());
        inquireSafeEntity.setShipMember(appInquireSafeVO.getShipMember());
        inquireSafeEntity.setShipCaseName(appInquireSafeVO.getShipCaseName());
        inquireSafeEntity.setShipCaseRegistry(appInquireSafeVO.getShipCaseRegistry());
        inquireSafeEntity.setShipRegistry(appInquireSafeVO.getShipRegistry());
        inquireSafeEntity.setShipCaseCard(appInquireSafeVO.getShipCaseCard());
        inquireSafeEntity.setLifebuoyCount(appInquireSafeVO.getLifebuoyCount());
        inquireSafeEntity.setExtinguisherCount(appInquireSafeVO.getExtinguisherCount());
        inquireSafeEntity.setLifeVestCount(appInquireSafeVO.getLifeVestCount());
        inquireSafeEntity.setLifeRaftCount(appInquireSafeVO.getLifeRaftCount());
        inquireSafeEntity.setShipCertificateCount(appInquireSafeVO.getShipCertificateCount());
        inquireSafeEntity.setShipCommonCertificateCount(appInquireSafeVO.getShipCommonCertificateCount());
        inquireSafeEntity.setShipLength(appInquireSafeVO.getShipLength());
        inquireSafeEntity.setShipStatus(appInquireSafeVO.getShipStatus());
        inquireSafeEntity.setCreateDate(new Date());
        inquireSafeEntity.setCreatePerson(personalId);
        inquireSafeEntity.setDeleteFlag(true);
        return inquireSafeEntity;
    }

    //决定VO转对象-生产
    private DecisionEntity decisionVOToDecisionEntity(String personalId, String id, AppDecisionVO appDecisionVO) {
        DecisionEntity decisionEntity = new DecisionEntity();
        decisionEntity.setId(id);
        decisionEntity.setPunishPersonType(appDecisionVO.getPunishPersonType());
        decisionEntity.setPunishCompanyName(appDecisionVO.getPunishCompanyName());
        decisionEntity.setPunishPersonName(appDecisionVO.getPunishPersonName());
        decisionEntity.setPunishAge(appDecisionVO.getPunishAge());
        decisionEntity.setPunishSex(appDecisionVO.getPunishSex());
        decisionEntity.setPunishIdentityCard(appDecisionVO.getPunishIdentityCard());
        decisionEntity.setPunishTel(appDecisionVO.getPunishTel());
        decisionEntity.setPunishAddr(appDecisionVO.getPunishAddr());
        decisionEntity.setSeverity(appDecisionVO.getSeverity());
        decisionEntity.setCaseBureauPrice(appDecisionVO.getCaseBureauPrice());
        decisionEntity.setCaseNotification(appDecisionVO.getCaseNotification());
        decisionEntity.setFine(appDecisionVO.getFine());
        decisionEntity.setResourceCompensation(appDecisionVO.getResourceCompensation());
        decisionEntity.setDescription(appDecisionVO.getDescription());
        decisionEntity.setCaseViolenceLaw(appDecisionVO.getCaseViolenceLaw());
        decisionEntity.setCreateDate(new Date());
        decisionEntity.setCreatePerson(personalId);
        decisionEntity.setDeleteFlag(true);
        return decisionEntity;
    }

    //勘验笔录VO转对象
    private InquisitionEntity inquisitionVOToInquisition(String personalId, String id, AppInquisitionEntityVO appInquireSafeEntityVO) {
        InquisitionEntity inquisitionEntity = new InquisitionEntity();
        inquisitionEntity.setId(id);
        inquisitionEntity.setShipCaseName(appInquireSafeEntityVO.getShipCaseName());
        inquisitionEntity.setShipCaseRegistry(appInquireSafeEntityVO.getShipCaseRegistry());
        inquisitionEntity.setShipRegistry(appInquireSafeEntityVO.getShipRegistry());
        inquisitionEntity.setShipCaseCard(appInquireSafeEntityVO.getShipCaseCard());
        inquisitionEntity.setShipCaseCredentials(appInquireSafeEntityVO.getShipCaseCredentials());
        inquisitionEntity.setShipCaseFish(appInquireSafeEntityVO.getShipCaseFish());
        inquisitionEntity.setShipCredentialsCode(appInquireSafeEntityVO.getShipCredentialsCode());
        inquisitionEntity.setShipCode(appInquireSafeEntityVO.getShipCode());
        inquisitionEntity.setShipOperatePerson(appInquireSafeEntityVO.getShipOperatePerson());
        inquisitionEntity.setShipPower(appInquireSafeEntityVO.getShipPower());
        inquisitionEntity.setShipCredentialsOwner(appInquireSafeEntityVO.getShipCredentialsOwner());
        inquisitionEntity.setShipIdentityCase(appInquireSafeEntityVO.getShipIdentityCase());
        inquisitionEntity.setShipCredentialsOwnerIdentity(appInquireSafeEntityVO.getShipCredentialsOwnerIdentity());
        inquisitionEntity.setShipStatus(appInquireSafeEntityVO.getShipStatus());
        inquisitionEntity.setCreateDate(new Date());
        inquisitionEntity.setCreatePerson(personalId);
        inquisitionEntity.setDeleteFlag(true);
        return inquisitionEntity;
    }

    private List<EvidenceEntity> getEvidenceEntityList(String personalId, String id, String data) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<EvidenceEntity> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(data);
        for(int i=0; i<jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String files = jsonObject.getString("files");
            JSONArray fileArray = JSONArray.parseArray(files);

            String fileName = "";
            EvidenceEntity evidenceEntity = new EvidenceEntity();
            evidenceEntity.setCreateDate(new Date());
            evidenceEntity.setCreatePerson(personalId);
            evidenceEntity.setRecordId(id);
            evidenceEntity.setEvidenceContent(jsonObject.getString("evidenceContent"));
            evidenceEntity.setEvidenceDate(formatter.parse(jsonObject.getString("evidenceDate")));

            for(int j=0; j<fileArray.size(); j++){
                JSONObject fileObject = fileArray.getJSONObject(j);
                fileName = FileUtil.base64ToFile(uploadPathFile, fileObject.getString("fileName"));
            }
            evidenceEntity.setEvidenceFilePath(fileName);
            list.add(evidenceEntity);
        }
        return list;
    }

    private List<InquireEntity> inquireEntityList(String personalId, String id, String data) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<InquireEntity> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray("data");
        for(int i=0; i<jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            InquireEntity inquireEntity = new InquireEntity();
            inquireEntity.setRecordId(id);
            inquireEntity.setInvestigateName(jsonObject.getString("investigateName"));
            inquireEntity.setInvestigateSex(Integer.parseInt(jsonObject.getString("investigateSex")));
            inquireEntity.setInvestigateAge(Integer.parseInt(jsonObject.getString("investigateAge")));
            inquireEntity.setInvestigatePosition(Integer.parseInt(jsonObject.getString("investigatePosition")));
            inquireEntity.setInvestigateTel(jsonObject.getString("investigateTel"));
            inquireEntity.setInvestigateAddr(jsonObject.getString("investigateAddr"));
            inquireEntity.setIdentityCase(Integer.parseInt(jsonObject.getString("identityCase")));
            inquireEntity.setIdentityCard(jsonObject.getString("identityCard"));
            inquireEntity.setShipName(jsonObject.getString("shipName"));
            inquireEntity.setShipOwner(jsonObject.getString("shipOwner"));
            inquireEntity.setShipMember(Integer.parseInt(jsonObject.getString("shipMember")));
            inquireEntity.setShipRealType(Integer.parseInt(jsonObject.getString("shipRealType")));
            inquireEntity.setShipRatedType(Integer.parseInt(jsonObject.getString("shipRatedType")));
            inquireEntity.setShipRealPower(Double.parseDouble(jsonObject.getString("shipRealPower")));
            inquireEntity.setShipRatedPower(Double.parseDouble(jsonObject.getString("shipRatedPower")));
            inquireEntity.setShipRealPowerUnit(Integer.parseInt(jsonObject.getString("shipRealPowerUnit")));
            inquireEntity.setShipRatedPowerUnit(Integer.parseInt(jsonObject.getString("shipRatedPowerUnit")));
            inquireEntity.setShipInfo(jsonObject.getString("shipInfo"));
            inquireEntity.setShipStatus(Integer.parseInt(jsonObject.getString("shipStatus")));
            inquireEntity.setShipOutDate(formatter.parse(jsonObject.getString("shipOutDate")));
            inquireEntity.setShipOutPort(jsonObject.getString("shipOutPort"));
            inquireEntity.setShipGoodsCount(jsonObject.getString("shipGoodsCount"));
            inquireEntity.setShipGoodsValue(jsonObject.getString("shipGoodsValue"));
            inquireEntity.setShipGenerateCount(Integer.parseInt(jsonObject.getString("shipGenerateCount")));
            inquireEntity.setShipFishAreaDate(formatter.parse(jsonObject.getString("shipFishAreaDate")));

            inquireEntity.setCreateDate(new Date());
            inquireEntity.setCreatePerson(personalId);
            inquireEntity.setDeleteFlag(true);
            list.add(inquireEntity);
        }
        return list;
    }

    //执法人员VO转对象
    private OperatorEntity operatorVOToOperator1(String personalId, String id, AppOperatorVO appOperatorVO) {
        OperatorEntity operatorEntity = new OperatorEntity();
        operatorEntity.setRecordId(id);
        operatorEntity.setLawPersonName(appOperatorVO.getLawPersonName1());
        operatorEntity.setLawCredentialCode(appOperatorVO.getLawCredentialCode1());
        operatorEntity.setCreateDate(new Date());
        operatorEntity.setCreatePerson(personalId);
        operatorEntity.setDeleteFlag(true);
        return operatorEntity;
    }

    //执法人员VO转对象
    private OperatorEntity operatorVOToOperator2(String personalId, String id, AppOperatorVO appOperatorVO) {
        OperatorEntity operatorEntity = new OperatorEntity();
        operatorEntity.setRecordId(id);
        operatorEntity.setLawPersonName(appOperatorVO.getLawPersonName2());
        operatorEntity.setLawCredentialCode(appOperatorVO.getLawCredentialCode2());
        operatorEntity.setCreateDate(new Date());
        operatorEntity.setCreatePerson(personalId);
        operatorEntity.setDeleteFlag(true);
        return operatorEntity;
    }

    //办案机关VO转对象
    private AgencyEntity agencyVOToAgency(String personalId, String id, AppAgencyVO appAgencyVO) {
        AgencyEntity agencyEntity = new AgencyEntity();
        agencyEntity.setId(id);
        agencyEntity.setShortName(appAgencyVO.getShortName());
        agencyEntity.setLawShipCode(appAgencyVO.getLawShipCode());
        agencyEntity.setEnforcementAgency(appAgencyVO.getEnforcementAgency());
        agencyEntity.setLawCaseFineCode(appAgencyVO.getLawCaseFineCode());
        agencyEntity.setLawCaseCode(appAgencyVO.getLawCaseCode());
        agencyEntity.setLawCaseSource(appAgencyVO.getLawCaseSource());
        agencyEntity.setLawCaseLon(appAgencyVO.getLawCaseLon());
        agencyEntity.setLawCaseLat(appAgencyVO.getLawCaseLat());
        agencyEntity.setLawCaseAddr(appAgencyVO.getLawCaseAddr());
        agencyEntity.setLawCaseDate(appAgencyVO.getLawCaseDate());
        agencyEntity.setAgencyPerson(appAgencyVO.getAgencyPerson());
        agencyEntity.setAgencyTel(appAgencyVO.getAgencyTel());
        agencyEntity.setAgencyAddr(appAgencyVO.getAgencyAddr());
        agencyEntity.setBank(appAgencyVO.getBank());
        agencyEntity.setReviewAgency(appAgencyVO.getReviewAgency());
        agencyEntity.setLawsuitAgency(appAgencyVO.getLawsuitAgency());
        agencyEntity.setProspectStartDate(appAgencyVO.getProspectStartDate());
        agencyEntity.setProspectEndDate(appAgencyVO.getProspectEndDate());
        agencyEntity.setInquireStartDate(appAgencyVO.getInquireStartDate());
        agencyEntity.setInquireEndDate(appAgencyVO.getInquireEndDate());
        agencyEntity.setTelApplyDate(appAgencyVO.getTelApplyDate());
        agencyEntity.setAcceptDate(appAgencyVO.getAcceptDate());
        agencyEntity.setDealDate(appAgencyVO.getDealDate());
        agencyEntity.setPunishDate(appAgencyVO.getPunishDate());
        agencyEntity.setDecisionDate(appAgencyVO.getDecisionDate());
        agencyEntity.setFinishDate(appAgencyVO.getFinishDate());
        agencyEntity.setCreatePerson(personalId);
        agencyEntity.setCreateDate(new Date());
        agencyEntity.setDeleteFlag(true);
        agencyEntity.setSaveDate(appAgencyVO.getSaveDate());
        return agencyEntity;
    }
}
