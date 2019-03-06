package com.fpoly.duyet.oderfood.Utils;

import com.fpoly.duyet.oderfood.Database.DataSource.CartRepository;
import com.fpoly.duyet.oderfood.Database.DataSource.FavoriteRepository;
import com.fpoly.duyet.oderfood.Database.Local.DTDRoomDatabase;
import com.fpoly.duyet.oderfood.Model.Category;
import com.fpoly.duyet.oderfood.Model.Drink;
import com.fpoly.duyet.oderfood.Model.Order;
import com.fpoly.duyet.oderfood.Model.User;
import com.fpoly.duyet.oderfood.Retrofit.FCMClient;
import com.fpoly.duyet.oderfood.Retrofit.IDrinkShopAPI;
import com.fpoly.duyet.oderfood.Retrofit.IFCMService;
import com.fpoly.duyet.oderfood.Retrofit.RetrofiScalarsClient;
import com.fpoly.duyet.oderfood.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duyet on 10/13/2018.
 */

public class Common {
    public static final String BASE_URL = "http://androidnetworking.dakdesign.net/drinkshop/";
    public static final String API_TOKEN_URL = "http://androidnetworking.dakdesign.net/drinkshop/braintree/main.php";
    public static User currentUser = null;
    public static Category currentCategory = null;
    public static Drink currentDrink = null;
    public static Order currentOrder = null;
    public static List<Drink> toping_list = new ArrayList<>();
    public static final String TOPPING_MENU_ID="60";

    public static int sizeofCup = -1;
    public static int sugar = -1;
    public static int ice = -1;

    public static double toppingPrice=0.0;
    public static List<String> toppingAdded=new ArrayList<>();

    //data base
    public static DTDRoomDatabase dtdroomdatabse;
    public static CartRepository cartRepository;
    public static FavoriteRepository favoriteRepository;


    private static final String FCM_API ="http://fcm.googleapis.com/" ;

    public static IFCMService getGetFCMService()

    {
        return FCMClient.getClient(FCM_API).create(IFCMService.class);
    }

    public static IDrinkShopAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IDrinkShopAPI.class);
    }
    public static IDrinkShopAPI getScalarsAPI()
    {
        return RetrofiScalarsClient.getScalarsClient(BASE_URL).create(IDrinkShopAPI.class);
    }

    public static String convertCodeToSatus(int orderstatus) {
        switch (orderstatus)
        {
            case 0:
                return "Placed";
            case 1:
                return "Processing";
            case 2:
                return "Shipping";
            case 3:
                return "Shipped";
            case -1:
                return "Cancelled";
                default:
                    return "Order Error";
        }
    }
}
