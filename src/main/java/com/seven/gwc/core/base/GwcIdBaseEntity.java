package com.seven.gwc.core.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author : zzl
 * @Date: 2020-02-29
 * @description :
 */
@NoArgsConstructor
@Data
public class GwcIdBaseEntity extends GwcBaseEntity {

    @TableId(type= IdType.UUID)
    private String id;

    public void init(String userId){
         super.init(this.id,userId);
    }



}
