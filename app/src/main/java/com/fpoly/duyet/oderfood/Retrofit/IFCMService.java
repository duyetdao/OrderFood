package com.fpoly.duyet.oderfood.Retrofit;

import com.fpoly.duyet.oderfood.Model.DataMessage;
import com.fpoly.duyet.oderfood.Model.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by duyet on 11/15/2018.
 */

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAsqLPxI0:APA91bHzrbV3L4LNMkNUi5wydrN-rBBDUbVzgXeXDb3UJYgH2T1AkjJ4E_a9lWXXOym0trrw6FqffGW-uYc75jzIM0DdMlCthgiX39XjarFyBY1VZuUF7MVxjUXdOrvc6j8QfW-jfVlm"

    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body DataMessage body);
}
