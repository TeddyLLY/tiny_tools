package com.tools;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

public class Excel2DML {

    final static int[] IntValueArray = {9,10,11};
    final static String InsertString = " INSERT INTO customer_portal.legacy_customer (data_source,add_company,company_name,company_name_eng," +
            "tax_no,post_code,address,registration_phone,employees_number,industry_id,capital_amount,employee_name," +
            "position_title,employee_email,company_tel,extension,cellphone,remark,company_state) \n" ;
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
                        for(int i = 0 ; i< 19 ; i++){
                            try {
                                row.getCell(i).setCellType(CellType.STRING);
                                if(containsNumber(IntValueArray,i)){
                                    InsertSQL.append( row.getCell(i).getStringCellValue() + ",");
                                }else{
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
                InsertSQL.setLength(InsertSQL.length() - 1);
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