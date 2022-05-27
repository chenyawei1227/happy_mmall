package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.MessageMapper;
import com.mmall.pojo.Message;
import com.mmall.service.IMessageService;
import com.mmall.util.DateTimeUtil;
import com.mmall.vo.MessageDetailVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:48
 * @Description:
 */
@Service("iMessageService")
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public ServerResponse<String> deleteMessage(Integer messageId) {
        if (messageId==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int rowCount = messageMapper.deleteByPrimaryKey(messageId);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("留言成功删除");
        }
        return ServerResponse.createByErrorMessage("留言删除失败");
    }

    @Override
    public ServerResponse<MessageDetailVo> manageMessageDetail(Integer messageId) {
        if(messageId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Message message = messageMapper.selectByPrimaryKey(messageId);
        if(message == null){
            return ServerResponse.createByErrorMessage("此留言不存在");
        }
        MessageDetailVo messageDetailVo = assembleMessageDetailVo(message);
        return ServerResponse.createBySuccess(messageDetailVo);
    }

    private MessageDetailVo assembleMessageDetailVo(Message message){
        MessageDetailVo messageDetailVo = new MessageDetailVo();
        messageDetailVo.setId(message.getId());
        messageDetailVo.setUser_id(message.getUserId());
        messageDetailVo.setUsername(message.getUsername());
        messageDetailVo.setContent(message.getContent());
        messageDetailVo.setCreateTime(DateTimeUtil.dateToStr(message.getCreateTime()));
        messageDetailVo.setUpdateTime(DateTimeUtil.dateToStr(message.getUpdateTime()));
        return messageDetailVo;
    }

    @Override
    public ServerResponse<PageInfo> getMessageList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Message> messageList = messageMapper.selectList();
        List<MessageDetailVo> messageDetailVoList = Lists.newArrayList();
        if (CollectionUtils.size(messageList) > 0){
            messageList.forEach(message -> {
                MessageDetailVo vo = assembleMessageDetailVo(message);
                messageDetailVoList.add(vo);
            });
        }
        PageInfo pageResult = new PageInfo(messageDetailVoList);
        pageResult.setList(messageDetailVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<PageInfo> searchMessage(String userName, Integer messageId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if (StringUtils.isNotBlank(userName)){
            userName = new StringBuilder().append("%").append(userName).append("%").toString();
        }
        List<Message> messageList = messageMapper.selectByNameAndMessageId(userName,messageId);
        List<MessageDetailVo> messageDetailVoList = Lists.newArrayList();
        if (CollectionUtils.size(messageList) > 0){
            messageList.forEach(message -> {
                MessageDetailVo vo = assembleMessageDetailVo(message);
                messageDetailVoList.add(vo);
            });
        }
        PageInfo pageInfo = new PageInfo(messageDetailVoList);
        pageInfo.setList(messageDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
