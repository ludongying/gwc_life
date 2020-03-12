package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.enums.ProduceReasonEnum;
import com.seven.gwc.modular.lawrecord.enums.SafeReasonEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-11
 * @description :
 */
@Data
public class ReasonSafeDTO extends ReasonDTO{


    private SafeReasonEnum main;

    private List<SafeReasonEnum> second;

    public ReasonSafeDTO(LawRecordEntity lawRecordEntity) {
        super(lawRecordEntity);
        this.main=SafeReasonEnum.findByCode(this.getMainReason());
        this.second=new ArrayList<>();
        List<Integer> secondReason = this.getSecondReason();
        if(Objects.nonNull(secondReason) && !secondReason.isEmpty()){
            secondReason.stream().map(SafeReasonEnum::findByCode).filter(Objects::nonNull).forEach(second::add);
        }
    }
}
