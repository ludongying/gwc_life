package com.seven.gwc.modular.lawrecord.data.instrument;


import com.seven.gwc.modular.lawrecord.data.instrument.dos.FilePathDO;
import com.seven.gwc.modular.lawrecord.enums.InstrumentEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
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

    public static boolean mergeDoc(List<FilePathDO> srcfile, String newFile){
        try {
            List<Integer> noBreaks = InstrumentEnum.getNoBreakCode();
            OutputStream dest = new FileOutputStream(newFile);
            ArrayList<XWPFDocument> documentList = new ArrayList<>();
            XWPFDocument doc = null;
            for (int i = 0; i < srcfile.size(); i++) {
                FileInputStream in = new FileInputStream(srcfile.get(i).getPath());
                OPCPackage open = OPCPackage.open(in);
                XWPFDocument document = new XWPFDocument(open);
                documentList.add(document);
            }
            for (int i = 0; i < documentList.size(); i++) {
                doc = documentList.get(0);
                Integer code=srcfile.get(i).getCode();
//                if(i != 0){
                    XWPFParagraph paragraph = documentList.get(i).createParagraph();
                    if(i!=documentList.size()-1){
                        if(!noBreaks.contains(code)){
                            System.out.println(">>>>>>>>code"+code);
                            paragraph.setPageBreak(true);
                        }
                    }
                    appendBody(doc,documentList.get(i));
                }
//            }
            doc.createParagraph().setPageBreak(false);
            doc.write(dest);
        } catch (Exception e) {
            e.printStackTrace();
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


    private static FilePathDO gen(String path){
        String name = path.substring(path.lastIndexOf("\\")+1);
        String str = name.substring(0, 2);
        return new FilePathDO(Integer.parseInt(str),path,"1");
    }

    public static void main(String[] args) {
        String p1="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\01封面_法人.docx";
        String p2="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\02目录.docx";
        String p3="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\03行政处罚决定书_法人_安.docx";
        String p4="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\05行政处罚立案审批表_法人_安.docx";
        String p5="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\06勘验笔录_安.docx";
        String p6="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\07询问笔录_安.docx";
        String p7="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\13案件处理意见书_法人_安.docx";
        String p8="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\14行政处罚事先告知书_法人_安.docx";
        String p9="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\15陈述和申辩笔录.docx";
        String p10="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\21送达回证一.docx";
        String p11="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\21送达回证二.docx";
        String p12="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\24行政处罚结案报告_法人.docx";
        String p13="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\25备考表.docx";
        String p14="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\26行政处罚决定审批表_法人.docx";
        String p15="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\27责令返港通知书_法人_安.docx";
        String p16="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\28限时返港承诺书.docx";
        String p17="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\29责令整改通知书_法人_安.docx";
        String p18="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\30整改承诺书_安.docx";
        List<FilePathDO> list= new ArrayList<>(18);
        list.add(gen(p1));
        list.add(gen(p2));
        list.add(gen(p3));
        list.add(gen(p4));
        list.add(gen(p5));
        list.add(gen(p6));
        list.add(gen(p7));
        list.add(gen(p8));
        list.add(gen(p9));
        list.add(gen(p10));
        list.add(gen(p11));
        list.add(gen(p12));
        list.add(gen(p13));
        list.add(gen(p14));
        list.add(gen(p15));
        list.add(gen(p16));
        list.add(gen(p17));
        list.add(gen(p18));

        AsopseWordUtils.mergeDoc(list,"D:\\222.docx");
    }




}
