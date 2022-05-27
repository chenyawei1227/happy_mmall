package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.INoticeService;
import com.mmall.vo.NoticeDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:42
 * @Description:
 */
@Controller
@RequestMapping("/manage/notice")
public class NoticeController {

    @Autowired
    private INoticeService iNoticeService;



    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<NoticeDetailVo> detail(Integer id){
        System.out.println("要查询的公告详细id为---"+id);
        return iNoticeService.getNoticeDetail(id);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(
            @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "8") int pageSize){
        return iNoticeService.getLastNotice( pageNum, pageSize);
    }

//    @RequestMapping("list.do")
//    @ResponseBody
//    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,
//                                         @RequestParam(value = "noticeId",required = false)Integer noticeId,
//                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
//                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
//        return iNoticeService.getNoticeByKeyword();
////        return iNoticeService.getNoticeByKeyword(keyword,noticeId,pageNum,pageSize);
//    }
}
