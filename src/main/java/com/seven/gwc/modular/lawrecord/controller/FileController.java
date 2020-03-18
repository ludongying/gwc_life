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


/*
    @Autowired
    private DocumentConverter converter;

    @RequestMapping("toPdfFile")
    public String toPdfFile(HttpServletResponse response) {
        File file = new File("D:/word/4.docx");//需要转换的文件
        try {
            File newFile = new File("D:/word");//转换之后文件生成的地址
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            //文件转化
            converter.convert(file).to(new File("D:/word/4.pdf")).execute();
            //使用response,将pdf文件以流的方式发送的前段
            ServletOutputStream outputStream = response.getOutputStream();
            InputStream in = new FileInputStream(new File("D:/word/4.pdf"));// 读取文件
            // copy文件
            int i = IOUtils.copy(in, outputStream);
            System.out.println(i);
            in.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "This is to pdf";
    }
*/



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



}
