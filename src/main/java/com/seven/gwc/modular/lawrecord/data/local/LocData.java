package com.seven.gwc.modular.lawrecord.data.local;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : zzl
 * @Date: 2020-02-27
 * @description :
 */
@Slf4j
public class LocData {

    private List<StateData> stateDataList;

    private String uri= ResourceUtils.CLASSPATH_URL_PREFIX+"LocList.xml";

    public LocData(){
        this.stateDataList=new ArrayList<>();
        readXml();
    }

    private void readXml(){
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Element root=null;
        try {
            //2.创建DocumentBuilder对象
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse(uri);
            root = d.getDocumentElement();
        } catch (Exception e) {
            log.error("文件LocList.xml找不到相应的路径");
            e.printStackTrace();
        }
        NodeList countryRegions = root.getElementsByTagName("CountryRegion");
        Node country = countryRegions.item(0);
        NodeList states = country.getChildNodes();
        for (int i = 0; i < states.getLength(); i++) {
            Node node = states.item(i);
            String nodeName = node.getNodeName();
            if("State".equals(nodeName)){
                this.stateDataList.add(new StateData(node));
            }
        }
    }

    public List<StateData> getStateDataList() {
        return stateDataList;
    }


}
