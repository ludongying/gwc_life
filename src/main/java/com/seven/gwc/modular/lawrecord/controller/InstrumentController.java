package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.modular.lawrecord.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : zzl
 * @Date: 2020-03-23
 * @description : 文书控制器
 */
@Controller
@RequestMapping("lawRecord/instrument")
public class InstrumentController {

    private static String PREFIX = "/modular/lawRecord/";

    @Autowired
    private InstrumentService instrumentService;
    /**
     * 跳转到文书
     */
    @RequestMapping("")
    public String instrument(String id) {
        instrumentService.generateSystemInstrument(id);
        return PREFIX + "instrument";
    }

    @RequestMapping("list")
    @ResponseBody
    public BaseResultPage getInstrument(String id){
        BaseResultPage res=new BaseResultPage();
        res.setCode(0);
        res.setData(instrumentService.getInstrument(id));
        return res;
    }

    @RequestMapping("uploadInstrument")
    @ResponseBody
    public BaseResult uploadInstrument(String id,Integer code,String generateName,MultipartFile file){
         return instrumentService.uploadInstrument(id, code,generateName,file);
    }

    @RequestMapping("generate")
    @ResponseBody
    public BaseResult generateCase(String id){
      return  instrumentService.generateCase(id);
    }



    @RequestMapping("generatePdf")
    @ResponseBody
    public BaseResult generateCasePdf(String path){
        return  instrumentService.generatePdf(path);
    }


    @RequestMapping("preview")
    public void previewPdf(String path, HttpServletResponse response){
         instrumentService.previewPdf(path,response);
    }

    @RequestMapping("previewPage")
    public String previewPage(String path, Model model){
        model.addAttribute("path",path);
        return PREFIX + "pdfPreview";
    }





}
