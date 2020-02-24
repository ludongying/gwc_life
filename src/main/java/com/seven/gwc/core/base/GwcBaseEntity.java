package com.seven.gwc.core.base;

import lombok.Data;
import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author : zzl
 * description : 渔政船实体父类
 */
@Data
public class GwcBaseEntity extends BaseEntity{

    /**
     * 更新时间
     */
    protected Date updateDate;
    /**
     * 更新人
     */
    protected String updatePerson;
    /**
     * 创建时间
     */
    protected Date createDate;
    /**
     * 创建人
     */
    protected String createPerson;

    /**
     * 删除标记 false已经删除 true未删除
     */
    protected Boolean deleteFlag;

    @PostConstruct
    public void init(){
       this.createDate=new Date();
       this.deleteFlag=false;
    }




}
