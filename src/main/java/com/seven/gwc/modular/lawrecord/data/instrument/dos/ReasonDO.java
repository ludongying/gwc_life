package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.enums.ProduceReasonEnum;
import com.seven.gwc.modular.lawrecord.enums.SafeReasonEnum;
import lombok.Data;

import java.util.Objects;

/**
 * @Description 案由 模板数据
 * @author zzl
 */
@Data
public class ReasonDO extends BaseDO {

    /**
     * 案由(违规类型)
     */
    private String Type_Violation;

    public ReasonDO(LawRecordEntity entity){
        Integer lawType = entity.getLawType();
        if(LawTypeEnum.PRODUCE.getCode().equals(lawType)){
            ProduceReasonEnum produce = ProduceReasonEnum.findByCode(entity.getMainReason());
            this.setType_Violation(Objects.nonNull(produce)?produce.getContent():"");
        }else if(LawTypeEnum.SAFE.getCode().equals(lawType)){
            SafeReasonEnum safe = SafeReasonEnum.findByCode(entity.getMainReason());
            this.setType_Violation(Objects.nonNull(safe)?safe.getContent():"");
        }
    }
}
