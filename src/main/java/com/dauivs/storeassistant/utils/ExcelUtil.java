package com.dauivs.storeassistant.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    /**
     * 从输入流读取excel数据
     * @param in 输入流
     * @param type excel数据类型
     * @param sheetIndex 要读取的表
     * @return 读取后的数据
     */
    public static List<List<Object>> read(InputStream in, String type, int sheetIndex) {
        List<List<Object>> result = new ArrayList<>();
        Workbook workbook = null;
        try {
            switch (type) {
                case "xls":
                    workbook = new HSSFWorkbook(in);
                    break;
                case "xlsx":
                    workbook = new XSSFWorkbook(in);
                    break;
                default:
                    throw new Exception("不支持的类型:" + type);
            }
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); ++i) {
                Row row = sheet.getRow(i);
                List<Object> values = new ArrayList<>();
                for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); ++j ) {
                    Cell cell = row.getCell(j);
                    Object value = null;
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BLANK:
                                value = "";
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                value = cell.getBooleanCellValue();
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                value = cell.getCellFormula();
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                value = cell.getNumericCellValue();
                                break;
                            case Cell.CELL_TYPE_STRING:
                                value = cell.getStringCellValue();
                                break;
                        }
                    }
                    values.add(value);
                }
                result.add(values);
            }
        } catch (Exception e) {
            throw new RuntimeException("读取excel文件失败", e);
        }
        return result;
    }

    /**
     * 从输入流读取excel内容
     * @param in
     * @param type
     * @return
     */
    public static List<List<Object>> read(InputStream in, String type) {
        return read(in, type, 0);
    }

    public static List<List<Object>> read(File file, int sheetIndex) {
        if (file == null || !file.exists()) {
            return new ArrayList<>();
        }
        int dotIndex = file.getName().lastIndexOf(".");
        String type = file.getName().substring(dotIndex + 1).toLowerCase();
        try {
            return read(new FileInputStream(file), type, sheetIndex);
        } catch(Exception e) {
            throw new RuntimeException("读取excel文件失败", e);
        }
    }

    public static List<List<Object>> read(File file) {
        return read(file, 0);
    }

 }
