package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by chenyawei on 2017/7/20.
 */
@Data
public class CartProductVo {
    //封装了产品和购物车到一个抽象对象

    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;//购物车中此商品的数量
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private String productImageHost;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    private Integer productChecked;//此商品是否勾选

    private String limitQuantity;//限制数量的一个返回结果


}
