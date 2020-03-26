package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-26
 * @description :
 */
@Data
@AllArgsConstructor
public class FilePathDO implements Comparable<FilePathDO>,Serializable {

  private Integer code;

  private String path;

  private Long timeStamp;

  private String name;

  public FilePathDO(Integer code,String path){
      this.code=code;
      this.path=path;
      this.timeStamp=System.currentTimeMillis();
  }

  public static String  getJson(List<FilePathDO> list){
      return JSON.toJSONString(list);
  }

  public static List<FilePathDO> getFiles(String str){
      if(Objects.nonNull(str) && !str.trim().isEmpty()){
          List<FilePathDO> filePathDOS = JSON.parseArray(str, FilePathDO.class);
          filePathDOS.sort(FilePathDO::compareTo);
          return filePathDOS;
      }
      return null;
   }

    @Override
    public int compareTo(FilePathDO o) {
        if(this.getCode()>o.getCode()){
            return 1;
        }else if(this.getCode().equals(o.getCode())){
            return  (int)(this.getTimeStamp()-o.getTimeStamp());
        }
        return -1;
    }
}
