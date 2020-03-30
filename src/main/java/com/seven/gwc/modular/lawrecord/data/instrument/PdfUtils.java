package com.seven.gwc.modular.lawrecord.data.instrument;

import java.util.ArrayList;
import java.util.List;

public class PdfUtils {
    public static void main (String[] args) throws Exception {

        String path="D:\\myfile\\file\\lawrecord\\2020\\苏连渔执罚20201014号\\";
        List<String> srcfile = new ArrayList<>();
        String file1=path+"01封面_法人.docx";
        String file2 = path+"02目录.docx";
        srcfile.add(file1);
        srcfile.add(file2);

    }



}