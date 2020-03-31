package com.seven.gwc.modular.lawrecord.data.instrument;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * word文件合并
 * @author zzl
 */
@Slf4j
public class WordUtils {

    public static boolean mergeDoc(List<String> srcfile,String newFile){
        try {
            OutputStream dest = new FileOutputStream(newFile);
            ArrayList<XWPFDocument> documentList = new ArrayList<>();
            XWPFDocument doc = null;
            for (int i = 0; i < srcfile.size(); i++) {
                FileInputStream in = new FileInputStream(srcfile.get(i));
                OPCPackage open = OPCPackage.open(in);
                XWPFDocument document = new XWPFDocument(open);
                documentList.add(document);
            }
            for (int i = 0; i < documentList.size(); i++) {
                doc = documentList.get(0);
                if(i != 0){
                    XWPFParagraph paragraph = documentList.get(i).createParagraph();
                    if(i!=documentList.size()-1){
                        paragraph.setPageBreak(true);
                    }
                    appendBody(doc,documentList.get(i));
                }
            }
            doc.createParagraph().setPageBreak(false);
            doc.write(dest);
        } catch (Exception e) {
            log.error("案件文书合并失败");
            return false;
        }
        return true;
    }

    private static void appendBody(XWPFDocument src, XWPFDocument append) throws Exception {
        CTBody src1Body = src.getDocument().getBody();
        CTBody src2Body = append.getDocument().getBody();

        List<XWPFPictureData> allPictures = append.getAllPictures();
        // 记录图片合并前及合并后的ID
        Map<String,String> map = new HashMap();
        for (XWPFPictureData picture : allPictures) {
            String before = append.getRelationId(picture);
            //将原文档中的图片加入到目标文档中
            String after = src.addPictureData(picture.getData(), Document.PICTURE_TYPE_PNG);
            map.put(before, after);
        }

        appendBody(src1Body, src2Body,map);

    }

    private static void appendBody(CTBody src, CTBody append,Map<String,String> map) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);

        String srcString = src.xmlText();
        String prefix = srcString.substring(0,srcString.indexOf(">")+1);
        String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
        String sufix = srcString.substring( srcString.lastIndexOf("<") );
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));

        if (map != null && !map.isEmpty()) {
            //对xml字符串中图片ID进行替换
            for (Map.Entry<String, String> set : map.entrySet()) {
                addPart = addPart.replace(set.getKey(), set.getValue());
            }
        }
        //将两个文档的xml内容进行拼接
        CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+sufix);

        src.set(makeBody);
    }


    public static void main(String[] args) {
        String p1="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\26行政处罚决定审批表_法人.docx";
        String p2="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\28限时返港承诺书.docx";
        String p3="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\29责令整改通知书_法人_安.docx";
        ArrayList<String> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        mergeDoc(list,"D:\\1.docx");
    }


}
