package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.core.util.DateTimeUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileData;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.lawrecord.entity.EvidenceEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author : zzl
 * @Date: 2020-03-04
 * @description :
 */
@Data
public class EvidenceDTO implements Serializable {
    private String id;
    private String evidenceContent;
    private String evidenceTime;
    private List<FileData> path;

    public EvidenceDTO(EvidenceEntity entity){
        this.id=entity.getId();
        this.evidenceContent=entity.getEvidenceContent();
        this.evidenceTime= DateTimeUtil.parse2String(entity.getEvidenceDate(),"yyyy-MM-dd HH:mm:ss");

    }

}
