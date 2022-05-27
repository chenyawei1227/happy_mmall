package com.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:35
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDetailVo {
    private Integer id;
    private Integer user_id;
    private String username;
    private String content;
    private String createTime;
    private String updateTime;
}
