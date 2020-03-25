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
    public static final Pattern pattern = Pattern.compile("\\$\\{[0-9a-zA-Z_]{1,}\\}");

    public InstrumentModelData(String path,Map<String,String> contentMap){
        this.path=path;
        this.contentMap = contentMap;
        read();
    }

    private void read(){
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            log.error(">>>>文书模板路径正确");
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
        List<XWPFParagraph> paras = xwpfDocument.getParagraphs();
        if(Objects.nonNull(paras) && !paras.isEmpty()){
            for (XWPFParagraph para : paras) {
                if (this.matcher(para.getParagraphText()).find()) {
                    List<XWPFRun>  runs = para.getRuns();
                    for (int i=0; i<runs.size(); i++) {
                        XWPFRun run = runs.get(i);
                        String runText = run.toString();
                        Matcher matcher = this.matcher(runText);
                        if (matcher.find()) {
                            while ((matcher = this.matcher(runText)).find()) {
                                runText = matcher.replaceFirst(String.valueOf(contentMap.get(matcher.group(0))));
                            }
                            //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                            //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                            para.removeRun(i);
                            para.insertNewRun(i).setText(runText);
                        }
                    }
                }
            }
        }
    }
    /**
     * 替换表格里面的变量
     */
    private void replaceInTable(){
        // 替换表格中的指定文字 获得Word的表格
        Iterator<XWPFTable> itTable = xwpfDocument.getTablesIterator();
        //遍历表格
        while (itTable.hasNext()) {
            XWPFTable table =itTable.next();
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
                    Matcher matcher = this.matcher(text);
                    if (matcher.find()) {
                        while ((matcher = this.matcher(text)).find()) {
                            text = matcher.replaceFirst(String.valueOf(contentMap.get(matcher.group(0))));
                        }
                        cell.removeParagraph(0);
                        cell.setText(text);
                    }
                }
            }
        }
    }
    public void export(String exportPath){
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
       Map<String,String> map=new HashMap<>(1);
        map.put("${Punish_year}","2020");
        InstrumentModelData instrumentModelData = new InstrumentModelData("D:\\ideawork\\GWC-WEB\\src\\main\\resources\\lawrecord\\instrument\\02目录.docx",map);
        instrumentModelData.export("D:\\word\\1.docx");

    }

}
