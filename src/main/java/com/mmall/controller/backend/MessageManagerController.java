package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IMessageService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:43
 * @Description:
 */
@Controller
@RequestMapping("/manage/message")
public class MessageManagerController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IMessageService iMessageService;


    @RequestMapping("delete_msg.do")
    @ResponseBody
    public ServerResponse deleteMessage(HttpSession session, Integer messageId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iMessageService.deleteMessage(messageId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer messageId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iMessageService.manageMessageDetail(messageId);

        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        //填充业务
        return iMessageService.getMessageList(pageNum, pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse messageSearch(HttpSession session, String userName, Integer messageId, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iMessageService.searchMessage(userName, messageId, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

}
