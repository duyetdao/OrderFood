package com.fpoly.duyet.oderfood.Database.Local;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.fpoly.duyet.oderfood.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by duyet on 10/21/2018.
 */
@Dao
public interface FavoriteDAO {
    @Query("SELECT *FROM Favorite")
    Flowable<List<Favorite>> getFavItems();

    @Query("SELECT EXISTS(SELECT 1 FROM Favorite WHERE id=:itemId)")
    int isFavorite(int itemId);

    @Insert
    void insertFav(Favorite...favorites);

    @Delete
    void delete (Favorite favorite);
}
