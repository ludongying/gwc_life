package com.seven.gwc.modular.lawrecord.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.vo.*;

import java.text.ParseException;
import java.util.List;

public interface LawProductService {

    //获取询问人职位
    List<EnumVO> getInvestigatePositionList();

    //获取案件来源
    List<EnumVO> getLawCaseSourceList();

    //获取案件类型
    List<EnumVO> getLawTypeList();

    //获取情节严重类型
    List<EnumVO> getPlotSeverityList();

    //获取生产案由
    List<EnumVO> getProduceReasonList();

    //获取处罚人类型
    List<EnumVO> getPunishmentTypeList();

    //执法记录状态
    List<EnumVO> getRecordStatusList();

    //获取安全生产案由
    List<EnumVO> getSafeReasonList();

    //获取船牌悬挂类型
    List<EnumVO> getShipCaseCardList();

    //获取船涂写情况
    List<EnumVO> getShipCaseList();

    //获取核定作业类型
    List<EnumVO> getShipRatedTypeList();

    //获取实际作业类型
    List<EnumVO> getShipRealTypeList();

    //获取渔船状态
    List<EnumVO> getShipStatusList();

    //案件生产新增
    BaseResult addLawProduct(String personalId, AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO, AppInquisitionEntityVO appInquireSafeEntityVO, AppDecisionVO appDecisionVO, AppReasonVO appReasonVO) throws ParseException;

    //案件安全新增
    BaseResult addLawSafe(String personalId, AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO, AppInquireSafeVO appInquireSafeVO, AppDecisionSafeVO appDecisionSafeVO, AppReasonVO appReasonVO);

    List<AppLawRecordVO> getLawRecordList();

    List<AppLawRecordVO> searchLawRecordList(String search);

}
