package com.seven.gwc.modular.lawrecord.vo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author : zzl
 * @Date: 2020-03-05
 * @description :
 */
@Data
public class ReasonVO implements Serializable {
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
     * 主案由
     */
    private Integer mainReason;
    /**
     * 副案由
     */
    private String secondReason;

    /**
     * 格式转换
     * @return
     */
    public String getSecondReasonStr(){
        if(Objects.nonNull(this.secondReason) && !secondReason.trim().isEmpty()){
            List<String> list = JSON.parseArray(secondReason, String.class);
            return list.stream().map(String::trim).filter(s->!s.isEmpty()).collect(Collectors.joining(",",",",","));
        }
        return "";
    }


}
