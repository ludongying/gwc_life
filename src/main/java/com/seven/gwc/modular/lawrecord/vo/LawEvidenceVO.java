package com.seven.gwc.modular.lawrecord.vo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-04
 * @description :
 */
@Data
public class LawEvidenceVO implements Serializable {

    /**
     * 记录id
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 案件类型
     */
    private Integer lawType;

    /**
     * 表单内所有内容
     */
    private String content;


    public List<EvidenceVO> getEvidences(){
      if(Objects.nonNull(content) && !content.trim().isEmpty()){
          List<EvidenceVO> evidences = JSON.parseArray(content, EvidenceVO.class);
          if(Objects.nonNull(evidences) && !evidences.isEmpty()){
              evidences.stream().forEach(e->{
                  e.setUserId(userId);
                  e.setRecordId(id);
                  e.parseTime();
                  if(Objects.nonNull(e.getId()) && e.getId().trim().isEmpty()){ e.setId(null);}
              });
              return evidences;
          }
      }
      return null;
    }

}
