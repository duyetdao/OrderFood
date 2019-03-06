package com.fpoly.duyet.oderfood.Database.DataSource;

import com.fpoly.duyet.oderfood.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by duyet on 10/19/2018.
 */

public interface ICartDataSource {
    Flowable<List<Cart>> getCartItems();
    Flowable<List<Cart>> getCartItemsById(int cartItemId);
    int countCartItems();
    float sumPrice();
    void emptycart();
    void insertToCart(Cart...carts);
    void updateCart(Cart...carts);
    void deleteCartItem(Cart cart);


}
