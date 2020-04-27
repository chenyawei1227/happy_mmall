package com.mmall.vo;

import lombok.Data;

import java.util.Objects;

/**
 * Created by chenyawei on 2017/7/31.
 */
@Data
public class ShippingVo {

    //收货姓名
    private String receiverName;

    //收货固定电话
    private String receiverPhone;

    //收货移动电话
    private String receiverMobile;

    //省份
    private String receiverProvince;

    //市区
    private String receiverCity;

    //区/县
    private String receiverDistrict;

    //详细地址
    private String receiverAddress;

    //邮编
    private String receiverZip;
}
