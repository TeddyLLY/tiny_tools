package com.tools;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author teddylai
 */
public class Excel2DML {

    //一行有幾個 column start with 1
    final static int ExcelColumnCount = 6;
    //int column ( start with 0 )
    final static int[] IntValueArray = {4};
    //date column ( start with 0 )
    final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    final static int[] DateValueArray = {3};
    //dml string (insert)
    final static String InsertString =
    "INSERT INTO employee (first_name, last_name, email, hire_date, salary, department) \n";
    public static void main(String[] args) {
        Excel2DML.getDataFromExcel(System.getProperty("user.dir") + "/excel.xlsx");
    }


    /**
     * 读取excel文件内容生成数据库表 insert
     *
     * @param filePath excel文件的绝对路径
     */
    public static void getDataFromExcel(String filePath) {
        if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
            System.out.println("文件不是excel类型");
        }
        InputStream fis = null;
        Workbook wookbook = null;

        try {
            fis = new FileInputStream(filePath);
            if (filePath.endsWith(".xls")) {
                    //2003版本的excel，用.xls结尾
                    wookbook = new HSSFWorkbook(fis);
            }
            if (filePath.endsWith(".xlsx")) {
                    //2007版本的excel，用.xlsx结尾
                    wookbook = new XSSFWorkbook(fis);
            }

            Iterator<Sheet> sheets = wookbook.sheetIterator();


            while (sheets.hasNext()) {
                // 是否自增
                Sheet sheet = sheets.next();
                System.out.println("--------------------------当前读取的sheet页：" + sheet.getSheetName() + "--------------------------");
                // 行號
                int columns = 1;
                Iterator<Row> rows = sheet.rowIterator();

                //要寫入的 sql
                StringBuilder InsertSQL = new StringBuilder();
                InsertSQL.append( InsertString +  "VALUES ");
                while (rows.hasNext()) {
                    Row row = rows.next();
                    InsertSQL.append(" ( ");
                        for(int i = 0 ; i< ExcelColumnCount ; i++){
                            try {
                                if(containsNumber(IntValueArray,i)){
                                    row.getCell(i).setCellType(CellType.STRING);
                                    InsertSQL.append( row.getCell(i).getStringCellValue() + ",");
                                }else if(containsNumber(DateValueArray,i)){
                                    InsertSQL.append( "'" + sdf.format(row.getCell(i).getDateCellValue())  + "'" + ",");
                                }else{
                                    row.getCell(i).setCellType(CellType.STRING);
                                    InsertSQL.append( "'" +row.getCell(i).getStringCellValue() + "'" + ",");
                                }
                            } catch (NullPointerException e) {
                                InsertSQL.append("null" + ",");
                            } catch (Exception ee) {
                                System.out.println("i = " + i + "  , columns = " + columns );
                                throw new RuntimeException(ee);
                            }
                            columns++;
                        }
                    InsertSQL.setLength(InsertSQL.length() - 1);
                    InsertSQL.append(" ) ,\n");
                }//row end
                InsertSQL.setLength(InsertSQL.length() - 2);
                InsertSQL.append(" ;");
                writeMessageToFile(InsertSQL.toString());
            }//sheet end

            System.out.println("运行成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeMessageToFile(String message) {
        try {
            File file = new File("dml.sql");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            fileWriter.write(message);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean containsNumber(int[] array, int target) {
        int index = Arrays.binarySearch(array, target);
        return index >= 0;
    }

}