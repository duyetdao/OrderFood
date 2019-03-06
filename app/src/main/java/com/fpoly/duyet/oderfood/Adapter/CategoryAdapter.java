package com.fpoly.duyet.oderfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.duyet.oderfood.DrinkActivity;
import com.fpoly.duyet.oderfood.Interface.IItemClickListener;
import com.fpoly.duyet.oderfood.Model.Category;
import com.fpoly.duyet.oderfood.R;
import com.fpoly.duyet.oderfood.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by duyet on 10/15/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(context).inflate(R.layout.menu_item_layout,null);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        Picasso.with(context).load(categories.get(position).Link).into(holder.img_sp);
        holder.txt_name_sp.setText(categories.get(position).Name);

        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentCategory = categories.get(position);

                context.startActivity(new Intent(context, DrinkActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
