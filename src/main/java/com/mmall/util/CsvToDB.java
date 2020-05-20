package com.mmall.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Scanner;

/**
 * @Author: chenyawei
 * @Date: 2020-05-13  15:15
 * @Description:
 */
public class CsvToDB {

        private static Connection con;
        static Scanner in;

        public static void main(String[] args) throws FileNotFoundException, SQLException
        {

            long startTime = System.currentTimeMillis();
            File file = new File("/Users/chenyawei/aaa.csv");
           // System.out.println(file.exists());

            in = new Scanner(file);

            getConnect();
            System.out.println("数据库连接成功");
            insert_data(in);

            long EndTime = System.currentTimeMillis();
            long time = (EndTime - startTime) / 1000;

            System.out.println("导入数据共用时：" + time);
        }

        private static void insert_data(Scanner in) throws SQLException
        {
            int count = 0;
            String sql = "insert into Student (id,name,age)" + "values(?,?,?)";

            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(sql);
            System.out.println(in.hasNext());
            in.next();
            while (in.hasNext())
            {
                String temp1 = in.nextLine();
                String[] temp = temp1.split(",");
                int length = temp.length;

                if(length < 2)
                    continue;
                try{
                    pstmt.setString(1, temp[0]);
                }catch (Exception e){
                    pstmt.setString(1, "20");
                }
                try{
                    pstmt.setString(2, temp[1]);
                }catch (Exception e){
                    pstmt.setString(2, "20");
                }
                try{
                    pstmt.setString(3, temp[2]);
                }catch (Exception e){
                    pstmt.setString(3, "20");
                }
                pstmt.setBigDecimal(3,temp[2].equals("")?null: BigDecimal.valueOf(Double.valueOf(temp[2])));
                pstmt.setInt(3,0);
//                pstmt.setTimestamp(3, Timestamp.valueOf(DateUtils.formateDate(new java.util.Date("yyyy-MM-dd HH:mm:ss"))));
//                if(length < 3)
//                    continue;
//
//                for (int i=0;i<length;i++)
//                {
//                    try{
//                        pstmt.setString(i+1, temp[i]);
//                    }catch (Exception e){
//                        pstmt.setString(i+1, "20");
//                    }
//
//                }

                pstmt.addBatch();

                count++;

                if (count == 20000)
                {
                    count = execute(pstmt, count);
                }
            }
            pstmt.executeBatch();
            con.commit();

        }

        public static int execute(PreparedStatement pstmt, int count) throws SQLException
        {

            pstmt.executeBatch();
            con.commit();
            return 0;

        }

        private static void getConnect()
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teachYourselfSQL?characterEncoding=utf-8", "mmall", "password123");
            }
            catch (ClassNotFoundException | SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

}
