package com.fpoly.duyet.oderfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.duyet.oderfood.Adapter.OrderDetailAdapter;
import com.fpoly.duyet.oderfood.Database.ModelDB.Cart;
import com.fpoly.duyet.oderfood.Retrofit.IDrinkShopAPI;
import com.fpoly.duyet.oderfood.Utils.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    TextView txt_order_id,txt_order_price,txt_order_address,txt_order_commnet,txt_order_status;
    Button btn_cancel;
    RecyclerView recycler_ordetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        txt_order_id = findViewById(R.id.txt_order_id);
        txt_order_price =findViewById(R.id.txt_order_price);
        txt_order_address = findViewById(R.id.txt_order_address);
        txt_order_commnet = findViewById(R.id.txt_order_comment);
        txt_order_status = findViewById(R.id.txt_order_status);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder();
            }
        });

        recycler_ordetail = findViewById(R.id.recycler_deatil);
        recycler_ordetail.setLayoutManager(new LinearLayoutManager(this));
        recycler_ordetail.setHasFixedSize(true);

        txt_order_id.setText(new StringBuilder("#").append(Common.currentOrder.getOrderId()));
        txt_order_price.setText(new StringBuilder("$").append(Common.currentOrder.getOrderPrice()));
       txt_order_address.setText(new StringBuilder("Address: ").append(Common.currentOrder.getOrderAddress()));
        txt_order_commnet.setText(new StringBuilder("Comment: ").append(Common.currentOrder.getOrderComment()));
       txt_order_status.setText(new StringBuilder("Order Status: ").append(Common.convertCodeToSatus(Common.currentOrder.getOrderStatus())));

       displayorderDetail();
    }

    private void cancelOrder() {
        IDrinkShopAPI iDrinkShopAPI =Common.getAPI();
        iDrinkShopAPI.cancelOrder(String.valueOf(Common.currentOrder.getOrderId()),
                Common.currentUser.getPhone())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(OrderDetailActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                        if (response.body().contains("Order has been cancelled"))
                            finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("DEBUG",t.getMessage());
                    }
                });
    }

    private void displayorderDetail() {
        List<Cart> orderDetail = new Gson().fromJson(Common.currentOrder.getOrderDetail(),
                new TypeToken<List<Cart>>(){}.getType());
        recycler_ordetail.setAdapter(new OrderDetailAdapter(this,orderDetail));
    }
}
