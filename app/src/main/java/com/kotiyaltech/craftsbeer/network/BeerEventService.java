package com.kotiyaltech.craftsbeer.network;

import com.kotiyaltech.craftsbeer.model.BeerItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hp pc on 30-06-2018.
 */

public interface BeerEventService {

    @GET("beercraft")
    Call<List<BeerItem>> getBeerList();
}
