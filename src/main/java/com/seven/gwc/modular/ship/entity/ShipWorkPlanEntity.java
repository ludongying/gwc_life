package com.seven.gwc.modular.ship.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : 工作计划实体
 *
 * @author : 李晓晖
 * @date : 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_work_plan")
public class ShipWorkPlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 执法船id */
    private String shipId;

    /** 开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /** 结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /** 工作内容 */
    private String workContent;

    /** 工作人员 */
    private String personIds;

    /** 类型（枚举）颜色 */
    private Integer planType;

}
