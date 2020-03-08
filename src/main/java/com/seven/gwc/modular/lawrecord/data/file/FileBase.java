package com.seven.gwc.modular.lawrecord.data.file;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author : zzl
 * @Date: 2020-03-03
 * @description :
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class FileBase implements Serializable {

    /**
     * 物理地址
     */
    protected String path;
    /**
     * 预览地址
     */
    protected String url;

    /**
     * 文件类型
     */
    private Integer type;


    public FileBase(String path) {
        this.path = path;
    }
}
