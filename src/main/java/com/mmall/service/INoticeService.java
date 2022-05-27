package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.NoticeDetailVo;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:45
 * @Description:
 */
public interface INoticeService {

//    ServerResponse<PageInfo> getNoticeList(int pageNum, int pageSize);
//
//    ServerResponse<PageInfo> searchNotice(String keyword, Integer noticeId,int pageNum,int pageSize);

    ServerResponse<NoticeDetailVo> getNoticeDetail(Integer id);

    ServerResponse<PageInfo> getLastNotice(int pageNum, int pageSize);

//    ServerResponse<PageInfo> getNoticeByKeyword(String keyword,Integer noticeId,int pageNum,int pageSize);

}
