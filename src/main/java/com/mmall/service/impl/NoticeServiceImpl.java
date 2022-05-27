package com.mmall.service.impl;

import avro.shaded.com.google.common.collect.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.dao.NoticeMapper;
import com.mmall.pojo.Notice;
import com.mmall.service.INoticeService;
import com.mmall.util.DateTimeUtil;
import com.mmall.vo.NoticeDetailVo;
import com.mmall.vo.NoticeListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:47
 * @Description:
 */
@Service("iNoticeService")
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;


    @Override
    public ServerResponse<NoticeDetailVo> getNoticeDetail(Integer id){
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        if(notice == null){
            return ServerResponse.createByErrorMessage("该公告不存在！");
        }
        NoticeDetailVo noticeDetailVo = assembleNoticeDetailVo(notice);
        return ServerResponse.createBySuccess(noticeDetailVo);
    }

    private NoticeDetailVo assembleNoticeDetailVo(Notice notice){
        NoticeDetailVo noticeDetailVo = new NoticeDetailVo();
        noticeDetailVo.setId(notice.getId());
        noticeDetailVo.setUser_id(notice.getUserId());
        noticeDetailVo.setContent(notice.getContent());
        noticeDetailVo.setCreateTime(DateTimeUtil.dateToStr(notice.getCreateTime()));
        noticeDetailVo.setUpdateTime(DateTimeUtil.dateToStr(notice.getUpdateTime()));
        return noticeDetailVo;
    }

    private NoticeListVo assembleNoticeListVo(Notice notice){
        NoticeListVo noticeListVo = new NoticeListVo();
        noticeListVo.setId(notice.getId());
        noticeListVo.setContent(notice.getContent());
        noticeListVo.setCreate_time(DateTimeUtil.dateToStr(notice.getCreateTime()));
        return noticeListVo;
    }

    @Override
    public ServerResponse<PageInfo> getLastNotice(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "8") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Notice> noticeList = noticeMapper.selectList();
        List<NoticeListVo> noticeListVoList = Lists.newArrayList();
        for (Notice noticeItem : noticeList){
            NoticeListVo noticeListVo = assembleNoticeListVo(noticeItem);
            noticeListVoList.add(noticeListVo);
        }
        PageInfo pageResult = new PageInfo(noticeList);
        pageResult.setList(noticeListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }



//    public ServerResponse<PageInfo> getNoticeByKeyword(String keyword, Integer noticeId,int pageNum, int pageSize){
////        if(StringUtils.isBlank(keyword) && noticeId == null){
////            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
////        }
//        List<Integer> noticeIdList = new ArrayList<Integer>();
//
//
//        PageHelper.startPage(pageNum,pageSize);
//        List<Notice> noticeList = noticeMapper.selectByNameAndNoticeIds(StringUtils.isBlank(keyword)?null:keyword,noticeIdList.size()==0?null:noticeIdList);
//
//        List<NoticeListVo> noticeListVoList = Lists.newArrayList();
//        for(Notice notice : noticeList){
//            NoticeListVo noticeListVo = assembleNoticeListVo(notice);
//            noticeListVoList.add(noticeListVo);
//        }
//
//        PageInfo pageInfo = new PageInfo(noticeList);
//        pageInfo.setList(noticeListVoList);
//        return ServerResponse.createBySuccess(pageInfo);
//    }
}
