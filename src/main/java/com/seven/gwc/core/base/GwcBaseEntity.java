package com.seven.gwc.core.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Objects;

/**
 * @author : zzl
 * @description : 渔政船实体父类
 */
@NoArgsConstructor
@Data
public class GwcBaseEntity extends BaseEntity{

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected Date updateDate;
    /**
     * 更新人ID
     */
    protected String updatePerson;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected Date createDate;
    /**
     * 创建人ID
     */
    protected String createPerson;

    /**
     * 删除标记 false已经删除 true未删除
     */
    protected Boolean deleteFlag;


    public void init(String id,String userId){
        if(Objects.isNull(id) || id.trim().isEmpty()){
            this.createPerson=userId;
            this.createDate=new Date();
        }else{
            this.updatePerson=userId;
            this.updateDate=new Date();
        }
        this.deleteFlag=true;
        this.synFlag=false;
    }




}
