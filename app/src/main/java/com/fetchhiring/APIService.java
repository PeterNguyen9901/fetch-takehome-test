package com.fetchhiring;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    //we make a get request to grab the data
    // The endpoint (hiring.json) is specified using Retrofit's annotations.
    // GET request should be made to the hiring.json endpoint.
    // The function fetchItems will return a list of items when called.
    @GET("hiring.json")
    Call<List<ItemModel>> fetchItems();
}

