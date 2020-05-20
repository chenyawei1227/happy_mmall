package com.mmall.util;


import com.sun.jndi.toolkit.url.GenericURLContext;
import sun.net.www.content.text.Generic;

import java.util.ArrayList;
import java.util.List;

/** @Author: chenyawei @Date: 2020-05-18 18:03 @Description: */
public class TwoGen<T, V> {

  private T ob1;
  private V ob2;

  public TwoGen(T o1, V o2) {
    ob1 = o1;
    ob2 = o2;
  }

  public void showTypes() {
    System.out.println("Type of T is " + ob1.getClass().getName());
    System.out.println("Type of V is " + ob2.getClass().getName());
  }

  public T getOb1() {
    return ob1;
  }

  public V getOb2() {
    return ob2;
  }
}

class SimpGen {
    public static void main(String[] args) {
        List<String> name = new ArrayList<String>();
        List<Integer> age = new ArrayList<Integer>();
        List<Number> number = new ArrayList<Number>();
        name.add("icon");
        age.add(18);
        number.add(314);

        getData(name);
        getData(age);
        getData(number);
    }

    public static void getData(List<?> data) {
        System.out.println("data :" + data.get(0));
    }
}