package com.seven.gwc.modular.electronic_data.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "法律法规列表")
public class LawsRegulationVO {
    private String id;

    private String fileName;

    private String filePath;
}
