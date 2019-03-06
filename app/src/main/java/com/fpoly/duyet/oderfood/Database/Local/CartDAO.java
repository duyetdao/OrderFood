package com.fpoly.duyet.oderfood.Database.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.fpoly.duyet.oderfood.Database.ModelDB.Cart;

import java.nio.FloatBuffer;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by duyet on 10/19/2018.
 */
@Dao
public interface CartDAO {
    @Query("SELECT * FROM Cart")
    Flowable<List<Cart>> getCartItems();

    @Query("SELECT * FROM Cart WHERE id=:cartItemId")
    Flowable<List<Cart>> getCartItemsById(int cartItemId);

    @Query("SELECT COUNT(*) from Cart")
    int countCartItems();

    @Query("SELECT SUM(Price) from Cart")
    float sumPrice();

    @Query("DELETE from Cart")
    void emptycart();

    @Insert
    void insertToCart(Cart...carts);

    @Update
    void updateCart(Cart...carts);

    @Delete
    void deleteCartItem(Cart cart);
}
