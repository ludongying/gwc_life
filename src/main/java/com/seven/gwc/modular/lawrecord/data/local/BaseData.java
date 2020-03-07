package com.seven.gwc.modular.lawrecord.data.local;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.Serializable;

/**
 * @author : zzl
 * @Date: 2020-02-27
 * @description :
 */
@Data
@NoArgsConstructor
public class BaseData implements Serializable {
    protected String name;
    protected String code;

    public BaseData(Node node){
        NamedNodeMap attributes = node.getAttributes();
        this.name = attributes.getNamedItem("Name").getTextContent();
        this.code = attributes.getNamedItem("Code").getTextContent();
    }
}
