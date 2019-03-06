package com.fpoly.duyet.oderfood.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.fpoly.duyet.oderfood.Database.ModelDB.Cart;
import com.fpoly.duyet.oderfood.Database.ModelDB.Favorite;
import com.fpoly.duyet.oderfood.FoodDetailActivity;
import com.fpoly.duyet.oderfood.Interface.IItemClickListener;
import com.fpoly.duyet.oderfood.Model.Drink;
import com.fpoly.duyet.oderfood.R;
import com.fpoly.duyet.oderfood.Utils.Common;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by duyet on 10/15/2018.
 */

public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder> {
    Context context;
    List<Drink> drinkList;



    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @Override
    public DrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(context).inflate(R.layout.drink_item_layout,null);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DrinkViewHolder holder, final int position) {
        holder.txt_price.setText(new StringBuilder("$").append(drinkList.get(position).Price));
        holder.txt_drink_name.setText(drinkList.get(position).Name);
        holder.bnt_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showaddtocart(position);
            }
        });


        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(holder.img_sp);

        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentDrink = drinkList.get(position);
                context.startActivity(new Intent(context, FoodDetailActivity.class));


            }
        });
        //favorite
        if (Common.favoriteRepository.isFavorite(Integer.parseInt(drinkList.get(position).ID))==1)
            holder.bnt_favorites.setImageResource(R.drawable.ic_favorite_black_24dp);
        else
            holder.bnt_favorites.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        holder.bnt_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.favoriteRepository.isFavorite(Integer.parseInt(drinkList.get(position).ID))!=1){
                    addorRemoToFavorite(drinkList.get(position),true);
                    holder.bnt_favorites.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
                else
                {
                    addorRemoToFavorite(drinkList.get(position),false);
                    holder.bnt_favorites.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
            }
        });


    }

    private void addorRemoToFavorite(Drink drink, boolean isAdd) {

        Favorite favorite = new Favorite();
        favorite.id = drink.ID;
        favorite.link = drink.Link;
        favorite.name = drink.Name;
        favorite.price = drink.Price;
        favorite.menuId = drink.MenuId;

        if (isAdd)
            Common.favoriteRepository.insertFav(favorite);
        else
            Common.favoriteRepository.delete(favorite);
    }

    private void showaddtocart(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart,null);
        ImageView img_cart_sp = itemView.findViewById(R.id.img_cart_sp);
        final ElegantNumberButton txt_count = itemView.findViewById(R.id.txt_count);
        TextView txt_sp_dialog = itemView.findViewById(R.id.txt_cart_sp_name);

        EditText ext_commnent = itemView.findViewById(R.id.edt_comment);

        RadioButton rdi_sizeM = itemView.findViewById(R.id.rdi_sizeM);
        RadioButton rdi_sizeL = itemView.findViewById(R.id.rdi_sizeL);

        rdi_sizeM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Common.sizeofCup=0;
                }
            }
        });
        rdi_sizeL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Common.sizeofCup=1;
                }
            }
        });

        RadioButton rdi_sugar_100 = itemView.findViewById(R.id.rdi_gugar_100);
        RadioButton rdi_sugar_70 = itemView.findViewById(R.id.rdi_gugar_70);
        RadioButton rdi_sugar_50 = itemView.findViewById(R.id.rdi_gugar_50);
        RadioButton rdi_sugar_30 = itemView.findViewById(R.id.rdi_gugar_30);
        RadioButton rdi_sugar_free = itemView.findViewById(R.id.rdi_gugar_free);

        rdi_sugar_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar=100;
                }
            }
        });
        rdi_sugar_70.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar=70;
                }
            }
        });
        rdi_sugar_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar=50;
                }
            }
        });
        rdi_sugar_30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar=30;
                }
            }
        });
        rdi_sugar_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar=0;
                }
            }
        });


        RadioButton rdi_ice_100 = itemView.findViewById(R.id.rdi_ice_100);
        RadioButton rdi_ice_70 = itemView.findViewById(R.id.rdi_ice_70);
        RadioButton rdi_ice_50 = itemView.findViewById(R.id.rdi_ice_50);
        RadioButton rdi_ice_30 = itemView.findViewById(R.id.rdi_ice_30);
        RadioButton rdi_ice_free = itemView.findViewById(R.id.rdi_ice_free);

        rdi_ice_30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Common.ice = 30;
            }
        });
        rdi_ice_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Common.ice = 50;
            }
        });
        rdi_ice_70.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Common.ice = 70;
            }
        });
        rdi_ice_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Common.ice = 100;
            }
        });
        rdi_ice_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Common.ice = 0;
            }
        });

        RecyclerView recycler_topping = itemView.findViewById(R.id.recycler_topping);
        recycler_topping.setLayoutManager(new LinearLayoutManager(context));
        recycler_topping.setHasFixedSize(true);

        MultiChoiceAdapter adapter = new MultiChoiceAdapter(context, Common.toping_list);
        recycler_topping.setAdapter(adapter);

        //set data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_cart_sp);
        txt_sp_dialog.setText(drinkList.get(position).Name);
        builder.setView(itemView);
        builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (Common.sizeofCup == -1)
                {
                    Toast.makeText(context, "Please choose size of cup", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Common.sugar == -1)
                {
                    Toast.makeText(context, "Please choose sugar", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Common.ice == -1)
                {
                    Toast.makeText(context, "Please choose ice", Toast.LENGTH_SHORT).show();
                    return;
                }
                showConfirmDialog(position,txt_count.getNumber());
                dialogInterface.dismiss();
            }
        });
        builder.show();




    }

    private void showConfirmDialog(final int position, final String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout,null);

        //view
        ImageView img_cart_sp = itemView.findViewById(R.id.img_sp);
        final TextView txt_sp_dialog = itemView.findViewById(R.id.txt_cart_sp_name);
        TextView txt_cart_sp_price = itemView.findViewById(R.id.txt_cart_sp_price);
        TextView txt_sugar = itemView.findViewById(R.id.txt_surgar);
        TextView txt_ice = itemView.findViewById(R.id.txt_ice);
        final TextView txt_topping_extra = itemView.findViewById(R.id.txt_topping_extras);

        //set data
        Picasso.with(context).load(drinkList.get(position).Link).into(img_cart_sp);
        txt_sp_dialog.setText(new StringBuilder(drinkList.get(position).Name).append(" x")
         .append(Common.sizeofCup==0 ? " Size M ":" Size L ")
        .append(number).toString());

        txt_ice.setText(new StringBuilder("Ice: ").append(Common.ice).append("%").toString());
        txt_sugar.setText(new StringBuilder("Sugar: ").append(Common.sugar).append("%").toString());

        double price =(Double.parseDouble(drinkList.get(position).Price)) * Double.parseDouble(number) + Common.toppingPrice;
        if (Common.sizeofCup == 1)
            price+=(3.0*Double.parseDouble(number));

        StringBuilder topping_final_comment =new StringBuilder("");
        for (String line:Common.toppingAdded)
            topping_final_comment.append(line).append("\n");
        txt_topping_extra.setText(topping_final_comment);
        final double finalPrice = Math.round(price);
        txt_cart_sp_price.setText(new StringBuilder("$").append(finalPrice));
        AlertDialog.Builder builder1 = builder.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                try {
                    Cart cartItem = new Cart();
                    cartItem.name = drinkList.get(position).Name;
                    cartItem.amount = Integer.parseInt(number);
                    cartItem.sugar = Common.sugar;
                    cartItem.ice = Common.ice;
                    cartItem.price = finalPrice;
                    cartItem.size = Common.sizeofCup;
                    cartItem.toppingExtras = txt_topping_extra.getText().toString();
                    cartItem.link = drinkList.get(position).Link;

                    Common.cartRepository.insertToCart(cartItem);

                    Log.d("DTD_DEBUG", new Gson().toJson(cartItem));
                    Toast.makeText(context, "Save items to cart", Toast.LENGTH_SHORT).show();
                }

                catch(Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setView(itemView);
        builder.show();

    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
