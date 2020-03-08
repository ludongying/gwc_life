package com.seven.gwc.modular.lawrecord.data.file;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author : zzl
 * @Date: 2020-03-03
 * @description :
 */
@NoArgsConstructor
@Data
public class FileData extends FileBase{

    /**
     * 文件名称
     */
    protected String name;
    /**
     * 文件存储名称
     */
    protected String realName;

    /**
     * 文件尺寸
     */
    private String size;
    /**
     * 文件字节
     */
    private Long length;


    public FileData(String realPath){
        File file=new File(realPath);
        this.realName=file.getName();
        this.name=realName.substring(13);
        this.path=realPath;
        this.length=file.length();
    }



}
