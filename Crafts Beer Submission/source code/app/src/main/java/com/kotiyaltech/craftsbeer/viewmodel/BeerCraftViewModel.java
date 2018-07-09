package com.kotiyaltech.craftsbeer.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.kotiyaltech.craftsbeer.database.AppDatabase;
import com.kotiyaltech.craftsbeer.model.BeerItem;
import com.kotiyaltech.craftsbeer.network.RestClient;
import com.kotiyaltech.craftsbeer.util.Util;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

/**
 * Created by hp pc on 30-06-2018.
 */

public class BeerCraftViewModel extends AndroidViewModel {
    private MutableLiveData<List<BeerItem>> beerItemMutableLiveData;
    private AppDatabase appDatabase;

    private static final int QUERY_TYPE_ASC_ORDER = 0;
    private static final int QUERY_TYPE_DESC_ORDER = 1;
    private static final int QUERY_TYPE_SEARCH_BY_NAME = 2;
    private static final int QUERY_TYPE_FILTER_BY_STYLE = 3;

    public BeerCraftViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application.getApplicationContext());
    }

    public LiveData<List<BeerItem>> getBeerList() {
        if (beerItemMutableLiveData == null) {
            beerItemMutableLiveData = new MutableLiveData<>();
            loadBeerItem();

        }
        return beerItemMutableLiveData;
    }

    public void loadBearListInDescendingOrder(){
        new LoadBeerItemsTask(appDatabase, beerItemMutableLiveData, QUERY_TYPE_DESC_ORDER).execute();
    }

    public void loadBeerItem() {
        new LoadBeerItemsTask(appDatabase, beerItemMutableLiveData, QUERY_TYPE_ASC_ORDER).execute();
    }

    public void searchBeerItems(String query){
        new LoadBeerItemsTask(appDatabase, beerItemMutableLiveData, QUERY_TYPE_SEARCH_BY_NAME, query).execute();
    }

    public void filterBeerItems(String query){
        new LoadBeerItemsTask(appDatabase, beerItemMutableLiveData, QUERY_TYPE_FILTER_BY_STYLE, query).execute();
    }

    private static class LoadBeerItemsTask extends AsyncTask<Void, Void, List<BeerItem>> {
        private AppDatabase appDatabase;
        private MutableLiveData<List<BeerItem>> listMutableLiveData;
        private int queryType;
        private String query;

        LoadBeerItemsTask(AppDatabase appDatabase, MutableLiveData<List<BeerItem>> listMutableLiveData, int queryType){
            this.appDatabase = appDatabase;
            this.listMutableLiveData = listMutableLiveData;
            this.queryType = queryType;
        }

        LoadBeerItemsTask(AppDatabase appDatabase, MutableLiveData<List<BeerItem>> listMutableLiveData, int queryType, String query){
            this.appDatabase = appDatabase;
            this.listMutableLiveData = listMutableLiveData;
            this.queryType = queryType;
            this.query = query;
        }

        @Override
        protected List<BeerItem> doInBackground(Void... voids) {
            List<BeerItem> beerItemList =  getBeerListByQueryType(appDatabase, queryType, query);
            if(!Util.listNotNull(beerItemList)){
                try {
                    Response<List<BeerItem>> response =  RestClient.getInstance().getBeerEventService().getBeerList().execute();
                    beerItemList = response.body();
                    appDatabase.beerDao().insertAll(beerItemList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return beerItemList;
        }

        @Override
        protected void onPostExecute(List<BeerItem> beerItems) {
            super.onPostExecute(beerItems);
            listMutableLiveData.setValue(beerItems);
        }
    }

    private static List<BeerItem> getBeerListByQueryType(AppDatabase appDatabase, int queryType, String query){
        switch (queryType){
            case QUERY_TYPE_ASC_ORDER:
                return appDatabase.beerDao().getBeerList();
            case QUERY_TYPE_DESC_ORDER:
                return appDatabase.beerDao().getBeerListByDescendingOrder();
            case QUERY_TYPE_SEARCH_BY_NAME:
                return appDatabase.beerDao().searchBearListByName(query);
            case QUERY_TYPE_FILTER_BY_STYLE:
                return appDatabase.beerDao().filterBeerListByStyle(query);
             default:
                 return appDatabase.beerDao().getBeerList();
        }
    }

}
