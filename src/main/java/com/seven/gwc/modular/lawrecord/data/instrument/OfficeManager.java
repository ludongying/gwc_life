package com.seven.gwc.modular.lawrecord.data.instrument;

import org.apache.commons.io.IOUtils;
import org.jodconverter.DocumentConverter;

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
//@Component
public class OfficeManager {


//    @Autowired
    private DocumentConverter converter;



    public void preview(String src, HttpServletResponse response){
        //需要转换的文件
        File file = new File(src);
        try {
            //转换之后文件生成的地址
            File newFile = new File("D:/word");
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            //文件转化
            converter.convert(file).to(new File("D:/word/4.pdf")).execute();
            //使用response,将pdf文件以流的方式发送的前段
            ServletOutputStream outputStream = response.getOutputStream();
            // 读取文件
            InputStream in = new FileInputStream(new File("D:/word/4.pdf"));
            // copy文件
            int i = IOUtils.copy(in, outputStream);
            System.out.println(i);
            in.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
