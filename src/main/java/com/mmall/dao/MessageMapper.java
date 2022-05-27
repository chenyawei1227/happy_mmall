package com.mmall.dao;

import com.mmall.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:50
 * @Description:
 */
@Mapper
public interface MessageMapper {

    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> selectList();

    List<Message> selectByNameAndMessageId(@Param("userName")String userName, @Param("messageId") Integer messageId);
}