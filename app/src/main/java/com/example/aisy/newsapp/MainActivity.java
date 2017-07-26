package com.example.aisy.newsapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aisy.newsapp.adapter.ListSourcesAdapter;
import com.example.aisy.newsapp.api.ApiInterface;
import com.example.aisy.newsapp.api.ApiClient;

import com.example.aisy.newsapp.Model.Source;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvly_list_sources;
    //private List<Source> listSource = new ArrayList<>();
    private ListSourcesAdapter listSourcesAdapter;
    private ApiInterface client;
    private Source source = new Source();
    private List<Source.ListSource> listSources = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        client = ApiClient.createService(ApiInterface.class);

        rvly_list_sources = (RecyclerView) findViewById(R.id.rvly_list_sources);

        listSourcesAdapter = new ListSourcesAdapter(this, listSources);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvly_list_sources.setLayoutManager(linearLayoutManager);
        rvly_list_sources.setAdapter(listSourcesAdapter);

        loadDataSources();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void loadDataSources(){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Memuat...");
        dialog.show();

        Call<Source> api = client.getListSource("en");
        api.enqueue(new Callback<Source>() {
            @Override
            public void onResponse(Call<Source> call, Response<Source> response) {
                if (response.isSuccessful()){
                    source = response.body();

                    if (source.getStatus().equals("ok")){
                        listSources = source.getSources();

                        listSourcesAdapter.setListSources(listSources);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Source> call, Throwable t) {

            }
        });

    }

}
