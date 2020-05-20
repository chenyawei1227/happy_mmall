package com.mmall.util;

import com.mmall.pojo.User;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: chenyawei
 * @Date: 2020-05-18  09:13
 * @Description:
 */
public class Java8_removeIf {

  public static void main(String[] args) {
      List<User> userpojoList = new ArrayList<>();
      User user1 = new User(1,"name1","psd1");
      User user2 = new User(2," ","psd2");
      User user3 = new User(3,"name3","psd3");
      userpojoList.add(user1);
      userpojoList.add(user2);
      userpojoList.add(user3);
      userpojoList.removeIf(t -> {
          Object invoke = null;
          try {
              Method method = t.getClass().getMethod("getUsername");
              invoke = method.invoke(t);
          } catch (NoSuchMethodException e) {
              e.printStackTrace();
          } catch (IllegalAccessException e) {
              e.printStackTrace();
          } catch (InvocationTargetException e) {
              e.printStackTrace();
          }
          return invoke == null || StringUtils.isBlank((String)invoke);
      });
    userpojoList.stream()
        .forEach(
            s -> {
                System.out.println(s.getUsername() + "/" + s.getPassword());
            });
  }
}
