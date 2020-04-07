package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : zzl
 * @Date: 2020-03-03
 * @description : 文件控制器
 */
@Controller
@RequestMapping("file")
public class FileController {


    @Autowired
    private FileManager fileManager;


    /**
     * 上传文件
     * @param file
     * @return
     */
    @RequestMapping("uploadFile")
    @ResponseBody
    public BaseResult uploadFile(@RequestParam("file") MultipartFile file){
       return fileManager.uploadFile(file);
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    @RequestMapping("deleteFile")
    @ResponseBody
    public BaseResult deleteFile(String path){
       return fileManager.deleteFile(path);
    }

    /**
     * 下载文件
     * @param path
     */
    @RequestMapping("downFile")
    public void downFile(String path, HttpServletResponse response){
        fileManager.download(path,response);
    }

    /**
     * 下载模板
     * @param name
     */
    @RequestMapping("downTemplate")
    public void downTemplate(String name, HttpServletResponse response){
        fileManager.downloadTemplate(name,response);
    }

}
