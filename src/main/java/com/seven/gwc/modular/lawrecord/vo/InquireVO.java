package com.seven.gwc.modular.lawrecord.vo;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import lombok.Data;

import java.util.List;
import java.util.Objects;

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
     * 补录文书
     */
    private String inquireContent;

    /**
     * 设置登录用户
     * @param userId
     */
    public void setUserId(String userId){
        this.userId=userId;
        this.init(this.getId(),userId);
    }

    /**
     * 获取补录信息
     * @return
     */
    public List<InquireSupplementVO> listInquire(){
        if(Objects.nonNull(this.inquireContent) && !inquireContent.trim().isEmpty()){
            List<InquireSupplementVO> inquireEntities = JSON.parseArray(this.inquireContent, InquireSupplementVO.class);
            if(Objects.nonNull(inquireEntities) && !inquireEntities.isEmpty()){
                inquireEntities.forEach(inquireEntity -> {
                    inquireEntity.init(inquireEntity.getId(),userId);
                    String id = inquireEntity.getId();
                    if(Objects.isNull(id) || id.trim().isEmpty()){
                       inquireEntity.setId(UUID.fastUUID().toString(Boolean.TRUE));
                    }
                    inquireEntity.setContent(inquireEntity.getId());
            });
                return inquireEntities;
            }
        }
        return null;
    }



}
