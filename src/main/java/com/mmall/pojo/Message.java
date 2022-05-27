package com.mmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:50
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private Integer id;

    private Integer userId;

    private String username;

    private String content;

    private Date createTime;

    private Date updateTime;
}
