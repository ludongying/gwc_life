package com.seven.gwc.modular.lawrecord.data.word;

        import lombok.Data;
        import lombok.extern.slf4j.Slf4j;
        import org.apache.poi.hwpf.HWPFDocument;
        import org.apache.poi.hwpf.usermodel.Range;
        import org.apache.poi.xwpf.usermodel.IBodyElement;
        import org.apache.poi.xwpf.usermodel.XWPFDocument;
        import org.apache.poi.xwpf.usermodel.XWPFParagraph;
        import org.apache.poi.xwpf.usermodel.XWPFRun;

        import java.io.*;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.Objects;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

/**
 * @author : zzl
 * @Date: 2020-03-16
 * @description : 文书模板数据
 */
@Slf4j
@Data
public class InstrumentModelData {

    /**
     * 物理地址
     */
    public String path;
    /**
     * 对照表
     */
    public Map<String,String> contentMap=new HashMap<>();

    /**
     * 导出地址
     */

    public String exportPath;

    public static final String WORD_2003=".doc";
    public static final String WORD_2007=".docx";

    Pattern pattern = Pattern.compile("\\$\\{[0-9a-zA-Z_]{1,}\\}");
    public void read(){
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            log.error(">>>>文书模板路径正确");
        }
        try {
            if(path.endsWith(WORD_2003)){
                HWPFDocument  document = new HWPFDocument(inputStream);
                replace2003( document);
                // 读取文本内容
            }else if(path.endsWith(WORD_2007)){
                XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
                replace2007(xwpfDocument);

            }
        } catch (IOException e) {
            log.error("文件格式异常");
        }


    }

    public void replace2003(HWPFDocument document){
        // 读取文本内容
        Range bodyRange = document.getRange();
        // 替换内容
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
        }
    }

    public void replace2007( XWPFDocument xwpfDocument){
        List<XWPFParagraph> paras = xwpfDocument.getParagraphs();
        if(Objects.nonNull(paras) && !paras.isEmpty()){
            paras.stream().forEach(this::replaceInPara);
        }
    }


    private Matcher matcher(String content){
        return pattern.matcher(content);
    }

    /**
     * 替换段落里面的变量
     * @param para 要替换的段落
     */
    private void replaceInPara(XWPFParagraph para) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i=0; i<runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = this.matcher(runText);
                if (matcher.find()) {
                    while ((matcher = this.matcher(runText)).find()) {
                      /*runText = matcher.replaceFirst(String.valueOf(contentMap.get(matcher.group(1))));*/
                        runText=contentMap.get(runText);
                    }
                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }


    public void export2003(HWPFDocument  document){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            document.write(byteArrayOutputStream);
            OutputStream outputStream = new FileOutputStream(exportPath);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        InstrumentModelData instrumentModelData = new InstrumentModelData();
        Map<String, String> map = instrumentModelData.getContentMap();

        map.put("${ar_cp_name}","月报");
        map.put("ar_dateTime","2018-5-28");
        map.put("ar_info","岁的法国大使馆的风格");
        instrumentModelData.setPath("E:\\myfile\\file\\2.docx");
        instrumentModelData.read();


    }

}
