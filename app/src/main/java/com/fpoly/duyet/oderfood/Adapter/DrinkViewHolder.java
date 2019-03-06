package com.fpoly.duyet.oderfood.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.duyet.oderfood.Interface.IItemClickListener;
import com.fpoly.duyet.oderfood.R;

/**
 * Created by duyet on 10/15/2018.
 */

public class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView img_sp;
    TextView txt_drink_name,txt_price;
    IItemClickListener iItemClickListener;
    ImageView bnt_add_to_cart,bnt_favorites;
    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    public DrinkViewHolder(View itemView) {
        super(itemView);

        img_sp = itemView.findViewById(R.id.img_sp);
        txt_drink_name = itemView.findViewById(R.id.txt_drink_name);
        txt_price = itemView.findViewById(R.id.txt_price);
        bnt_add_to_cart = itemView.findViewById(R.id.bnt_add_cart);
        bnt_favorites = itemView.findViewById(R.id.bnt_favorites);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        iItemClickListener.onClick(v);

    }
}
