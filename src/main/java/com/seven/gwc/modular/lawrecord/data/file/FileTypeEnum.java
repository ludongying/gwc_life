package com.seven.gwc.modular.lawrecord.data.file;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author : zzl
 * @Date: 2020-03-04
 * @description :
 */
@Getter
public enum FileTypeEnum {

     image(1,"图片", Arrays.asList(".jpg",".png",".jpeg")),

     video(2,"视频",Arrays.asList(".mp4",".avi")),

     pdf(3,"pdf",Arrays.asList(".pdf")),

     other(99,"其他");

    Integer code;
    String message;
    List<String> suffix;

    FileTypeEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    FileTypeEnum(Integer code,String message,List<String> suffix){
        this.code=code;
        this.message=message;
        this.suffix=suffix;
    }
}
