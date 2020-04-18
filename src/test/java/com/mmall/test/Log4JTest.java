package com.mmall.test;

import com.mmall.dao.CartMapper;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by chenyawei on 2017/12/26.
 */
public class Log4JTest {

    private final Logger logger = Logger.getLogger(this.getClass());
    private CartMapper cartMapper;

    @Test
    public void test1() {
        try {
            cartMapper.insert(null);

        } catch (Exception se) {
            System.out.println(this.getClass().getName() + "common.createTaskInfo");
            //logger.warn(this.getClass().getName() + "common.createTaskInfo",se);
            System.out.println(this.getClass().getName() + "成功反馈给推送服务器失败消息：来源sip.js:exports.get_msg_bysip");
        }

    }
}
