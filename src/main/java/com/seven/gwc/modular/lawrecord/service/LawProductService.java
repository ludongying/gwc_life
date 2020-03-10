package com.seven.gwc.modular.lawrecord.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.vo.AppAgencyVO;
import com.seven.gwc.modular.lawrecord.vo.AppInquireVO;
import com.seven.gwc.modular.lawrecord.vo.AppOperatorVO;
import com.seven.gwc.modular.lawrecord.vo.EnumVO;

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

    //获取案件类型
    List<EnumVO> getProduceReasonList();

    //获取生产案由
    List<EnumVO> getPunishmentTypeList();

    //获取案件类型
    List<EnumVO> getSafeReasonList();

    //获取
    List<EnumVO> getShipCaseCardList();

    //获取
    List<EnumVO> getShipCaseList();

    //获取核定作业类型
    List<EnumVO> getShipRatedTypeList();

    //获取实际作业类型
    List<EnumVO> getShipRealTypeList();

    //获取
    List<EnumVO> getShipStatusList();

    BaseResult addLawProduct(String personalId, AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO, AppInquireVO appInquireVO);
}
