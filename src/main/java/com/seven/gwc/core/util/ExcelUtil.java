package com.seven.gwc.core.util;

import com.seven.gwc.config.constant.SysConsts;
import com.seven.gwc.modular.lawrecord.data.export.ExcelData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Description: TODO
 * @Date: 2020/1/15 17:18
 * @Author: GD
 * @Version: 1.0
 */
public class ExcelUtil {

    //文件保存在文件夹
    public static String saveFile(String fileDown, MultipartFile file) throws IOException {
        String message = "";
        if (file.isEmpty()) {
            message = "文件为空";
        } else {
            byte[] bytes = file.getBytes();

            File nodeFile = new File(fileDown);
            if (!nodeFile.exists()) {//如果文件夹不存在
                nodeFile.mkdirs();//创建文件夹
            }
            String fileFullName = nodeFile + "//" + file.getOriginalFilename();
            Path path = Paths.get(fileFullName);
            Files.write(path, bytes);
            message = fileFullName;
        }
        return message;
    }

    public static Workbook importJudgeEdition(String fileFullName){
        File uploadDir = new File(fileFullName);
        if (!uploadDir.exists())
            uploadDir.mkdirs();
        // 新建一个文件
        File tempFile = new File(fileFullName);
        // 初始化输入流
        InputStream is = null;
        try {
            is = new FileInputStream(tempFile);
            String postfix = ToolUtil.getPostfix(fileFullName);
            if (!SysConsts.EMPTY.equals(postfix)) {
                if (SysConsts.STR_XLS.equals(postfix)) {
                    HSSFWorkbook workbook = new HSSFWorkbook(is);
                    return workbook;
                } else if (SysConsts.STR_XLSX.equals(postfix)) {
                    Workbook workbook = new XSSFWorkbook(is);
                    return workbook;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     *  导出导入时的错误数据
     * @param fileDown 文件路径
     * @param head 表头
     * @param errorList 错误数据的行
     * @param sheet 数据
     * @param lastCellNum 表头的长度
     * @param date 时间戳（为了区分导出错误数据的excel，以免文件名一样）
     */
    public static void errorExcel(String fileDown, String[] head, List<Integer> errorList, Sheet sheet, int lastCellNum, Long date, HttpServletResponse resp) {
        Workbook workbook = new XSSFWorkbook();
        Sheet errorExcel = workbook.createSheet("导入反馈表");

        Font headerFont = workbook.createFont();
        headerFont.setBoldweight((short) 3);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = errorExcel.createRow(0);

        for (int i = 0; i < head.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(head[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for (int i = 0; i < errorList.size(); i++) {
            Row row = sheet.getRow(errorList.get(i));
            Row createRow = errorExcel.createRow(rowNum++);
            for (int j = 0; j < lastCellNum; j++) {
                createRow.createCell(j).setCellValue(ToolUtil.getCellValue(row.getCell(j)));
            }
        }
        for (int i = 0; i < head.length; i++) {
            sheet.autoSizeColumn(i);
        }
        try {
            String fileFullName = fileDown + "//" + date + ".xlsx";
            FileOutputStream fileOut = new FileOutputStream(fileFullName);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData data) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
        exportExcel(data, response.getOutputStream());
    }

    public static void exportExcel(ExcelData data, OutputStream out) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            XSSFSheet sheet = wb.createSheet(sheetName);
            writeExcel(wb, sheet, data);

            wb.write(out);
        } finally {
            out.close();
        }
    }

    private static void writeExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data) {
        int rowIndex = 0;
        rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
        writeRowsToExcel(wb, sheet, data.getRows(), rowIndex);
        autoSizeColumns(sheet, data.getTitles().size() + 1);
    }

    private static int writeTitlesToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex = 0;

        Font titleFont = wb.createFont();
        titleFont.setFontName("微软雅黑");
        titleFont.setBoldweight((short)10);
        // titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        titleStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);

        Row titleRow = sheet.createRow(rowIndex);
        colIndex = 0;

        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }

        rowIndex++;
        return rowIndex;
    }

    private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<List<Object>> rows, int rowIndex) {
        int colIndex = 0;

        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle, BorderStyle.NONE, new XSSFColor(java.awt.Color.LIGHT_GRAY));

        for (List<Object> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            // dataRow.setHeightInPoints(25);
            colIndex = 0;

            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                    cell.setCellValue(cellData.toString());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(dataStyle);
                colIndex++;
            }
            rowIndex++;
        }
        return rowIndex;
    }

    private static void autoSizeColumns(Sheet sheet, int columnNumber) {
        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }

    private static void setBorder(XSSFCellStyle style, BorderStyle border, XSSFColor color) {
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
    }
}
