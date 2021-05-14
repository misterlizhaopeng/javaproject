package C;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @ClassName C.L
 * @Deacription TODO
 * @Author LP
 * @Date 2021/4/18 15:39
 * @Version 1.0
 **/
public class L {
    public static void main(String[] args) throws IOException {
        // 获取文件路径和文件
        FileInputStream fis = new FileInputStream("C:/Users/Administrator/Desktop/a.xlsx");
        // 将输入流转换为工作簿对象
        @SuppressWarnings("resource")
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
        //XSSFWorkbook
        // 获取第一个工作表
        HSSFSheet sheet = workbook.getSheet("sheet0");
        // 使用索引获取工作表
        // HSSFSheet sheet = workbook.getSheetAt(0);
        // 获取指定行
        HSSFRow row = sheet.getRow(0);
        // 获取指定列
        HSSFCell cell = row.getCell(2);
        // 打印
        System.out.println(cell.getStringCellValue());
    }
}

