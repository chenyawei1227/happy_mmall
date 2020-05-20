package com.mmall.util;


import org.apache.commons.lang3.time.DateUtils;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.util.Calendar;

/**
 * Created by chenyawei on 2019/3/10.
 */
public class TestMain {

    public static void main(String[] args) {
//        String aa = "2017/4/25";
//        aa = aa.replaceAll("/","-");
//        Date date = Date.valueOf(aa);
//        System.out.println(date.toString());

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
    }


}
