package com.seven.gwc.modular.lawrecord.data.instrument.dto;

import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.FilePathDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.tomcat.jni.File;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public FilePathDTO(Integer code, String path,String generate) {
        super(code, path,generate);
        this.filePath="";
    }

    public FilePathDTO(Integer code, String path) {
        super(code, path);
        this.filePath="";
    }

    public static FilePathDTO getInstance(FilePathDO filePathDO){
        FilePathDTO dto=new FilePathDTO();
        BeanUtils.copyProperties(filePathDO,dto);
        String filePath = dto.getPath();
        String name = filePath.substring(filePath.lastIndexOf(FileUtils.file_sep) + 1);
        dto.setName(name);
        return dto;
    }

    public static void mergeFilePath(List<FilePathDTO> list,List<FilePathDO> dos){
        if(Objects.nonNull(list) && !list.isEmpty() && Objects.nonNull(dos) && !dos.isEmpty()){
            Map<Integer, List<FilePathDTO>> map = list.stream().collect(Collectors.groupingBy(FilePathDTO::getCode));
            for (FilePathDO dto : dos) {
                Integer c = dto.getCode();
                List<FilePathDTO> filePathDTOS = map.get(c);
                if(Objects.nonNull(filePathDTOS) && !filePathDTOS.isEmpty()){
                    if(filePathDTOS.size()==1){
                        FilePathDTO filePathDTO = filePathDTOS.get(0);
                        filePathDTO.setFilePath(dto.getPath());
                    }else{
                        String t = dto.getGenerateName();
                        for (FilePathDTO filePathDTO : filePathDTOS) {
                            if (t.equals(filePathDTO.getGenerateName())) {
                                filePathDTO.setFilePath(dto.getPath());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }











}
