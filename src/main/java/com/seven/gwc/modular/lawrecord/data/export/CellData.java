package com.seven.gwc.modular.lawrecord.data.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zzl
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CellData implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;



}
