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

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView img_sp;
    TextView txt_name_sp;
    IItemClickListener iItemClickListener;



    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    public CategoryViewHolder(View itemView) {
        super(itemView);

        img_sp = itemView.findViewById(R.id.img_sp);
        txt_name_sp = itemView.findViewById(R.id.txt_name_sp);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        iItemClickListener.onClick(v);
    }
}
