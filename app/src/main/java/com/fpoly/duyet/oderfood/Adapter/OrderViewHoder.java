package com.fpoly.duyet.oderfood.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fpoly.duyet.oderfood.Interface.IItemClickListener;
import com.fpoly.duyet.oderfood.R;

/**
 * Created by duyet on 11/5/2018.
 */

public class OrderViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txt_order_id,txt_order_price,txt_order_address,txt_order_commnet,txt_order_status;
    IItemClickListener iItemClickListener;

    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    public OrderViewHoder(View itemView) {
        super(itemView);
        txt_order_id = itemView.findViewById(R.id.txt_order_id);
        txt_order_price = itemView.findViewById(R.id.txt_order_price);
        txt_order_address = itemView.findViewById(R.id.txt_order_address);
        txt_order_commnet = itemView.findViewById(R.id.txt_order_comment);
        txt_order_status = itemView.findViewById(R.id.txt_order_status);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        iItemClickListener.onClick(view);

    }
}
