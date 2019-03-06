package com.fpoly.duyet.oderfood.Model;

/**
 * Created by duyet on 11/15/2018.
 */

public class OrderResult {
    public  int orderId;
    public String orderDate;
    public int orderStatus;
    public float orderPrice;
    public String orderDetail,orderComment,orderAddress,userPhone;

    public OrderResult() {
    }

    public OrderResult(int orderId, String orderDate, int orderStatus, float orderPrice, String orderDetail, String orderComment, String orderAddress, String userPhone) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderPrice = orderPrice;
        this.orderDetail = orderDetail;
        this.orderComment = orderComment;
        this.orderAddress = orderAddress;
        this.userPhone = userPhone;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
