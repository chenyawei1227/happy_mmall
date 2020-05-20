package com.mmall.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

/** @Author: chenyawei @Date: 2020-05-14 10:51 @Description: */
public class ReadCsv {

  /*
   * author:命运的信徒 date:2019-1-15 arm:java读取.csv文件里面的所有内容
   */
  public static void main(String[] args) {
    // 1. .csv文件的路径。注意只有一个\的要改成\\
    File csv = new File("/Users/chenyawei/aaa.csv"); // CSV文件路径
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(csv));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    String line = "";
    String everyLine = "";
    try {
      List<String> allString = new ArrayList<>();
      while ((line = br.readLine()) != null) // 读取到的内容给line变量
      {
        everyLine = line;
        String[] temp = line.split(",");
        System.out.println(temp.length);
        allString.add(everyLine);
      }
      System.out.println("csv表格中所有行数：" + allString.size());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
