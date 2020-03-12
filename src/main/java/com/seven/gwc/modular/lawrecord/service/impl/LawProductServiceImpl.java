package com.seven.gwc.modular.lawrecord.service.impl;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dao.*;
import com.seven.gwc.modular.lawrecord.entity.*;
import com.seven.gwc.modular.lawrecord.enums.*;
import com.seven.gwc.modular.lawrecord.service.LawProductService;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    @Override
    public BaseResult addLawProduct(String personalId, AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO, AppInquireVO appInquireVO, AppInquisitionEntityVO appInquisitionEntityVO, AppDecisionVO appDecisionVO, AppLawRecordVO appLawRecordVO) {
        LawRecordEntity lawRecordEntity = lawRecordService.createLawRecord(personalId, LawTypeEnum.PRODUCE.getCode());
        AgencyEntity agencyEntity = this.agencyVOToAgency(personalId, lawRecordEntity.getId(), appAgencyVO);
        OperatorEntity operatorEntity1 = this.operatorVOToOperator1(personalId, lawRecordEntity.getId(), appOperatorVO);
        OperatorEntity operatorEntity2 = this.operatorVOToOperator2(personalId, lawRecordEntity.getId(), appOperatorVO);
        InquireEntity inquireEntity = this.inquireVOToInquire(personalId, lawRecordEntity.getId(), appInquireVO);
        InquisitionEntity inquisitionEntity = this.inquisitionVOToInquisition(personalId, lawRecordEntity.getId(), appInquisitionEntityVO);
        DecisionEntity decisionEntity = this.decisionVOToDecisionEntity(personalId, lawRecordEntity.getId(), appDecisionVO);
        LawRecordEntity lawRecord = this.lawRecordVOToLawRecord(lawRecordEntity, appLawRecordVO);
        agencyMapper.insert(agencyEntity);
        operatorMapper.insert(operatorEntity1);
        operatorMapper.insert(operatorEntity2);
        inquireMapper.insert(inquireEntity);
        inquisitionMapper.insert(inquisitionEntity);
        decisionMapper.insert(decisionEntity);
        lawRecordMapper.updateById(lawRecord);
        return new BaseResult(true, 200, "操作成功");
    }

    @Override
    public BaseResult addLawSafe(String personalId, AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO, AppInquireSafeVO appInquireSafeVO, AppDecisionSafeVO appDecisionSafeVO, AppLawRecordVO appLawRecordVO) {
        LawRecordEntity lawRecordEntity = lawRecordService.createLawRecord(personalId, LawTypeEnum.SAFE.getCode());
        AgencyEntity agencyEntity = this.agencyVOToAgency(personalId, lawRecordEntity.getId(), appAgencyVO);
        OperatorEntity operatorEntity1 = this.operatorVOToOperator1(personalId, lawRecordEntity.getId(), appOperatorVO);
        OperatorEntity operatorEntity2 = this.operatorVOToOperator2(personalId, lawRecordEntity.getId(), appOperatorVO);
        InquireSafeEntity inquireSafeEntity = this.inquireSafeVOToInquireSafe(personalId, lawRecordEntity.getId(), appInquireSafeVO);
        DecisionSafeEntity decisionSafeEntity = this.decisionSafeVOToDecisionSafe(personalId, lawRecordEntity.getId(), appDecisionSafeVO);
        LawRecordEntity lawRecord = this.lawRecordVOToLawRecord(lawRecordEntity, appLawRecordVO);
        lawRecordMapper.updateById(lawRecord);
        agencyMapper.insert(agencyEntity);
        operatorMapper.insert(operatorEntity1);
        operatorMapper.insert(operatorEntity2);
        inquireSafeMapper.insert(inquireSafeEntity);
        decisionSafeMapper.insert(decisionSafeEntity);
        return new BaseResult(true, 200, "操作成功");
    }

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

    private LawRecordEntity lawRecordVOToLawRecord(LawRecordEntity lawRecordEntity, AppLawRecordVO appLawRecordVO) {
        lawRecordEntity.setMainReason(appLawRecordVO.getMainReason());
        lawRecordEntity.setSecondReason(appLawRecordVO.getSecondReason());
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
        decisionSafeEntity.setDeleteFlag(true);
        decisionSafeEntity.setCreateDate(new Date());
        decisionSafeEntity.setCreatePerson(personalId);

        return decisionSafeEntity;
    }

    private InquireSafeEntity inquireSafeVOToInquireSafe(String personalId, String id, AppInquireSafeVO appInquireSafeVO) {
        InquireSafeEntity inquireSafeEntity = new InquireSafeEntity();
        inquireSafeEntity.setId(id);
        inquireSafeEntity.setInvestigateName(appInquireSafeVO.getInvestigateName());
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

    //询问笔录VO转对象 --生产
    private InquireEntity inquireVOToInquire(String personalId, String id, AppInquireVO appInquireVO) {
        InquireEntity inquireEntity = new InquireEntity();
        inquireEntity.setId(id);
        inquireEntity.setInvestigateName(appInquireVO.getInvestigateName());
        inquireEntity.setInvestigateSex(appInquireVO.getInvestigateSex());
        inquireEntity.setInvestigateAge(appInquireVO.getInvestigateAge());
        inquireEntity.setInvestigatePosition(appInquireVO.getInvestigatePosition());
        inquireEntity.setInvestigateTel(appInquireVO.getInvestigateTel());
        inquireEntity.setInvestigateAddr(appInquireVO.getInvestigateAddr());
        inquireEntity.setIdentityCase(appInquireVO.getIdentityCase());
        inquireEntity.setIdentityCard(appInquireVO.getIdentityCard());
        inquireEntity.setShipName(appInquireVO.getShipName());
        inquireEntity.setShipOwner(appInquireVO.getShipOwner());
        inquireEntity.setShipMember(appInquireVO.getShipMember());
        inquireEntity.setShipRealType(appInquireVO.getShipRealType());
        inquireEntity.setShipRatedType(appInquireVO.getShipRatedType());
        inquireEntity.setShipRealPower(appInquireVO.getShipRealPower());
        inquireEntity.setShipRatedPower(appInquireVO.getShipRatedPower());
        inquireEntity.setShipInfo(appInquireVO.getShipInfo());
        inquireEntity.setShipStatus(appInquireVO.getShipStatus());
        inquireEntity.setShipOutDate(appInquireVO.getShipOutDate());
        inquireEntity.setShipOutPort(appInquireVO.getShipOutPort());
        inquireEntity.setShipGoodsCount(appInquireVO.getShipGoodsCount());
        inquireEntity.setShipGoodsValue(appInquireVO.getShipGoodsValue());
        inquireEntity.setShipGenerateCount(appInquireVO.getShipGenerateCount());
        inquireEntity.setShipFishAreaDate(appInquireVO.getShipFishAreaDate());
        inquireEntity.setCreateDate(new Date());
        inquireEntity.setCreatePerson(personalId);
        inquireEntity.setDeleteFlag(true);
        return inquireEntity;
    }

    //执法人员VO转对象
    private OperatorEntity operatorVOToOperator1(String personalId, String id, AppOperatorVO appOperatorVO) {
        OperatorEntity operatorEntity = new OperatorEntity();
        operatorEntity.setId(id);
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
        operatorEntity.setId(id);
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
        return agencyEntity;
    }
}
