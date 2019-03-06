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

import com.fpoly.duyet.oderfood.Database.ModelDB.Favorite;
import com.fpoly.duyet.oderfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by duyet on 10/21/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    Context context;
    List<Favorite> favorites;

    public FavoriteAdapter(Context context, List<Favorite> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.fav_item_layout,parent,false);

        return new FavoriteViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        Picasso.with(context).load(favorites.get(position).link).into(holder.img_sp);
        holder.txt_price.setText(new StringBuilder("Price: $").append(favorites.get(position).price).toString());
        holder.txt_sp_name.setText(new StringBuilder("Name: ").append(favorites.get(position).name));

    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_sp;
        TextView txt_sp_name,txt_price;

        public RelativeLayout view_background;
        public LinearLayout view_foreground;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            img_sp=itemView.findViewById(R.id.img_sp);
            txt_sp_name=itemView.findViewById(R.id.txt_sp_name);
            txt_price=itemView.findViewById(R.id.txt_price);

            view_background = itemView.findViewById(R.id.view_background);
            view_foreground = itemView.findViewById(R.id.view_foreground);
        }
    }
    public void removeItem(int position)
    {
        favorites.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(Favorite item,int position)
    {
        favorites.add(position,item);
        notifyItemInserted(position);
    }
}
