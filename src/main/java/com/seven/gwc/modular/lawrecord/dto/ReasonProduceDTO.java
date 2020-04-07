package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.enums.ProduceReasonEnum;
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
public class ReasonProduceDTO extends ReasonDTO{


    private ProduceReasonEnum  main;

    private List<ProduceReasonEnum> second;


    public ReasonProduceDTO(LawRecordEntity lawRecordEntity) {
        super(lawRecordEntity);
        this.main=ProduceReasonEnum.findByCode(this.getMainReason());
        this.second=new ArrayList<>();
        List<Integer> secondReason = this.getSecondReason();
        if(Objects.nonNull(secondReason) && !secondReason.isEmpty()){
            secondReason.stream().map(ProduceReasonEnum::findByCode).filter(Objects::nonNull).forEach(second::add);
        }
    }
}
