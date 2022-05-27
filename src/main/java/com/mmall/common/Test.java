package com.mmall.common;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyawei
 * @Date: 2020/8/31  3:06 下午
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(9633);
        BigDecimal b = new BigDecimal(8837);
        BigDecimal multiply = a.subtract(b).divide(b, 3, BigDecimal.ROUND_HALF_UP);
//multiply.multiply(new BigDecimal(100))
        System.out.println(multiply.multiply(new BigDecimal(100)).setScale(1,BigDecimal.ROUND_HALF_UP));
    }
}
