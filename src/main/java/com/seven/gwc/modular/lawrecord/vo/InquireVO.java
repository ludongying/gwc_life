package com.seven.gwc.modular.lawrecord.vo;

import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import lombok.Data;

/**
 * @author : zzl
 * @Date: 2020-03-02
 * @description :生产 安全 询问笔录
 */
@Data
public class InquireVO extends InquireEntity {

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
