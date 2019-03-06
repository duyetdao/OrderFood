package com.fpoly.duyet.oderfood.Database.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.fpoly.duyet.oderfood.Database.ModelDB.Cart;
import com.fpoly.duyet.oderfood.Database.ModelDB.Favorite;
import com.fpoly.duyet.oderfood.R;

/**
 * Created by duyet on 10/19/2018.
 */
@Database(entities = {Cart.class, Favorite.class},version = 1,exportSchema = false)
public abstract class DTDRoomDatabase extends RoomDatabase{

    public abstract CartDAO cartDAO();
    public abstract FavoriteDAO  favoriteDAO();
    public static DTDRoomDatabase instance;

    public static DTDRoomDatabase getInstance(Context context)
    {
        if (instance == null)
            instance = Room.databaseBuilder(context,DTDRoomDatabase.class,"DTD_DrinkShopDB")
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}

