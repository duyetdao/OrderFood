package com.fpoly.duyet.oderfood.Retrofit;

import android.icu.util.ULocale;

import com.fpoly.duyet.oderfood.Model.Banner;
import com.fpoly.duyet.oderfood.Model.Category;
import com.fpoly.duyet.oderfood.Model.CheckUserResponse;
import com.fpoly.duyet.oderfood.Model.Drink;
import com.fpoly.duyet.oderfood.Model.Order;
import com.fpoly.duyet.oderfood.Model.OrderResult;
import com.fpoly.duyet.oderfood.Model.Store;
import com.fpoly.duyet.oderfood.Model.Token;
import com.fpoly.duyet.oderfood.Model.User;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by duyet on 10/13/2018.
 */

public interface IDrinkShopAPI {
    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkUserExists(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone,
                               @Field("name") String name,
                               @Field("address") String address,
                               @Field("birthdate") String birthdate);
    @FormUrlEncoded
    @POST("getuser.php")
    Call<User> getUserInformation(@Field("phone") String phone);


    @FormUrlEncoded
    @POST("getdrink.php")
    Observable<List<Drink>> getDrink(@Field("menuid") String menuID);

    @FormUrlEncoded
    @POST("getdetaildrink.php")
    Observable<List<Drink>> getDetailDrink(@Field("id") String id);

    @GET("getbanner.php")
    Observable<List<Banner>> getBanners();

    @GET("getmenu.php")
    Observable<List<Category>> getMenu();

    @Multipart
    @POST("upload.php")
    Call<String> uploadFile(@Part  MultipartBody.Part phone, @Part MultipartBody.Part file);

    @GET("getalldrink.php")
    Observable<List<Drink>> getallDrinks();

    @FormUrlEncoded
    @POST("sumitoder.php")
    Call<OrderResult> submitorder(@Field("price") float orderPice,
                                  @Field("orderDetail") String orderDetail,
                                  @Field("comment") String comment,
                                  @Field("address") String address,
                                  @Field("phone") String phone,
                                  @Field("paymentMethod") String paymentMethod);
    @FormUrlEncoded
    @POST("braintree/checkout.php")
    Call<String> payment(@Field("nonce") String nonce,
                             @Field("amount") String amount);
    @FormUrlEncoded
    @POST("getorder.php")
    Observable<List<Order>> getOrder(@Field("userPhone") String userPhone,@Field("status") String status);

    @FormUrlEncoded
    @POST("updatetoken.php")
    Call<String> updateToken(@Field("phone") String phone,@Field("token") String token,@Field("issevertoken") String issevertoken);

    @FormUrlEncoded
    @POST("cancelorder.php")
    Call<String> cancelOrder(@Field("orderId") String orderId,@Field("userPhone") String userPhone);

    @FormUrlEncoded
    @POST("getnearbystore.php")
    Observable<List<Store>> getNearbyStore(@Field("lat") String lat, @Field("lng") String lng);

    @FormUrlEncoded
    @POST("gettoken.php")
    Call<Token> getToken(@Field("phone") String phone,
                         @Field("issevertoken") String issevertoken);


}
