package com.seven.gwc.modular.path.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : 船的轨迹信息实体
 *
 * @author: QQC
 * @date: 2020-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_fish_ship_track")
public class FishShipTrackEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ID
    private String id;

    // 鱼船id
    private String fishShipId;

    // 渔船编码（渔船可能在数据库里没有）
    private String shipCode;

    // 轨迹点
    private String track;

    // 存点时间(yyyy-MM-dd)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordDate;

    // AIS/北斗
    private String singnalType;

    // 开始时间(锁定)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    // 结束时间（解锁）
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
