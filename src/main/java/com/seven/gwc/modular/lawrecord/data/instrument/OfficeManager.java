package com.seven.gwc.modular.lawrecord.data.instrument;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description :
 */
@Component
@Slf4j
public class OfficeManager {


//    @Autowired
//    private DocumentConverter converter;
//
//
//    public String  generatePdf(String src){
//        File file = new File(src);
//        int position = src.lastIndexOf(".");
//        String exprotPath=src.substring(0,position)+".pdf";
//        FileUtils.generateDir(file.getParent());
//        try {
//            converter.convert(file).to(new File(exprotPath)).execute();
//        } catch (OfficeException e) {
//            log.error("文书word转pdf失败");
//            return null;
//        }
//        return exprotPath;
//    }

    public void preview(String src, HttpServletResponse response){
        //需要转换的文件
        try {
            //使用response,将pdf文件以流的方式发送的前段
            ServletOutputStream outputStream = response.getOutputStream();
            // 读取文件
            InputStream in = new FileInputStream(new File(src));
            // copy文件
            IOUtils.copy(in, outputStream);
            in.close();
            outputStream.close();
        } catch (Exception e) {
            log.error("pdf文件路径异常");
        }
    }


}
