package com.seven.gwc.modular.munition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : 库存管理实体
 *
 * @author : LDY
 * @date : 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_munition_store")
public class MunitionStoreEntity extends GwcBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 库存编码 */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 物资编码 */
    private String munitionId;

    /** 总数量 */
    private Integer totalNum;

//    /** 删除标记 */
//    private Boolean deleteFlag;
//
//    /** 同步标记 */
//    private Boolean synFlag;
//
//    /** 创建人 */
//    private String createPerson;
//
//    /** 创建时间 */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createDate;
//
//    /** 修改人 */
//    private String updatePerson;
//
//    /** 修改时间 */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date updateDate;

}
