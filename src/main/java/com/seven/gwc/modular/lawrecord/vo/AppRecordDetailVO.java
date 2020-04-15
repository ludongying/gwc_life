package com.seven.gwc.modular.lawrecord.vo;

import com.seven.gwc.modular.lawrecord.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "案件详情")
public class AppRecordDetailVO {
    /** 案件类型 */
    @ApiModelProperty(value = "案件类型")
    private Integer lawType;

    /** 办案机关 */
    @ApiModelProperty(value = "办案机关")
    private AgencyDTO agencyDTO;

    /** 证据 */
    @ApiModelProperty(value = "证据")
    private List<EvidenceDTO> evidenceDTOList;

    /** 询问笔录-生产 */
    @ApiModelProperty(value = "询问笔录-生产")
    private List<InquireSupplementDTO> inquireSupplementDTOList;

    /** 勘验笔录 */
    @ApiModelProperty(value = "勘验笔录")
    private InquisitionDTO inquisitionDTO;

    /** 案由-生产 */
    @ApiModelProperty(value = "案由-生产")
    private ReasonProduceDTO reasonProduceDTO;

    /** 决定实体-生产 */
    @ApiModelProperty(value = "决定实体-生产")
    private DecisionDTO decisionDTO;

    /** 询问笔录-安全 */
    @ApiModelProperty(value = "询问笔录-安全")
    private InquireSafeDTO inquireSafeDTO;

    /** 决定实体-安全 */
    @ApiModelProperty(value = "决定实体-安全")
    private DecisionSafeDTO decisionSafeDTO;

    /** 案由-安全 */
    @ApiModelProperty(value = "案由-安全")
    private ReasonSafeDTO reasonSafeDTO;
}
