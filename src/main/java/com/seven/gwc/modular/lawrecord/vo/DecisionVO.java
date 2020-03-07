package com.seven.gwc.modular.lawrecord.vo;

import com.seven.gwc.modular.lawrecord.entity.DecisionEntity;
import lombok.Data;

/**
 * @author : zzl
 * @Date: 2020-03-06
 * @description :
 */
@Data
public class DecisionVO extends DecisionEntity {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 案件类型
     */
    private Integer lawType;

    /**
     * 设置登录用户
     * @param userId
     */
    public void setUserId(String userId){
        this.userId=userId;
        this.init(this.getId(),userId);
    }

}
