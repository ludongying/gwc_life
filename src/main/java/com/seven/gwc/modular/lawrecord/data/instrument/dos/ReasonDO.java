package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.enums.InstrumentEnum;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.enums.ProduceReasonEnum;
import com.seven.gwc.modular.lawrecord.enums.SafeReasonEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 案由 模板数据
 * @author zzl
 */
@Data
@Accessors(chain = true)
public class ReasonDO extends BaseDO {

    /**
     * 案由(违规类型)
     */
    private String Type_Violation;

    /**
     * 根据案由产生数据
     */
    private String Laws_Violation2;
    private String Laws_Basis2;
    private String Laws_Violation;
    private String Laws_Basis;

    public ReasonDO(LawRecordEntity entity){
        Integer lawType = entity.getLawType();
        this.setLaws_Violation2("").setLaws_Basis2("").setLaws_Violation("").setLaws_Basis("");
        if(LawTypeEnum.PRODUCE.getCode().equals(lawType)){
            ProduceReasonEnum produce = ProduceReasonEnum.findByCode(entity.getMainReason());
            this.setType_Violation(Objects.nonNull(produce)?produce.getContent():"");
            initProduceReason(entity.getMainReason(),entity.getSecondReason());
        }else if(LawTypeEnum.SAFE.getCode().equals(lawType)){
            SafeReasonEnum safe = SafeReasonEnum.findByCode(entity.getMainReason());
            this.setType_Violation(Objects.nonNull(safe)?safe.getContent():"");
        }
    }


    private void initProduceReason(Integer main,String second){
        this.Laws_Violation2="";
        this.Laws_Basis2="";
        this.Laws_Violation="";
        this.Laws_Basis="";
         if(Objects.nonNull(main)){
            List<Integer> res=new ArrayList<>();
            res.add(main);
            if(Objects.nonNull(second)){
                List<Integer> list = Stream.of(second.split(",")).filter(Objects::nonNull).filter(s->!s.trim().isEmpty()).map(Integer::parseInt).collect(Collectors.toList());
                res.addAll(list);
            }
             LinkedHashMap<Integer, List<ProduceReasonEnum>> map = res.stream().filter(Objects::nonNull).sorted().map(ProduceReasonEnum::findByCode).collect(Collectors.groupingBy(ProduceReasonEnum::getLaw, LinkedHashMap::new, Collectors.toList()));
             map.forEach((k,v)->{
                 ReasonLawDO reasonLawDO = ReasonLawDO.getByLaw(k);
                 if(Objects.nonNull(reasonLawDO)){
                     String symbol="";
                     if(this.getLaws_Basis().length()>0){
                         symbol="、";
                     }
                     this.Laws_Violation2+=symbol+reasonLawDO.getLaws_Violation2();
                     this.Laws_Basis2+=symbol+reasonLawDO.getLaws_Basis2();
                     this.Laws_Violation+=symbol+reasonLawDO.getLaws_Violation();
                     this.Laws_Basis+=symbol+reasonLawDO.getLaws_Basis();
                 }
             });
         }
    }
}
