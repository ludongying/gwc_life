package com.seven.gwc.modular.lawrecord.data.word;

import com.seven.gwc.core.util.SpringContextUtil;
import com.seven.gwc.modular.lawrecord.data.word.config.InstrumentContrast;
import lombok.Data;
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
    }
    public void read(){
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
        // 替换表格中的指定文字
        Iterator<XWPFTable> itTable = xwpfDocument.getTablesIterator();//获得Word的表格
        while (itTable.hasNext()) { //遍历表格
            XWPFTable table =itTable.next();
            int count = table.getNumberOfRows();//获得表格总行数
            for (int i = 0; i < count; i++) { //遍历表格的每一行
                XWPFTableRow row = table.getRow(i);//获得表格的行
                List<XWPFTableCell> cells = row.getTableCells();//在行元素中，获得表格的单元格
                for (XWPFTableCell cell : cells) {   //遍历单元格
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
    private void export(String exportPath){
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
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        InstrumentModelData instrumentModelData = new InstrumentModelData("D:\\word\\1.doc",new InstrumentContrast().getMap());
        instrumentModelData.read();
        instrumentModelData.export("D:\\word\\1.doc");

    }

}
