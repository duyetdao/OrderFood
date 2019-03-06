package com.fpoly.duyet.oderfood.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.fpoly.duyet.oderfood.Database.ModelDB.Cart;
import com.fpoly.duyet.oderfood.R;
import com.fpoly.duyet.oderfood.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by duyet on 11/5/2018.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>{
    Context context;
    List<Cart> cartList;

    public OrderDetailAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public OrderDetailAdapter.OrderDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.order_detail_layout,parent,false);
        return new OrderDetailAdapter.OrderDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderDetailAdapter.OrderDetailViewHolder holder, final int position) {
        Picasso.with(context)
                .load(cartList.get(position).link)
                .into(holder.img_sp);
        holder.txt_price.setText(new StringBuilder("$").append(cartList.get(position).price));
        holder.txt_sp_name.setText(new StringBuilder(cartList.get(position).name)
                .append(" x")
                .append(cartList.get(position).amount)
                .append(" ")
                .append(cartList.get(position).size == 0 ? " Size M":" Size L"));
        holder.txt_sugar_ice.setText(new StringBuilder("Sugar: ")
                .append(cartList.get(position).sugar).append("%").append("\n")
                .append("Ice: ").append(cartList.get(position).ice)
                .append("%").toString());




        //auto save item when user change amount


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_sp;
        TextView txt_sp_name,txt_sugar_ice,txt_price;

        public RelativeLayout view_background;
        public LinearLayout view_foreground;

        public OrderDetailViewHolder(View itemView) {
            super(itemView);
            img_sp = itemView.findViewById(R.id.img_sp);
            txt_sp_name = itemView.findViewById(R.id.txt_sp_name);
            txt_sugar_ice = itemView.findViewById(R.id.txt_sugar_ice);
            txt_price = itemView.findViewById(R.id.txt_price);

            view_background = itemView.findViewById(R.id.view_background);
            view_foreground = itemView.findViewById(R.id.view_foreground);

        }
    }

}

