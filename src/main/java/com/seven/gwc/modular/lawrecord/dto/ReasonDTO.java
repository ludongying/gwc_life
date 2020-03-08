package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : zzl
 * @Date: 2020-03-05
 * @description :
 */
@Data
public class ReasonDTO implements Serializable {
   protected Integer mainReason;
   protected List<Integer> secondReason;

   public ReasonDTO(LawRecordEntity lawRecordEntity) {
       this.mainReason=lawRecordEntity.getMainReason();
       String str = lawRecordEntity.getSecondReason();
       if(Objects.nonNull(str) && !str.trim().isEmpty()){
          this.secondReason=Stream.of(str.split(",")).map(String::trim).filter(s->!s.isEmpty()).map(Integer::parseInt).collect(Collectors.toList());
       }

   }
}
