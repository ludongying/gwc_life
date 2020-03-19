package com.seven.gwc.modular.lawrecord.data.word.config;

import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Configuration
public class InstrumentContrast {

   private Map<String,String> map;

   @Value("${FILE_UPLOAD_PATH_FILE}")
   private String file_upload_path;

   private String savePath;

   private final String path=System.getProperty("user.dir")+"/src/main/resources/lawrecord/config.xls";

   public InstrumentContrast(){
      this.map=new HashMap<>();
      readExcel();
   }

   private  void readExcel() {
      // 读取excel文件
      File f = new File(path);
      try {
         // 创建文件流
         FileInputStream is = new FileInputStream(f);
         // 加载文件流
         HSSFWorkbook wbs = new HSSFWorkbook(is);
         // 读取第一个Sheet
         HSSFSheet childSheet = wbs.getSheetAt(0);
         // 遍历行
         for (int i = 1; i < childSheet.getLastRowNum() + 1; i++) {
            // 获得行对象
            HSSFRow row = childSheet.getRow(i);
            if (null != row) {
               HSSFCell value = row.getCell(1);
               HSSFCell name = row.getCell(2);
               if(Objects.nonNull(name)){
                  String val=Objects.nonNull(value)?value.toString():"";
                  map.put("${"+name.toString()+"}",val);
               }
            }
         }
         is.close();
      } catch (Exception e) {
         log.error(">>>>路径异常:"+path);
      }
   }

   public Map<String, String> getMap() {
      return map;
   }

   public String getSavePath() {
      return file_upload_path+ FileUtils.file_sep +"instrument";
   }

   public static void main(String[] args) {
      InstrumentContrast instrumentConfig = new InstrumentContrast();
      Map<String, String> map = instrumentConfig.getMap();
      System.out.println(map);

   }

}
