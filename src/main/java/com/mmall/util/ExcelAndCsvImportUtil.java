package com.mmall.util;

import com.alipay.api.domain.VehicleInfo;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: chenyawei
 * @Date: 2020-06-23  15:54
 * @Description:
 */
public class ExcelAndCsvImportUtil {

    public static List<VehicleInfo> readExcel(MultipartFile file) throws IOException, BiffException, Exception {
        // 新建list用于存放excel中的数据
        List<VehicleInfo> list = new ArrayList<VehicleInfo>();
        File file1 = new File("/Users/chenyawei/Downloads/mmall_product.xlsx");
        FileInputStream input = new FileInputStream(file1);
        MultipartFile file2 = new MockMultipartFile("file",
                file1.getName(), "text/plain", IOUtils.toByteArray(input));

        // 从MultipartFile的文件中获取一个输入流
        InputStream is = file2.getInputStream();

        // 创建一个Workbook的对象，即一个Workbook就一个excel
        Workbook workbook = Workbook.getWorkbook(is);

        // 在获取excel的sheet数
        int sheetNum = workbook.getNumberOfSheets();
        // 循环workbook，读取其中每一个sheet的数据
        for (int i = 0; i < sheetNum; i++) {
            // 先获取sheet对象
            Sheet sheet = workbook.getSheet(i);
            // 读取sheet的总行数
            int rows = sheet.getRows();
            // 读取sheet的总列数
            int columns = sheet.getColumns();

            // 从sheet中读取每一个单元格的数据，通过行和列可以定位到每一个cell
            for (int j = 0; j < rows; j++) {

                // 创建一个List用于存放sheet中的内容
                ArrayList<String> arr = new ArrayList<String>();

                for (int k = 0; k < columns; k++) {

                    String cellContent = sheet.getCell(k, j).getContents();
                    if (j == 0) {// 标题行
                        // 标题内容为空，则后续列都不在继续读取
                        if (cellContent == "" || cellContent == null) {
                            if (k == 0) {
                                throw new Exception("内容不能为空");
                            } else {
                                columns = k - 1;
                            }
                            continue;

                        }
                    }

                    arr.add(cellContent);

                }
                if (j > 0) {
                    // 将一行数据转化为一个VehicleInfo对象
//                    VehicleInfo info = ListConvertToVehicle(arr);
//
//                    list.add(info);
                }

            }
        }

        is.close();

        return list;
    }


    public static List<VehicleInfo> readCSV(MultipartFile file)
            throws UnsupportedEncodingException, IOException, Exception {

        Reader read = new InputStreamReader(file.getInputStream(), "GBK");

        BufferedReader bReader = new BufferedReader(read);
        List<VehicleInfo> list = new ArrayList<VehicleInfo>();

        int i = 0;
        while (true) {
            String line = bReader.readLine();
            if (line == null) {
                break;
            }

            if (i > 0) {// 第一行标题不转换
                String[] arrStr = line.split(",");
                //将一行数据转化为一个对象
                List<String> arrList = Arrays.asList(arrStr);
               // VehicleInfo info = ListConvertToVehicle(arrList);

               // list.add(info);
            }
            i++;
        }

        return list;

    }

  public static void main(String[] args) throws Exception {
      // 新建list用于存放excel中的数据
      List<VehicleInfo> list = new ArrayList<VehicleInfo>();
      File file1 = new File("/Users/chenyawei/Downloads/mmall_product.xls");
      FileInputStream input = new FileInputStream(file1);
      MultipartFile multipartFile = new MockMultipartFile("file1", file1.getName(), "text/plain", IOUtils.toByteArray(input));
      // 从MultipartFile的文件中获取一个输入流
      InputStream is = multipartFile.getInputStream();

      // 创建一个Workbook的对象，即一个Workbook就一个excel
      Workbook workbook = Workbook.getWorkbook(is);

      // 在获取excel的sheet数
      int sheetNum = workbook.getNumberOfSheets();
      // 循环workbook，读取其中每一个sheet的数据
      for (int i = 0; i < sheetNum; i++) {
          // 先获取sheet对象
          Sheet sheet = workbook.getSheet(i);
          // 读取sheet的总行数
          int rows = sheet.getRows();
          // 读取sheet的总列数
          int columns = sheet.getColumns();

          // 从sheet中读取每一个单元格的数据，通过行和列可以定位到每一个cell
          for (int j = 0; j < rows; j++) {

              // 创建一个List用于存放sheet中的内容
              ArrayList<String> arr = new ArrayList<String>();

              for (int k = 0; k < columns; k++) {

                  String cellContent = sheet.getCell(k, j).getContents();
                  if (j == 0) {// 标题行
                      // 标题内容为空，则后续列都不在继续读取
                      if (cellContent == "" || cellContent == null) {
                          if (k == 0) {
                              throw new Exception("内容不能为空");
                          } else {
                              columns = k - 1;
                          }
                          continue;

                      }
                  }

                  arr.add(cellContent);

              }
              if (j > 0) {
                  // 将一行数据转化为一个VehicleInfo对象
//                    VehicleInfo info = ListConvertToVehicle(arr);
//
//                    list.add(info);
              }

          }
      }

      is.close();

  }
}
