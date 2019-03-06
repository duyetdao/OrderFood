package com.fpoly.duyet.oderfood;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.andremion.counterfab.CounterFab;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.fpoly.duyet.oderfood.Model.Drink;
import com.fpoly.duyet.oderfood.Retrofit.IDrinkShopAPI;
import com.fpoly.duyet.oderfood.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.os.Build.ID;

public class FoodDetailActivity extends AppCompatActivity {
    ImageView foodImageView;
    CollapsingToolbarLayout collapsingToolbar;
    TextView foodNameTextView;
    TextView foodPriceTextView,food_desc_text_view;
    ElegantNumberButton numberButton;
    TextView foodDescTextView;
    List<Drink> drinkList;
    Button show_comments_button;
    IDrinkShopAPI mService;
    android.support.v7.widget.Toolbar toolbar;

    String ID = "";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);


        foodImageView = findViewById(R.id.food_image_view);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        foodPriceTextView = findViewById(R.id.food_price_text_view);
        toolbar = findViewById(R.id.toolbar);
        food_desc_text_view = findViewById(R.id.food_desc_text_view);
        mService = Common.getAPI();
        foodPriceTextView.setText(Common.currentDrink.getPrice());
        toolbar.setTitle(Common.currentDrink.getName());
        food_desc_text_view.setText(new StringBuilder("Description: ").append(Common.currentDrink.getDetial())) ;
        Picasso.with(this)
                .load(Common.currentDrink.getLink())
                .into(foodImageView);




        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);



    }




}
