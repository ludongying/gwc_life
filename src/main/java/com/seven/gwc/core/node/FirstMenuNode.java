package com.seven.gwc.core.node;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class FirstMenuNode implements Serializable {

    // 菜单code
    private String code;

    // 菜单名称
    private String name;

}
