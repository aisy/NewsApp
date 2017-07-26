package com.example.aisy.newsapp;

/**
 * Created by aisy on 26/07/17.
 */

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aisy.newsapp.adapter.ListArticlesAdapter;
import com.example.aisy.newsapp.api.ApiClient;
import com.example.aisy.newsapp.api.ApiInterface;
import com.example.aisy.newsapp.helper.ArticleHelper;
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
    private List<Article.ListArticle> listArticleHelper = new ArrayList<>();
    private String source = "";

    private SwipeRefreshLayout refresh;

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

        listArticleHelper = ArticleHelper.getArticles();
        listArticleHelper.clear();

        loadDataArticles(source);

//        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData();
//            }
//        });
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

                        for (Article.ListArticle data : listArticles){
                            listArticleHelper.add(data);
                        }

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

//    private void refreshData(){
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                loadDataSearch("");
//            }
//        });
//    }

    private void loadDataSearch(String query){

//        if (refresh != null){
//            refresh.post(new Runnable() {
//                @Override
//                public void run() {
//                    refresh.setRefreshing(true);
//                }
//            });
//        }

        listArticles.clear();

        CharSequence queryc = query;

        for (Article.ListArticle data : listArticleHelper){
            if (data.getTitle().trim().toLowerCase().contains(queryc)){
                Log.d("TAG", "loadDataSearch: "+data.getTitle());
                listArticles.add(data);
            }
        }

        listArticlesAdapter.setListArticles(listArticles);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadDataSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    loadDataArticles(source);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                loadDataArticles(source);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
