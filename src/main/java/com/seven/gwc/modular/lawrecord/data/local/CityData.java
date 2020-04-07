package com.seven.gwc.modular.lawrecord.data.local;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Node;
import java.util.List;

/**
 * @author : zzl
 * @Date: 2020-02-27
 * @description :如果添加 city以下区域 填入以下代码
 * {@code
 *   regionDataList=new ArrayList<>();
 *         NodeList childNodes = node.getChildNodes();
 *         for (int i = 0; i < childNodes.getLength(); i++) {
 *             Node item = childNodes.item(i);
 *             String nodeName = item.getNodeName();
 *             if("Region".equals(nodeName)){
 *                 this.regionDataList.add(new RegionData(item));
 *             }
 *         }
 * }
 *
 */
@Data
@NoArgsConstructor
public class CityData extends BaseData {
    private List<RegionData> regionDataList;

    public CityData(Node node){
        super(node);
        //此处可解析城市以下内容  区域

    }

}
