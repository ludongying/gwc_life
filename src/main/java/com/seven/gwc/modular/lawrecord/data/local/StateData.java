package com.seven.gwc.modular.lawrecord.data.local;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zzl
 * @Date: 2020-02-27
 * @description :
 */
@Data
@NoArgsConstructor
public class StateData extends BaseData {
    private List<CityData> cityDataList;

    public StateData(Node node){
        super(node);
        this.cityDataList=new ArrayList<>();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String nodeName = item.getNodeName();
            if("City".equals(nodeName)){
                this.cityDataList.add(new CityData(item));
            }
        }
    }
}
