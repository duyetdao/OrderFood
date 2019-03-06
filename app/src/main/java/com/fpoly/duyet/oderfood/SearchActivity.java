package com.fpoly.duyet.oderfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import com.fpoly.duyet.oderfood.Adapter.DrinkAdapter;
import com.fpoly.duyet.oderfood.Model.Drink;
import com.fpoly.duyet.oderfood.Retrofit.IDrinkShopAPI;
import com.fpoly.duyet.oderfood.Utils.Common;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    List<String> suggestList = new ArrayList<>();
    List<Drink> localDataSource = new ArrayList<>();
    MaterialSearchBar searchBar;

    IDrinkShopAPI mService;
    RecyclerView recyclerSearch;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    DrinkAdapter searchAdapter,adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //server
        mService = Common.getAPI();

        recyclerSearch = findViewById(R.id.recycler_search);
        recyclerSearch.setLayoutManager(new GridLayoutManager(this,2));

        searchBar = findViewById(R.id.searchBar);
        searchBar.setHint("Enter your food");

        loadAllDrink();
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    List<String> suggest = new ArrayList<>();
                    for (String search:suggestList)
                    {
                        if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                            suggest.add(search);
                    }
                    searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    recyclerSearch.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                    startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {
        List<Drink> result = new ArrayList<>();
        for (Drink drink:localDataSource)
            if (drink.Name.contains(text))
                result.add(drink);
        searchAdapter = new DrinkAdapter(this,result);
        recyclerSearch.setAdapter(searchAdapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void loadAllDrink() {
        compositeDisposable.add(mService.getallDrinks().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<Drink>>() {
            @Override
            public void accept(List<Drink> drinks) throws Exception {
                displayListDrink(drinks);
                buildSuggestList(drinks);
            }
        }));
    }

    private void buildSuggestList(List<Drink> drinks) {
        for (Drink drink:drinks)
            suggestList.add(drink.Name);
        searchBar.setLastSuggestions(suggestList);
    }

    private void displayListDrink(List<Drink> drinks) {
        localDataSource = drinks;
        adapter = new DrinkAdapter(this,drinks);
        recyclerSearch.setAdapter(adapter);
    }
}
