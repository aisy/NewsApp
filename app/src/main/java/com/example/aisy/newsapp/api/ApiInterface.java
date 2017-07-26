package com.example.aisy.newsapp.api;

/**
 * Created by aisy on 25/07/17.
 */

//memanggil model

import com.example.aisy.newsapp.Model.Source;
import com.example.aisy.newsapp.Model.Article;

//import java.util.List;

//memanggil retrofit
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface ApiInterface {

    //membuat method get untuk mendapatkan data source
    @GET("sources")
    Call<Source> getListSource(@Query("language") String lang);

    @GET("articles")
    Call<Article> getListArticle(@Query("source") String source, @Query("apiKey") String apiKey);
}
