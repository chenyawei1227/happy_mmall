package com.mmall.controller.backend;

import com.google.gson.JsonObject;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dto.UserDto;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by chenyawei on 2017/7/5.
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(@RequestBody UserDto userDto, HttpSession session) {

        ServerResponse<User> response = iUserService.login(userDto.getUsername(), userDto.getPassword());
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN) {
                //说明登陆者是管理员
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            } else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登陆");
            }
        }
        return response;
    }

    @RequestMapping(value = "loginJson.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> loginJson(@RequestBody User user, HttpSession session) {

        System.out.println(user.getUsername() + "--" + user.getPassword());
        return ServerResponse.createBySuccess(user);
    }

    @RequestMapping(value = "loginJsonString.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> loginJsonString(@RequestBody String username) {

//        Map<String,Object> maps =
        // JsonObject jsonObject = JsonUtil.string2Obj(user,JsonObject.class);
        System.out.println(username);
        //System.out.println(jsonObject.get("username") + "---" + jsonObject.get("password"));
        return ServerResponse.createBySuccess();
    }
}
