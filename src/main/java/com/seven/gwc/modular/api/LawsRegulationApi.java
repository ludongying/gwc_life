package com.seven.gwc.modular.api;

import cn.hutool.core.io.resource.Resource;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.util.FileUtil;
import com.seven.gwc.modular.electronic_data.service.RegulationSafeService;
import com.seven.gwc.modular.electronic_data.vo.LawsRegulationVO;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private FileManager fileManager;

    @GetMapping(value = "/getLawsRegulationList")
    @ApiOperation(value = "获取法律法规列表")
    public BaseResult<List<LawsRegulationVO>> getLawsRegulationList() {
        List<LawsRegulationVO> listVO = regulationSafeService.getLawsRegulationList();
        return new BaseResult().content(listVO);
    }

    @GetMapping("/previewFile")
    @ApiOperation(value = "预览文件")
    public ResponseEntity<Resource> previewFile(HttpServletResponse response,
                                                @ApiParam(name = "fileName", value = "文件名") String fileName,
                                                @ApiParam(name = "filePath", value = "文件路径") String filePath) {
        return FileUtil.previewFile(fileName + ".pdf", filePath, response);
    }
}
