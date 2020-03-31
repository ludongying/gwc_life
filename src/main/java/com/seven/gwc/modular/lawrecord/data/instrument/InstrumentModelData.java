package com.seven.gwc.modular.lawrecord.data.instrument;

import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : zzl
 * @Date: 2020-03-16
 * @description : 文书模板数据
 */
@Slf4j
public class InstrumentModelData {

    /**
     * 物理地址
     */
    public String path;
    /**
     * 对照表
     */
    public Map<String,String> contentMap;
    /**
     * 2003 doc
     */
    private HWPFDocument hwpfDocument;
    /**
     * 2007 doc
     */
    private XWPFDocument xwpfDocument;

    public static final String WORD_2003=".doc";
    public static final String WORD_2007=".docx";
    public static final Pattern pattern = Pattern.compile("\\$\\{[0-9a-zA-Z_]+\\}");

    public InstrumentModelData(String path,Map<String,String> contentMap){
        this.path=path;
        this.contentMap = contentMap;
        read();
    }

    private void read(){
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));
            log.info("处理文书"+path);
        } catch (FileNotFoundException e) {
            log.error(">>>>文书模板路径不正确:"+path);
        }
        try {
            if(path.endsWith(WORD_2003)){
                hwpfDocument = new HWPFDocument(inputStream);
                replace2003();
                // 读取文本内容
            }else if(path.endsWith(WORD_2007)){
                xwpfDocument= new XWPFDocument(inputStream);
                replace2007();
            }
        } catch (IOException e) {
            log.error("文件格式异常");
        }
    }

    private void replace2003(){
        // 读取文本内容
        Range bodyRange = hwpfDocument.getRange();
        // 替换内容
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            bodyRange.replaceText(entry.getKey(), entry.getValue());
        }
    }
    private void replace2007(){
        replaceInPara();
        replaceInTable();
    }
    /**
     * 是否符合正则表达式
     * @param content
     * @return
     */
    private Matcher matcher(String content){
        return  pattern.matcher(content);
    }
    /**
     * 替换段落里面的变量
     */
    private void replaceInPara() {
        replacePara(xwpfDocument.getParagraphs());
    }

    private void replacePara(List<XWPFParagraph> paras){
        if(Objects.nonNull(paras) && !paras.isEmpty()){
            for (XWPFParagraph para : paras) {
                if (this.matcher(para.getParagraphText()).find()) {
                    List<XWPFRun>  runs = para.getRuns();
                    for (int i=0; i<runs.size(); i++) {
                        XWPFRun run = runs.get(i);
                        String runText = run.toString();
                        if(runText.trim().isEmpty()|| !runText.contains("${")){
                            continue;
                        }
                        int fontSize = run.getFontSize();
                        String fontFamily = run.getFontFamily();
                        String color = run.getColor();
                        Matcher matcher = this.matcher(runText);
                        boolean flag=false;
                        while(matcher.find()){
                            flag=true;
                            String key = matcher.group(0);
                            String str = contentMap.get(key);
                            if(Objects.nonNull(str) && !str.trim().isEmpty()){
                                runText=runText.replace(key,str);
                                System.out.println(">>>>>>>>>>>>text"+runText);
                            }
                        }
                        if(flag){
                            para.removeRun(i);
                            XWPFRun xwpfRun = para.insertNewRun(i);
                            if(fontSize>0){
                                xwpfRun.setFontSize(fontSize);
                            }
                            xwpfRun.setFontFamily(fontFamily);
                            xwpfRun.setColor(color);
                            xwpfRun.setText(runText);
                        }
                    }
                }
            }
        }
    }


    /**
     * 替换表格里面的变量
     */
    private void replaceInTable() {
        // 替换表格中的指定文字 获得Word的表格
        Iterator<XWPFTable> itTable = xwpfDocument.getTablesIterator();
        //遍历表格
        while (itTable.hasNext()) {
            XWPFTable table = itTable.next();
            //获得表格总行数
            int count = table.getNumberOfRows();
            //遍历表格的每一行
            for (int i = 0; i < count; i++) {
                //获得表格的行
                XWPFTableRow row = table.getRow(i);
                //在行元素中，获得表格的单元格
                List<XWPFTableCell> cells = row.getTableCells();
                //遍历单元格
                for (XWPFTableCell cell : cells) {
                    String text = cell.getText();
                    if(text.trim().isEmpty() || !text.contains("${")){
                        continue;
                    }
                    replacePara(cell.getParagraphs());
                }
            }
        }
    }


    /**
     * 替换表格里面的变量
     */
    @Deprecated
    private void replaceInTable2() {
        // 替换表格中的指定文字 获得Word的表格
        Iterator<XWPFTable> itTable = xwpfDocument.getTablesIterator();
        //遍历表格
        while (itTable.hasNext()) {
            XWPFTable table = itTable.next();
            //获得表格总行数
            int count = table.getNumberOfRows();
            //遍历表格的每一行
            for (int i = 0; i < count; i++) {
                //获得表格的行
                XWPFTableRow row = table.getRow(i);
                //在行元素中，获得表格的单元格
                List<XWPFTableCell> cells = row.getTableCells();
                //遍历单元格
                for (XWPFTableCell cell : cells) {
                    String text = cell.getText();
                    if(text.trim().isEmpty() || !text.contains("${")){
                        continue;
                    }
                    XWPFParagraph xwpfParagraph = cell.getParagraphs().get(0);
                    XWPFRun xwpfRun = xwpfParagraph.getRuns().get(0);
                    String fontFamily = xwpfRun.getFontFamily();
                    String color = xwpfRun.getColor();
                    int fontSize = xwpfRun.getFontSize();
                    Matcher matcher = this.matcher(text);
                    boolean flag=false;
                    while(matcher.find()){
                        flag=true;
                        String key = matcher.group(0);
                        String str = contentMap.get(key);
                        if(Objects.nonNull(str) && !str.trim().isEmpty()){
                            text=text.replace(key,str);
                            System.out.println(">>>>>>>>>>>>text"+text);
                        }
                    }
                    if(flag){
                        cell.removeParagraph(0);
                        XWPFParagraph newParagraph = cell.addParagraph();
                        XWPFRun xwp=newParagraph.createRun();
/*                        cell.setText(text);*/
                        xwp.setText(text);
                        xwp.setColor(color);
                        xwp.setFontFamily(fontFamily);
                        xwp.setFontSize(fontSize);
                    }

/*                    if (matcher.find()) {
                        while ((matcher = this.matcher(text)).find()) {
                            String str = contentMap.get(matcher.group(0));
                            if (Objects.nonNull(str) && !str.trim().isEmpty()) {
                                text = matcher.replaceFirst(str);
                                System.out.println(">>>>>>>>>>>>text" + text);
                            }
                        }
                        cell.removeParagraph(0);
                        cell.setText(text);
                    }*/
                }
            }
        }
    }
    public void export(String exportPath){
        System.out.println(">>>>>>生成文书");
        File file=new File(exportPath);
        FileUtils.generateDir(file.getParent());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            if(path.endsWith(WORD_2003)){
                hwpfDocument.write(byteArrayOutputStream);
            }else{
                xwpfDocument.write(byteArrayOutputStream);
            }
            OutputStream outputStream = new FileOutputStream(exportPath);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.close();
        } catch (IOException e) {
            log.error("文书生成失败");
        }
    }

    public static void main(String[] args) {
        String path="D:\\ideawork\\GWC-WEB\\src\\main\\webapp\\static\\lawrecord\\instrument\\27责令返港通知书_法人_安.docx";
        InstrumentModelData instrumentModelData = new InstrumentModelData(path, new HashMap<>());
        instrumentModelData.export("D:\\1.docx");


    }

}
