package com.mmall.service.base;

import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.*;
import com.mmall.pojo.*;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.OrderItemVo;
import com.mmall.vo.OrderVo;
import com.mmall.vo.ShippingVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @Author: chenyawei
 * @Date: 2020-05-26  12:11
 * @Description:
 */
public class OrderRunner implements Callable<ServerResponse> {

    private Integer userId;
    private Integer shippingId;

    public OrderRunner(Integer userId,Integer shippingId){
        this.userId = userId;
        this.shippingId = shippingId;
    }

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse call() throws Exception {
        //从购物车中获取数据
        List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId);

        //计算这个订单的总价
        ServerResponse serverResponse = this.getCartOrderItem(userId, cartList);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);

        //生成订单
        Order order = this.assembleOrder(userId, shippingId, payment);
        if (order == null) {
            return ServerResponse.createByErrorMessage("生成订单错误");
        }

        if (CollectionUtils.isEmpty(orderItemList)) {
            return ServerResponse.createByErrorMessage("购物车为空");
        }

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }

        //mybatis 批量插入
        orderItemMapper.batchInsert(orderItemList);

        //生成成功，减少我们产品的库存
        this.reduceProductStock(orderItemList);
        //清空购物车
        this.cleanCart(cartList);

        //返回给前端数据
        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);

        return ServerResponse.createBySuccess(orderVo);
    }
    private List<OrderVo> assembleOrderVoList(List<Order> orderList, Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order order : orderList) {
            List<OrderItem> orderItemList = Lists.newArrayList();
            if (userId == null) {
                //todo 管理员查询时候，不需要传userId
                orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            } else {
                orderItemList = orderItemMapper.getByOrderNoUserId(order.getOrderNo(), userId);
            }
            OrderVo orderVo = assembleOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }


    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());

        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if (shipping != null) {
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(this.assembleShippingVo(shipping));
        }
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));

        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.bytenote.cn/"));

        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    private OrderItemVo assembleOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }


    private ShippingVo assembleShippingVo(Shipping shipping) {
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        return shippingVo;
    }

    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        Order order = new Order();
        long orderNo = this.generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);//运费,单位是元,留着以后扩展用的
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(payment);
        order.setUserId(userId);
        order.setShippingId(shippingId);
        //发货时间等等，在发货的时候更新
        //付款时间等等，在付款的时候更新
        int rowCount = orderMapper.insert(order);
        if (rowCount > 0) {
            return order;
        }
        return null;
    }


    /*
     * 生成订单号，这个方法是有瑕疵的，如果并发请求时，有可能两个客户同时下单生成相同的
     * 订单号，造成一个用户的下单失败
     * */
    private long generateOrderNo() {
        long currentTime = System.currentTimeMillis();
        //return currentTime+currentTime%10;
        return currentTime + new Random().nextInt(100);
    }


    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    private ServerResponse getCartOrderItem(Integer userId, List<Cart> cartList) {
        List<OrderItem> orderItemList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(cartList)) {
            return ServerResponse.createByErrorMessage("购物车为空");
        }

        //校验购物车的数据
        for (Cart cartItem : cartList) {
            OrderItem orderItem = new OrderItem();
            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
                return ServerResponse.createByErrorMessage("产品" + product.getName() + "不是在线售卖状态");
            }

            //校验库存
            if (cartItem.getQuantity() > product.getStock()) {
                return ServerResponse.createByErrorMessage("产品" + product.getName() + "库存不足");
            }

            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity()));
            orderItemList.add(orderItem);
        }
        return ServerResponse.createBySuccess(orderItemList);
    }

}
