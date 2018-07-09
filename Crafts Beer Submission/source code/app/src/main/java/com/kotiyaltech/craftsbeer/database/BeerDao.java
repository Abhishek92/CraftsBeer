package com.kotiyaltech.craftsbeer.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kotiyaltech.craftsbeer.model.BeerItem;

import java.util.List;

/**
 * Created by hp pc on 30-06-2018.
 */
@Dao
public interface BeerDao {
    @Query("SELECT * FROM beeritem ORDER BY abv ASC")
    List<BeerItem> getBeerList();

    @Query("SELECT * FROM beeritem ORDER BY abv DESC")
    List<BeerItem> getBeerListByDescendingOrder();

    @Query("SELECT * FROM beeritem WHERE name LIKE :queryText ORDER BY abv ASC")
    List<BeerItem> searchBearListByName(String queryText);

    @Query("SELECT * FROM beeritem WHERE style LIKE :queryText ORDER BY abv ASC")
    List<BeerItem> filterBeerListByStyle(String queryText);

    @Insert
    void insertAll(List<BeerItem> beerItems);
}
