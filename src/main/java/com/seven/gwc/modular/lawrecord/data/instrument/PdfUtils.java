package com.seven.gwc.modular.lawrecord.data.instrument;

import com.aspose.words.*;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.FilePathDO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PdfUtils {



    public static void mergeDoc(List<FilePathDO> srcfile, String newFile){
        if (!FileUtils.getLicense()) {
            return;
        }
        try {
            FileInputStream mainStream = new FileInputStream(new File(srcfile.get(0).getPath()));
            Document mainDoc=new Document(mainStream);
            List<Document> documents=new ArrayList<>();
            for (int i = 1; i < srcfile.size(); i++) {
                FileInputStream fileInputStream = new FileInputStream(new File(srcfile.get(i).getPath()));
                documents.add(new Document(fileInputStream));
                fileInputStream.close();
            }
            for (int i =1; i < documents.size(); i++) {
                appendDocument(mainDoc, documents.get(i), true, i<srcfile.size()-2);
            }
//            BookmarkCollection bookmarks = mainDoc.getRange().getBookmarks();
//            bookmarks.remove("no1");
            mainDoc.save(new FileOutputStream(new File(newFile)), SaveFormat.DOCX);
            mainStream.close();
            System.out.println("over");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Document appendDocument(Document mainDoc, Document addDoc, boolean isPortrait,boolean breakPage) {
       //设置书签，指定文档拼接的位置
        String bookmark = "no1";
        DocumentBuilder builder = null;
        try {
            builder = new DocumentBuilder(mainDoc);
            BookmarkCollection bms = mainDoc.getRange().getBookmarks();
            Bookmark bm = bms.get(bookmark);
            if (bm != null) {
                builder.moveToBookmark(bookmark, true, false);
                builder.writeln();
                Node insertAfterNode = builder.getCurrentParagraph().getPreviousSibling();
                insertDocumentAfterNode(insertAfterNode, mainDoc, addDoc);
            }
           //设置纸张大小
            builder.getPageSetup().setPaperSize(PaperSize.A4);
            if (isPortrait) {
                //纵向纸张，
                builder.getPageSetup().setOrientation(Orientation.PORTRAIT);
                if(breakPage){
                    builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
                }

            } else {
                //横向
                builder.getPageSetup().setOrientation(Orientation.LANDSCAPE);
                builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
            }
            //builder.insertBreak(BreakType.PAGE_BREAK);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mainDoc;
    }

    /**
     * @Description 向书签后插入文档
     * @param mainDoc 主文档
     * @param tobeInserted 拼接的文档
     * @param bookmark 书签
     * @Return com.aspose.words.Document
     * @Author Mr.Walloce
     * @Date 2019/7/27 18:33
     */
    private static Document insertDocumentAfterBookMark(Document mainDoc, Document tobeInserted, String bookmark)
            throws Exception {
        if (mainDoc == null) {
            return null;
        } else if (tobeInserted == null) {
            return mainDoc;
        } else {
            //构建新文档
            DocumentBuilder mainDocBuilder = new DocumentBuilder(mainDoc);
            if (bookmark != null && bookmark.length() > 0) {
                //获取到书签
                BookmarkCollection bms = mainDoc.getRange().getBookmarks();
                Bookmark bm = bms.get(bookmark);
                if (bm != null) {
                    mainDocBuilder.moveToBookmark(bookmark, true, false);
//                    mainDocBuilder.writeln();
                    //获取到插入的位置
                    Node insertAfterNode = mainDocBuilder.getCurrentParagraph().getPreviousSibling();
                    insertDocumentAfterNode(insertAfterNode, mainDoc, tobeInserted);
                }
            } else {
                appendDoc(mainDoc, tobeInserted, true);
            }

            return mainDoc;
        }
    }

    /**
     * @Description TODO
     * @param insertAfterNode 插入的位置
     * @param mainDoc
     * @param srcDoc
     * @Return void
     * @Author Mr.Walloce
     * @Date 2019/7/27 14:51
     */
    private static void insertDocumentAfterNode(Node insertAfterNode, Document mainDoc, Document srcDoc)
            throws Exception {
        if (insertAfterNode.getNodeType() != 8 & insertAfterNode.getNodeType() != 5) {
            throw new Exception("The destination node should be either a paragraph or table.");
        } else {
            CompositeNode dstStory = insertAfterNode.getParentNode();

            while (null != srcDoc.getLastSection().getBody().getLastParagraph()
                    && !srcDoc.getLastSection().getBody().getLastParagraph().hasChildNodes()) {
                srcDoc.getLastSection().getBody().getLastParagraph().remove();
            }

            NodeImporter importer = new NodeImporter(srcDoc, mainDoc, 1);
            int sectCount = srcDoc.getSections().getCount();

            for (int sectIndex = 0; sectIndex < sectCount; ++sectIndex) {
                Section srcSection = srcDoc.getSections().get(sectIndex);
                int nodeCount = srcSection.getBody().getChildNodes().getCount();

                for (int nodeIndex = 0; nodeIndex < nodeCount; ++nodeIndex) {
                    Node srcNode = srcSection.getBody().getChildNodes().get(nodeIndex);
                    Node newNode = importer.importNode(srcNode, true);
                    dstStory.insertAfter(newNode, insertAfterNode);
                    insertAfterNode = newNode;
                }
            }

        }
    }

    /**
     * @Description 文档拼接
     * @param dstDoc
     * @param srcDoc
     * @param includeSection
     * @Return void
     * @Author Mr.Walloce
     * @Date 2019/7/27 14:53
     */
    private static void appendDoc(Document dstDoc, Document srcDoc, boolean includeSection) throws Exception {
        if (includeSection) {
            Iterator<Section> var3 = srcDoc.getSections().iterator();
            while (var3.hasNext()) {
                Section srcSection = (Section) var3.next();
                Node dstNode = dstDoc.importNode(srcSection, true, 0);
                dstDoc.appendChild(dstNode);
            }
        } else {
            Node node = dstDoc.getLastSection().getBody().getLastParagraph();
            if (node == null) {
                node = new Paragraph(srcDoc);
                dstDoc.getLastSection().getBody().appendChild(node);
            }

            if (node.getNodeType() != 8 & node.getNodeType() != 5) {
                throw new Exception("Use appendDoc(dstDoc, srcDoc, true) instead of appendDoc(dstDoc, srcDoc, false)");
            }

            insertDocumentAfterNode(node, dstDoc, srcDoc);
        }
    }


    public static void main(String[] args) {
        String p1="D:\\myfile\\file\\lawrecord\\2020\\连海渔执罚20201005号\\01封面_法人.docx";
        String p10="D:\\myfile\\file\\lawrecord\\2020\\连海渔执罚20201005号\\21送达回证一.docx";
        String p11="D:\\myfile\\file\\lawrecord\\2020\\连海渔执罚20201005号\\21送达回证二.docx";
        String p12="D:\\myfile\\file\\lawrecord\\2020\\连海渔执罚20201005号\\24行政处罚结案报告_法人.docx";
        String p13="D:\\myfile\\file\\lawrecord\\2020\\连海渔执罚20201005号\\25备考表.docx";
        String p14="D:\\myfile\\file\\lawrecord\\2020\\连海渔执罚20201005号\\26行政处罚决定审批表_法人.docx";

        String p15="D:\\myfile\\file\\lawrecord\\2020\\连海渔执罚20201005号\\27责令返港通知书_法人_安.docx";
        String p16="D:\\myfile\\file\\lawrecord\\2020\\连海渔执罚20201005号\\28限时返港承诺书.docx";
        String p17="D:\\myfile\\file\\lawrecord\\2020\\连海渔执罚20201005号\\29责令整改通知书_法人_安.docx";
        try {
            if (!FileUtils.getLicense()) {
                return;
            }
            FileInputStream main = new FileInputStream(new File(p1));
            Document n = new Document(main);
            Document n10 = new Document(new FileInputStream(new File(p10)));
            Document n11 = new Document(new FileInputStream(new File(p11)));
            Document n12 = new Document(new FileInputStream(new File(p12)));
            Document n13 = new Document(new FileInputStream(new File(p13)));
            Document n14 = new Document(new FileInputStream(new File(p14)));
            Document n15 = new Document(new FileInputStream(new File(p15)));
            Document n16 = new Document(new FileInputStream(new File(p16)));
            Document n17 = new Document(new FileInputStream(new File(p17)));
              appendDocument(n,n10, true,true);
              appendDocument(n,n11, true,true);
              appendDocument(n,n12, true,true);
              appendDocument(n,n13, true,true);
              appendDocument(n,n14, true,true);
              appendDocument(n,n15, true,true);
              appendDocument(n,n16, true,true);
              appendDocument(n,n17,true,true);
//            DocumentBuilder documentBuilder = new DocumentBuilder(n);
//            BookmarkCollection bookmarks = n.getRange().getBookmarks();
//            bookmarks.remove("no1");



            // 根据表格找到所在段落
//            Paragraph lastParagraph = n.getLastSection().getBody().getLastParagraph();
//            lastParagraph.remove();
//            Paragraph lastParagraph2 = n.getLastSection().getBody().getLastParagraph();
//            lastParagraph2.remove();
// 清除段落前的分页符
//            if (lastParagraph.getParagraphFormat().getPageBreakBefore())
//                lastParagraph.getParagraphFormat().setPageBreakBefore(false);
// 清除段落中的分页符
//            RunCollection runs = lastParagraph.getRuns();
//            for (Run run : runs) {
//                if (run.getText().contains(ControlChar.PAGE_BREAK))
//                    run.setText(run.getText().replace(ControlChar.PAGE_BREAK, null));
//            }


            n.save(new FileOutputStream(new File("D://20.docx")), SaveFormat.DOCX);
            main.close();
            System.out.println("over");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}