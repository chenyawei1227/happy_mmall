package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.MessageDetailVo;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:45
 * @Description:
 */
public interface IMessageService {
    ServerResponse<String> deleteMessage(Integer messageId);

    ServerResponse<MessageDetailVo> manageMessageDetail(Integer messageId);

    ServerResponse<PageInfo> getMessageList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchMessage(String userName, Integer messageId, int pageNum, int pageSize);

}
