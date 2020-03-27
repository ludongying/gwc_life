package com.seven.gwc.modular.lawrecord.data.instrument.dto;

import com.seven.gwc.modular.lawrecord.data.instrument.dos.FilePathDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author zzl
 *
 */


@ToString
@Data
@NoArgsConstructor
public class FilePathDTO extends FilePathDO {

    private String name;
    private String filePath;

    public FilePathDTO(Integer code, String path) {
        super(code, path);
        this.filePath="";
    }










}
