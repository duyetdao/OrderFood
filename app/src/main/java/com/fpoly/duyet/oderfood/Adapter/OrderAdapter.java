package com.fpoly.duyet.oderfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.duyet.oderfood.Interface.IItemClickListener;
import com.fpoly.duyet.oderfood.Model.Order;
import com.fpoly.duyet.oderfood.OrderDetailActivity;
import com.fpoly.duyet.oderfood.R;
import com.fpoly.duyet.oderfood.Utils.Common;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by duyet on 11/5/2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHoder>{

    Context context;
    List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public OrderViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false);
        return new OrderViewHoder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHoder holder, final int position) {
        holder.txt_order_id.setText(new StringBuilder("#").append(orderList.get(position).getOrderId()));
        holder.txt_order_price.setText(new StringBuilder("$").append(orderList.get(position).getOrderPrice()));
        holder.txt_order_address.setText(new StringBuilder("Addresss: ").append(orderList.get(position).getOrderAddress()));
        holder.txt_order_commnet.setText(new StringBuilder("Comment: ").append(orderList.get(position).getOrderComment()));
        holder.txt_order_status.setText(new StringBuilder("Order Status: ").append(Common.convertCodeToSatus(orderList.get(position).getOrderStatus())));

        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentOrder = orderList.get(position);
                context.startActivity(new Intent(context, OrderDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}