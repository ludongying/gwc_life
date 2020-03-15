package com.seven.gwc.modular.api;

import com.seven.gwc.core.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@Api(tags = "预览文件")
@RequestMapping("gwcApi/reviewFile")
public class PreviewFileApi {

    @RequestMapping("/previewFile")
    @ApiOperation(value = "预览文件")
    public String previewFile(@ApiParam(name = "filePath", value = "文件路径") String filePath, Model model) {
        model.addAttribute("name", filePath);
        return "/modular/system/pdfPreview";
    }

    @GetMapping("/previewFileDownLoad")
    @ResponseBody
    @ApiOperation(value = "预览文件")
    public ResponseEntity<Resource> previewFile(HttpServletResponse response,
                                                @ApiParam(name = "filePath", value = "文件路径") String filePath) {
        return FileUtil.previewFile(filePath, response);
    }
}
