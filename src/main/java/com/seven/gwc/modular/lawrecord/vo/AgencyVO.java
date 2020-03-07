package com.seven.gwc.modular.lawrecord.vo;

import com.seven.gwc.modular.lawrecord.entity.AgencyEntity;
import lombok.Data;


/**
 * @author : zzl
 * @Date: 2020-02-29
 * @description :
 */
@Data
public class AgencyVO extends AgencyEntity {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 案件类型
     */
    private Integer lawType;

    /**
     * 执法人员
     */
    private String operators;


    /**
     * 设置登录用户
     * @param userId
     */
    public void setUserId(String userId){
       this.userId=userId;
       this.init(this.getId(),userId);
    }










}
