package com.fpoly.duyet.oderfood.Database.DataSource;
import com.fpoly.duyet.oderfood.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by duyet on 10/21/2018.
 */

public interface IFavoriteDataSource {

    Flowable<List<Favorite>> getFavItems();


    int isFavorite(int itemId);

    void insertFav(Favorite...favorites);

    void delete (Favorite favorite);
}
