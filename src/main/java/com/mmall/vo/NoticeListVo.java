package com.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenyawei
 * @Date: 2020-06-14  14:38
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeListVo {
    private Integer id;
    private String content;
    private String create_time;
}
