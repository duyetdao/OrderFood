package com.fpoly.duyet.oderfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.braintreepayments.api.PaymentMethod;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.fpoly.duyet.oderfood.Adapter.CartAdapter;
import com.fpoly.duyet.oderfood.Adapter.FavoriteAdapter;
import com.fpoly.duyet.oderfood.Database.ModelDB.Cart;
import com.fpoly.duyet.oderfood.Database.ModelDB.Favorite;
import com.fpoly.duyet.oderfood.Model.DataMessage;
import com.fpoly.duyet.oderfood.Model.MyResponse;
import com.fpoly.duyet.oderfood.Model.OrderResult;
import com.fpoly.duyet.oderfood.Model.Token;
import com.fpoly.duyet.oderfood.Retrofit.IDrinkShopAPI;
import com.fpoly.duyet.oderfood.Retrofit.IFCMService;
import com.fpoly.duyet.oderfood.Utils.Common;
import com.fpoly.duyet.oderfood.Utils.RecyclerItemTouchHelper;
import com.fpoly.duyet.oderfood.Utils.RecyclerItemTouchHelperListener;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener{

    private static final int PAYMENT_REQUEST_CODE = 7777;
    RecyclerView recyclercart;
    Button btn_place_order;
    CompositeDisposable compositeDisposable;
    List<Cart> cartList = new ArrayList<>();
    CartAdapter cartAdapter;
    RelativeLayout rootLayout;
    IDrinkShopAPI mService;
    IDrinkShopAPI mServiceScalars;
    String token,amount,ordrAddress,orderComment;
    HashMap<String,String> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        compositeDisposable = new CompositeDisposable();
        mService = Common.getAPI();
        mServiceScalars = Common.getScalarsAPI();

        recyclercart = findViewById(R.id.recycler_cart);
        recyclercart.setLayoutManager(new LinearLayoutManager(this));
        recyclercart.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclercart);


        btn_place_order = findViewById(R.id.btn_plcae_order);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartList.size() > 0) {
                    placeOrder();
                } else {
                    Toast.makeText(CartActivity.this, "Your orders is empty!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        rootLayout = findViewById(R.id.rootLayout);

        loadCartItem();
        loadToken();


    }

    private void loadToken() {
        final android.app.AlertDialog waitingdialog = new SpotsDialog(CartActivity.this);
        waitingdialog.show();
        waitingdialog.setMessage("Please wait...");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Common.API_TOKEN_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                waitingdialog.dismiss();
                btn_place_order.setEnabled(false);
                Toast.makeText(CartActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                waitingdialog.dismiss();

                token = responseString;
                btn_place_order.setEnabled(true);


            }
        });
    }

    private void placeOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Submit Order");
        View submitother = LayoutInflater.from(this).inflate(R.layout.submit_order_layout,null);
        final EditText edt_comment = submitother.findViewById(R.id.edt_comment);
        final EditText edt_other_address= submitother.findViewById(R.id.edt_other_address);

        final RadioButton rdi_user_address = submitother.findViewById(R.id.rdi_user_address);
        final RadioButton rdi_other_address = submitother.findViewById(R.id.rdi_orther_address);

        final RadioButton rdi_credit_card = (RadioButton)submitother.findViewById(R.id.rdi_credit_card);
        final RadioButton rdi_cod = (RadioButton)submitother.findViewById(R.id.rdi_cod);


        //event

        rdi_user_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    edt_other_address.setEnabled(false);
            }
        });

        rdi_other_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                edt_other_address.setEnabled(true);
            }
        });
        builder.setView(submitother);
        builder.setNegativeButton("CANCE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setNegativeButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (rdi_credit_card.isChecked()) {
                    orderComment = edt_comment.getText().toString();

                    //loi
                    if (rdi_user_address.isChecked())
                        ordrAddress = Common.currentUser.getAddress();
                    else if (rdi_other_address.isChecked())
                        ordrAddress = edt_other_address.getText().toString();
                    else
                        ordrAddress = "";
                    //payment
                    DropInRequest dropInRequest = new DropInRequest().clientToken(token);
                    startActivityForResult(dropInRequest.getIntent(CartActivity.this), PAYMENT_REQUEST_CODE);

                }
                else if (rdi_cod.isChecked()){
                    orderComment = edt_comment.getText().toString();

                    //loi
                    if (rdi_user_address.isChecked())
                        ordrAddress = Common.currentUser.getAddress();
                    else if (rdi_other_address.isChecked())
                        ordrAddress = edt_other_address.getText().toString();
                    else
                        ordrAddress = "";
                    compositeDisposable.add(
                            Common.cartRepository.getCartItems()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new Consumer<List<Cart>>() {
                                        @Override
                                        public void accept(List<Cart> carts) throws Exception {
                                            if (!TextUtils.isEmpty(ordrAddress))
                                                sendorderToSever(Common.cartRepository.sumPrice(),carts,orderComment,ordrAddress,"COD");

                                            else
                                                Toast.makeText(CartActivity.this, "Order Address can't null", Toast.LENGTH_SHORT).show();
                                        }
                                    })

                    );

                }
            }
        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYMENT_REQUEST_CODE)
        {
            if (resultCode==RESULT_OK)
            {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();
                if (Common.cartRepository.sumPrice() > 0)
                {
                    amount = String.valueOf(Common.cartRepository.sumPrice());
                    params = new HashMap<>();

                    params.put("amount",amount);
                    params.put("nonce",strNonce);

                    sendPayment();
                }
                else
                {
                    Toast.makeText(this, "Payment amount is 0", Toast.LENGTH_SHORT).show();
                }
            }
            else if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, "Payment cancelled", Toast.LENGTH_SHORT).show();

        }
    }

    private void sendPayment() {
        mServiceScalars.payment(params.get("nonce"),params.get("amount"))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().toString().contains("Successful"))
                        {
                            Toast.makeText(CartActivity.this, "Transaction sucessful", Toast.LENGTH_SHORT).show();

                            //submit orther
                            compositeDisposable.add(
                                    Common.cartRepository.getCartItems()
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeOn(Schedulers.io())
                                            .subscribe(new Consumer<List<Cart>>() {
                                                @Override
                                                public void accept(List<Cart> carts) throws Exception {
                                                    if (!TextUtils.isEmpty(ordrAddress))
                                                        sendorderToSever(Common.cartRepository.sumPrice(),carts,orderComment,ordrAddress,"Braintree");

                                                    else
                                                        Toast.makeText(CartActivity.this, "Order Address can't null", Toast.LENGTH_SHORT).show();
                                                }
                                            })

                            );
                        }else
                        {
                            Toast.makeText(CartActivity.this, "Transaction failed", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("DTD_INFO",response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("DTD_INFO",t.getMessage());

                    }
                });
    }

    private void sendorderToSever(float sumprice, List<Cart> carts, String ordercommnet, String orderaddress,String paymentMethod) {
        if (carts.size()>0)
        {
            String orderDetail = new Gson().toJson(carts);
            mService.submitorder(sumprice,orderDetail,ordercommnet,orderaddress,Common.currentUser.getPhone(),paymentMethod)
                    .enqueue(new Callback<OrderResult>() {
                        @Override
                        public void onResponse(Call<OrderResult> call, Response<OrderResult> response) {
                            sendNotificationToServer(response.body());
                        }

                        @Override
                        public void onFailure(Call<OrderResult> call, Throwable t) {
                            Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void sendNotificationToServer(final OrderResult orderResult) {
        mService.getToken("server_app_01","1")
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Map<String,String> contentsend = new HashMap<>();
                        contentsend.put("title","DTDShop");
                        contentsend.put("message","Your have new order "+orderResult.getOrderId());
                        DataMessage dataMessage = new DataMessage();
                        if (response.body().getToken() != null)
                            dataMessage.setTo(response.body().getToken());
                        dataMessage.setData(contentsend);

                        IFCMService ifcmService = Common.getGetFCMService();
                        ifcmService.sendNotification(dataMessage)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if (response.code() == 200)
                                        {
//                                            if (response.body().success == 1)
//                                            {
                                                Toast.makeText(CartActivity.this, "Thank you, Order place", Toast.LENGTH_SHORT).show();
                                                Common.cartRepository.emptycart();
                                                finish();
//                                            }else
//                                            {
//                                                Toast.makeText(CartActivity.this, "Send notification failed !", Toast.LENGTH_SHORT).show();
//                                                Common.cartRepository.emptycart();
//                                                finish();
//                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {
                                        Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void loadCartItem() {
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                        displaycartItem(carts);
                    }
                })
        );
    }

    private void displaycartItem(List<Cart> carts) {
        cartList =  carts;
        cartAdapter = new CartAdapter(this,carts);
        recyclercart.setAdapter(cartAdapter);
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
    // thoat app bang nut back


    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadCartItem();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartAdapter.CartViewHolder)
        {
            String name = cartList.get(viewHolder.getAdapterPosition()).name;
            final Cart deleteitem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            //delete item from adapter
            cartAdapter.removeItem(deletedIndex);
            //delete item from database
            Common.cartRepository.deleteCartItem(deleteitem);

            Snackbar snackbar = Snackbar.make(rootLayout,new StringBuilder(name).append(" remove from Favorite List").toString(),
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartAdapter.restoreItem(deleteitem,deletedIndex);
                    Common.cartRepository.insertToCart(deleteitem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
