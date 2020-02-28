package com.seven.gwc.modular.address_book.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : group实体
 *
 * @author : SHQ
 * @date : 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chat_friend")
public class FriendEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 人员id */
    private String person1Id;

    /** 人员id */
    private String person2Id;

}
