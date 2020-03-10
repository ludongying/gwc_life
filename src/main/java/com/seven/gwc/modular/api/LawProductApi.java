package com.seven.gwc.modular.api;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.service.LawProductService;
import com.seven.gwc.modular.lawrecord.vo.AppAgencyVO;
import com.seven.gwc.modular.lawrecord.vo.AppInquireVO;
import com.seven.gwc.modular.lawrecord.vo.AppOperatorVO;
import com.seven.gwc.modular.lawrecord.vo.EnumVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "执法生产")
@RequestMapping("gwcApi/lawProduct")
public class LawProductApi {
    @Autowired
    private LawProductService lawProductService;

    @PostMapping(value = "/addLawProduct")
    @ApiOperation(value = "新增新案件-生产")
    public BaseResult addLawProduct(HttpServletRequest request, AppAgencyVO appAgencyVO, AppOperatorVO appOperatorVO, AppInquireVO appInquireVO){
        String userId = request.getAttribute("userId").toString();
        return lawProductService.addLawProduct(userId, appAgencyVO, appOperatorVO, appInquireVO);
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



}
