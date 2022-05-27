package com.mmall.dao;

import com.mmall.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:50
 * @Description:
 */
@Mapper
public interface NoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    int insertSelective(Notice record);

    List<Notice> selectList();

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    List<Notice> selectByNameAndNoticeIds();

    List<Notice> selectByNameAndNoticeIds(@Param("content")String content, @Param("noticeIdList")List<Integer> noticeIdList);

}
