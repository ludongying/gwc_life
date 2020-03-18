package com.seven.gwc.modular.lawrecord.data.word;

import fr.opensagres.xdocreport.itext.extension.IPdfWriterConfiguration;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordUtils {

    /**
     * 合并docx文件
     * @param srcDocxs 需要合并的目标docx文件
     * @param destDocx 合并后的docx输出文件
     */
    public static void mergeDoc(String[] srcDocxs,String destDocx){

        OutputStream dest = null;
        List<OPCPackage> opcpList = new ArrayList<OPCPackage>();
        int length = null == srcDocxs ? 0 : srcDocxs.length;
        /**
         * 循环获取每个docx文件的OPCPackage对象
         */
        for (int i = 0; i < length; i++) {
            String doc = srcDocxs[i];
            OPCPackage srcPackage =  null;
            try {
                srcPackage = OPCPackage.open(doc);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(null != srcPackage){
                opcpList.add(srcPackage);
            }
        }

        int opcpSize = opcpList.size();
        //获取的OPCPackage对象大于0时，执行合并操作
        if(opcpSize > 0){
            try {
                dest = new FileOutputStream(destDocx);
                XWPFDocument src1Document = new XWPFDocument(opcpList.get(0));
                CTBody src1Body = src1Document.getDocument().getBody();
                //OPCPackage大于1的部分执行合并操作
                if(opcpSize > 1){
                    for (int i = 1; i < opcpSize; i++) {
                        OPCPackage src2Package = opcpList.get(i);
                        XWPFDocument src2Document = new XWPFDocument(src2Package);
                        CTBody src2Body = src2Document.getDocument().getBody();
                        appendBody(src1Body, src2Body);
                    }
                }
                //将合并的文档写入目标文件中
                src1Document.write(dest);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                //注释掉以下部分，去除影响目标文件srcDocxs。
				/*for (OPCPackage opcPackage : opcpList) {
					if(null != opcPackage){
						try {
							opcPackage.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}*/
                //关闭流
                IOUtils.closeQuietly(dest);
            }
        }


    }

    /**
     * 合并文档内容
     * @param src 目标文档
     * @param append 要合并的文档
     * @throws Exception
     */
    private static void appendBody(CTBody src, CTBody append) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);
        String srcString = src.xmlText();
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String mainPart = srcString.substring(srcString.indexOf(">") + 1,
                srcString.lastIndexOf("<"));
        String sufix = srcString.substring(srcString.lastIndexOf("<"));
        String addPart = appendString.substring(appendString.indexOf(">") + 1,
                appendString.lastIndexOf("<"));
        CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart
                + sufix);
        src.set(makeBody);
    }

    public static void main(String[] args) {
//        String dir="D:\\ideawork\\GWC-WEB\\src\\main\\resources\\lawrecord\\安全\\";
//        mergeDoc(new String[]{dir+"01封面_法人.docx",dir+"02目录.docx"},"D:\\word\\3.docx");
        word2Pdf(4);

    }


    public static void toHtml(){
        String src = "D:\\word\1.docx";
        String des = "D:\\word\\1.html";
        String htmlImagesPath="D:\\word\\";
        InputStream is = null;
        try {
            is = new FileInputStream(src);
            XWPFDocument document = new XWPFDocument(is);
            XHTMLOptions options = XHTMLOptions.create().indent(4);

            //img的src属性 后面会自动添加/word/media
            //这里就是images/word/media/ + 图片名字
            options.URIResolver(new BasicURIResolver("images"));
            //>> 文件的保存路径 之后自动会添加 word\media子路径
            FileImageExtractor extractor = new FileImageExtractor(new File(
                    htmlImagesPath));
            options.setExtractor(extractor);
            XHTMLConverter.getInstance().convert(document,
                    new FileOutputStream(des), options);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void word2Pdf(int index){
        String docPath = "D:\\word\\"+index+".docx";
        String pdfPath = "D:\\word\\"+index+".pdf";

        try{
            XWPFDocument document;
            InputStream doc = new FileInputStream(docPath);
            document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();
            OutputStream out = new FileOutputStream(pdfPath);
            PdfConverter.getInstance().convert(document, out, options);
            doc.close();
            out.close();
        }catch (Exception e){

        }


    }

}
