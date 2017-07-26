package com.example.aisy.newsapp;

/**
 * Created by aisy on 26/07/17.
 */

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.aisy.newsapp.adapter.ListArticlesAdapter;
import com.example.aisy.newsapp.api.ApiClient;
import com.example.aisy.newsapp.api.ApiInterface;
import com.example.aisy.newsapp.Model.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesActivity extends AppCompatActivity {

    private RecyclerView rvly_list_articles;
    private ListArticlesAdapter listArticlesAdapter;
    private ApiInterface client;
    private Article article = new Article();
    private List<Article.ListArticle> listArticles = new ArrayList<>();
    private String source = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        client = ApiClient.createService(ApiInterface.class);

        rvly_list_articles = (RecyclerView) findViewById(R.id.rvly_list_articles);
        listArticlesAdapter = new ListArticlesAdapter(this, listArticles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvly_list_articles.setLayoutManager(linearLayoutManager);
        rvly_list_articles.setAdapter(listArticlesAdapter);

        source = getIntent().getStringExtra("source");

        loadDataArticles(source);
    }

    private void loadDataArticles(String source){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        String apiKey = "eed31de19a48444e9fd92c1304b196df";

        Call<Article> api = client.getListArticle(source, apiKey);
        api.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccessful()){
                    article = response.body();

                    if (article.getStatus().equals("ok")){
                        listArticles = article.getArticles();

                        listArticlesAdapter.setListArticles(listArticles);
                    }

                } else {
                    Toast.makeText(ArticlesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Toast.makeText(ArticlesActivity.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

}
