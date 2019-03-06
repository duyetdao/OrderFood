package com.fpoly.duyet.oderfood;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;

import com.fpoly.duyet.oderfood.Adapter.FavoriteAdapter;
import com.fpoly.duyet.oderfood.Database.ModelDB.Favorite;
import com.fpoly.duyet.oderfood.Utils.Common;
import com.fpoly.duyet.oderfood.Utils.RecyclerItemTouchHelper;
import com.fpoly.duyet.oderfood.Utils.RecyclerItemTouchHelperListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoriteListActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener{
    RecyclerView recyclerfav;
    CompositeDisposable compositeDisposable;
    FavoriteAdapter favoriteAdapter;
    List<Favorite> localFavorite = new ArrayList<>();
    RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        compositeDisposable = new CompositeDisposable();

        recyclerfav = findViewById(R.id.recycler_fav);
        recyclerfav.setLayoutManager(new LinearLayoutManager(this));
        recyclerfav.setHasFixedSize(true);

        rootLayout = findViewById(R.id.rootLayout);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerfav);
        loadFavoriteItems();
    }

    private void loadFavoriteItems() {
        compositeDisposable.add(Common.favoriteRepository.getFavItems()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<Favorite>>() {
            @Override
            public void accept(List<Favorite> favorites) throws Exception {
                    displayFavoriteItem(favorites);
            }
        }));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadFavoriteItems();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void displayFavoriteItem(List<Favorite> favorites) {
        localFavorite = favorites;
        favoriteAdapter = new FavoriteAdapter(this,favorites);
        recyclerfav.setAdapter(favoriteAdapter);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder)
        {
            String name = localFavorite.get(viewHolder.getAdapterPosition()).name;
            final Favorite deleteitem = localFavorite.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            //delete item from adapter
            favoriteAdapter.removeItem(deletedIndex);
            //delete item from database
            Common.favoriteRepository.delete(deleteitem);

            Snackbar snackbar = Snackbar.make(rootLayout,new StringBuilder(name).append(" remove from Favorite List").toString(),
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteAdapter.restoreItem(deleteitem,deletedIndex);
                    Common.favoriteRepository.insertFav(deleteitem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }

    }
}
