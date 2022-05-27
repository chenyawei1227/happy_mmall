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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageListVo {
    private Integer id;
    private String username;
    private String content;
}
