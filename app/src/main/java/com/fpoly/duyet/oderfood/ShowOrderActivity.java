package com.fpoly.duyet.oderfood;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.fpoly.duyet.oderfood.Adapter.OrderAdapter;
import com.fpoly.duyet.oderfood.Database.ModelDB.Cart;
import com.fpoly.duyet.oderfood.Model.Order;
import com.fpoly.duyet.oderfood.Retrofit.IDrinkShopAPI;
import com.fpoly.duyet.oderfood.Utils.Common;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowOrderActivity extends AppCompatActivity {
    IDrinkShopAPI mserver;
    RecyclerView recycler_order;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        mserver = Common.getAPI();
        recycler_order = findViewById(R.id.recycler_orders);
        recycler_order.setLayoutManager(new LinearLayoutManager(this));
        recycler_order.setHasFixedSize(true);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        loadOrder("0");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.order_new){
                    loadOrder("0");
                }else
                if (item.getItemId() == R.id.order_cancel){
                    loadOrder("-1");
                }else if (item.getItemId() == R.id.order_prosseing){
                    loadOrder("1");
                }else if (item.getItemId() == R.id.order_shipping){
                    loadOrder("2");
                }else if (item.getItemId() == R.id.order_shipped){
                    loadOrder("3");
                }
                return true;
            }
        });


    }


    private void loadOrder(String statuscode) {

           compositeDisposable.add(mserver.getOrder(Common.currentUser.getPhone(),statuscode).observeOn(AndroidSchedulers.mainThread())
           .subscribeOn(Schedulers.io())
           .subscribe(new Consumer<List<Order>>() {
               @Override
               public void accept(List<Order> orders) throws Exception {
                   displayorder(orders);
               }
           }));

    }

    private void displayorder(List<Order> orders) {
      OrderAdapter adapter = new OrderAdapter(this,orders);
      recycler_order.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrder("0");
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
