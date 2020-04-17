package com.seven.gwc.modular.api;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.enums.SafeReasonEnum;
import com.seven.gwc.modular.lawrecord.service.LawProductService;
import com.seven.gwc.modular.lawrecord.vo.*;
import com.seven.gwc.modular.sailor.vo.PersonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@Api(tags = "执法")
@RequestMapping("gwcApi/lawRecord")
public class LawRecordApi {
    @Autowired
    private LawProductService lawProductService;

    @PostMapping(value = "/addLawProduct")
    @ApiOperation(value = "新增新案件-生产")
    public BaseResult addLawProduct(HttpServletRequest request,AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO, AppInquisitionEntityVO appInquireSafeEntityVO,
                                    AppDecisionVO appDecisionVO, AppReasonVO appReasonVO, String caseInquiryList, String caseEvidenceList) {
        String userId = request.getAttribute("userId").toString();
        return lawProductService.addLawProduct(userId, appAgencyVO, appOperatorVO, appInquireSafeEntityVO, appDecisionVO, appReasonVO, caseInquiryList, caseEvidenceList);
    }

    @PostMapping(value = "/addLawSafe")
    @ApiOperation(value = "新增新案件-安全")
    public BaseResult addLawSafe(HttpServletRequest request, AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO,
                                 AppInquireSafeVO appInquireSafeVO, AppDecisionSafeVO appDecisionSafeVO, AppReasonVO appReasonVO, String evidenceList) throws ParseException {
        String userId = request.getAttribute("userId").toString();
        return lawProductService.addLawSafe(userId, appAgencyVO, appOperatorVO, appInquireSafeVO, appDecisionSafeVO, appReasonVO, evidenceList);
    }

    @GetMapping(value = "/getRecordDetail")
    @ApiOperation(value = "获取笔录详情")
    public BaseResult<AppRecordDetailVO> getRecordDetail(String id) {
        return new BaseResult().content(lawProductService.getRecordDetail(id));
    }

    @PostMapping(value = "/addEvidence")
    @ApiOperation(value = "证据补充-图片")
    public BaseResult addEvidence(HttpServletRequest request, String id, String evidenceList) {
        String userId = request.getAttribute("userId").toString();
        return lawProductService.addEvidence(userId, id, evidenceList);
    }

    @PostMapping(value = "/addEvidenceVideo")
    @ApiOperation(value = "证据补充-视频")
    public BaseResult addEvidenceVideo(HttpServletRequest request, String id, String evidenceList) {
        String userId = request.getAttribute("userId").toString();
        return lawProductService.addEvidenceVideo(userId, id, evidenceList);
    }

    @GetMapping(value = "/getLawRecordList")
    @ApiOperation(value = "获取案件任务列表")
    public BaseResult<List<AppLawRecordVO>> getLawRecordList() {
        return new BaseResult().content(lawProductService.getLawRecordList());
    }

    @GetMapping(value = "/searchLawRecordList")
    @ApiOperation(value = "查询案件任务列表")
    public BaseResult<List<AppLawRecordVO>> searchLawRecordList( @ApiParam(name = "search", value = "查询条件")String search) {
        return new BaseResult().content(lawProductService.searchLawRecordList(search));
    }

    @GetMapping(value = "/getInvestigatePositionList")
    @ApiOperation(value = "获取询问人职位")
    public BaseResult<List<EnumVO>> getInvestigatePositionList() {
        return new BaseResult().content(lawProductService.getInvestigatePositionList());
    }

    @GetMapping(value = "/getLawCaseSourceList")
    @ApiOperation(value = "获取案件来源列表")
    public BaseResult<List<EnumVO>> getLawCaseSourceList() {
        return new BaseResult().content(lawProductService.getLawCaseSourceList());
    }

    @GetMapping(value = "/getLawTypeList")
    @ApiOperation(value = "获取案件类型列表")
    public BaseResult<List<EnumVO>> getLawTypeList() {
        return new BaseResult().content(lawProductService.getLawTypeList());
    }

    @GetMapping(value = "/getPlotSeverityList")
    @ApiOperation(value = "获取情节严重类型列表")
    public BaseResult<List<EnumVO>> getPlotSeverityList() {
        return new BaseResult().content(lawProductService.getPlotSeverityList());
    }

    @GetMapping(value = "/getProduceReasonList")
    @ApiOperation(value = "生产获取生产案由列表")
    public BaseResult getProduceReasonList() {
        return new BaseResult().content(lawProductService.getProduceReasonList());
    }

    @GetMapping(value = "/getPunishmentTypeList")
    @ApiOperation(value = "获取处罚人类型列表")
    public BaseResult<List<EnumVO>> getPunishmentTypeList() {
        return new BaseResult().content(lawProductService.getPunishmentTypeList());
    }

    @GetMapping(value = "/getRecordStatusList")
    @ApiOperation(value = "获取执法记录状态列表")
    public BaseResult<List<EnumVO>> getRecordStatusList() {
        return new BaseResult().content(lawProductService.getRecordStatusList());
    }

    @GetMapping(value = "/getSafeReasonList")
    @ApiOperation(value = "安全获取生产案由列表")
    public BaseResult<List<EnumVO>> getSafeReasonList() {
        return new BaseResult().content(SafeReasonEnum.getReasons());
    }

    @GetMapping(value = "/getShipCaseCardList")
    @ApiOperation(value = "获取船牌悬挂类型列表")
    public BaseResult<List<EnumVO>> getShipCaseCardList() {
        return new BaseResult().content(lawProductService.getShipCaseCardList());
    }

    @GetMapping(value = "/getShipCaseList")
    @ApiOperation(value = "获取船涂写情况列表")
    public BaseResult<List<EnumVO>> getShipCaseList() {
        return new BaseResult().content(lawProductService.getShipCaseList());
    }

    @GetMapping(value = "/getShipRatedTypeList")
    @ApiOperation(value = "获取核定作业类型列表")
    public BaseResult<List<EnumVO>> getShipRatedTypeList() {
        return new BaseResult().content(lawProductService.getShipRatedTypeList());
    }

    @GetMapping(value = "/getShipRealTypeList")
    @ApiOperation(value = "获取实际作业类型列表")
    public BaseResult<List<EnumVO>> getShipRealTypeList() {
        return new BaseResult().content(lawProductService.getShipRealTypeList());
    }

    @GetMapping(value = "/getShipStatusList")
    @ApiOperation(value = "获取渔船状态列表")
    public BaseResult<List<EnumVO>> getShipStatusList() {
        return new BaseResult().content(lawProductService.getShipStatusList());
    }

    @GetMapping(value = "/getOperatorInfoList")
    @ApiOperation(value = "获取执法人员列表")
    public BaseResult<List<PersonVO>> getOperatorInfoList() {
        return new BaseResult().content(lawProductService.getOperatorInfoList());
    }

}
