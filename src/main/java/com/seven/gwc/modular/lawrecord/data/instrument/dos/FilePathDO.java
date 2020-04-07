package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : zzl
 * @Date: 2020-03-26
 * @description :
 */
@Data
@NoArgsConstructor
public class FilePathDO implements Comparable<FilePathDO>,Serializable {

  private Integer code;

  private String path;

  private String  generateName;


  public FilePathDO(Integer code,String path,String generateName){
      this.code=code;
      this.path=path;
      this.generateName=generateName;
  }

    public FilePathDO(Integer code,String path){
        this.code=code;
        this.path=path;
        this.generateName="1";
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
      return new ArrayList<>();
   }

    public static List<FilePathDO>  addFilePath(List<FilePathDO> list, FilePathDO d){
        Map<Integer, List<FilePathDO>> map = list.stream().collect(Collectors.groupingBy(FilePathDO::getCode));
        Integer c = d.getCode();
        List<FilePathDO> filePathDOS = map.get(c);
        if(Objects.isNull(filePathDOS)||filePathDOS.isEmpty()){
            list.add(d);
        }else if(filePathDOS.size()==1){
            list.remove(filePathDOS);
            list.add(d);
        }else{
            String t = d.getGenerateName();
            FilePathDO delDto=null;
            for (FilePathDO filePathDO : filePathDOS) {
                if(t.equals(filePathDO.getGenerateName())){
                    delDto=filePathDO;
                    break;
                }
            }
            if(Objects.nonNull(delDto)){
                list.remove(delDto);
            }
            list.add(d);
        }
        return list;
    }


    @Override
    public int compareTo(FilePathDO o) {
        if(this.getCode()>o.getCode()){
            return 1;
        }
        return -1;
    }
}
