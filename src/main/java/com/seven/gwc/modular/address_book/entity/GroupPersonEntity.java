package com.seven.gwc.modular.address_book.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : groupPerson实体
 *
 * @author : SHQ
 * @date : 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chat_group_person")
public class GroupPersonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 群组id */
    private String groupId;

    /** 人员id */
    private String personId;

    /** 组内名称 */
    private String personNikeName;

}
