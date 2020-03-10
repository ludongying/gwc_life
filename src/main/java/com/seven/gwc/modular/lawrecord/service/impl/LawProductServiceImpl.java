package com.seven.gwc.modular.lawrecord.service.impl;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dao.AgencyMapper;
import com.seven.gwc.modular.lawrecord.entity.AgencyEntity;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.entity.OperatorEntity;
import com.seven.gwc.modular.lawrecord.enums.*;
import com.seven.gwc.modular.lawrecord.service.LawProductService;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.AppAgencyVO;
import com.seven.gwc.modular.lawrecord.vo.AppInquireVO;
import com.seven.gwc.modular.lawrecord.vo.AppOperatorVO;
import com.seven.gwc.modular.lawrecord.vo.EnumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LawProductServiceImpl implements LawProductService {
    @Autowired
    private LawRecordService lawRecordService;


    @Override
    public BaseResult addLawProduct(String personalId, AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO, AppInquireVO appInquireVO) {
        LawRecordEntity lawRecordEntity = lawRecordService.createLawRecord(personalId, LawTypeEnum.PRODUCE.getCode());
        AgencyEntity agencyEntity = this.agencyVOToAgency(appAgencyVO);
        OperatorEntity operatorEntity1 = this.operatorVOToOperator1(appOperatorVO);
        OperatorEntity operatorEntity2 = this.operatorVOToOperator2(appOperatorVO);
        InquireEntity inquireEntity = this.inquireVOToInquire(appInquireVO);

        return null;
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

    //询问笔录VO转对象
    private InquireEntity inquireVOToInquire(AppInquireVO appInquireVO) {
        InquireEntity inquireEntity = new InquireEntity();
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
        return inquireEntity;
    }

    //执法人员VO转对象
    private OperatorEntity operatorVOToOperator1(AppOperatorVO appOperatorVO) {
        OperatorEntity operatorEntity = new OperatorEntity();
        operatorEntity.setLawPersonName(appOperatorVO.getLawPersonName1());
        operatorEntity.setLawCredentialCode(appOperatorVO.getLawCredentialCode1());
        return operatorEntity;
    }

    //执法人员VO转对象
    private OperatorEntity operatorVOToOperator2(AppOperatorVO appOperatorVO) {
        OperatorEntity operatorEntity = new OperatorEntity();
        operatorEntity.setLawPersonName(appOperatorVO.getLawPersonName2());
        operatorEntity.setLawCredentialCode(appOperatorVO.getLawCredentialCode2());
        return operatorEntity;
    }

    //办案机关VO转对象
    private AgencyEntity agencyVOToAgency(AppAgencyVO appAgencyVO) {
        AgencyEntity agencyEntity = new AgencyEntity();
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
        return agencyEntity;
    }
}
