package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by chenyawei on 2017/7/5.
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {


    @Autowired
    private IUserService iUserService;

    @RequestMapping(value="login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
//                session.setAttribute(Const.CURRENT_USER,user);

                //新增redis共享cookie，session的方式
                CookieUtil.writeLoginToken(httpServletResponse,session.getId());
                RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);

                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
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
