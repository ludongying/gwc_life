package com.seven.gwc.modular.api;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.electronic_data.service.RegulationSafeService;
import com.seven.gwc.modular.electronic_data.vo.LawsRegulationVO;
import com.seven.gwc.modular.electronic_data.vo.LawsTypeVO;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import com.seven.gwc.modular.system.entity.DictEntity;
import com.seven.gwc.modular.system.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 法律法规控制器
 *
 * @author : SHQ
 * @date : 2020-03-09
 */

@RestController
@Api(tags = "法律法规")
@RequestMapping("gwcApi/lawsRegulation")
public class LawsRegulationApi {
    @Autowired
    private RegulationSafeService regulationSafeService;

    @GetMapping(value = "/getLawsTypeList")
    @ApiOperation(value = "获取法律法规类型列表")
    public BaseResult<List<LawsTypeVO>> getLawsTypeList() {
        List<LawsTypeVO> listVO = regulationSafeService.getLawsTypeVOList();
        return new BaseResult().content(listVO);
    }

    @GetMapping(value = "/getListByLawRegularId")
    @ApiOperation(value = "获取法律法规列表")
    public BaseResult<List<LawsRegulationVO>> getListByLawRegularId(@ApiParam(name = "lawRegularId", required = true, value = "法律法规类型ID")String lawRegularId) {
        List<LawsRegulationVO> listVO = regulationSafeService.getListByLawRegularId(lawRegularId);
        return new BaseResult().content(listVO);
    }
}
