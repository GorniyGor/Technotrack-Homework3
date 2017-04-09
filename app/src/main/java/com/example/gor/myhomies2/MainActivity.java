package com.example.gor.myhomies2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Скачиваем JSON и добавляем все ссылки из него в архив
        DownloadJSONAsync requestTask = new DownloadJSONAsync();
        requestTask.execute(DownloadJSONAsync.JSON_URL);

        mRecyclerView = new RecyclerView(this);

        mRecyclerView.setAdapter(new RecyclerAdapter(getLayoutInflater()));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2) );

        mRecyclerView.setHasFixedSize(true);
        setContentView(mRecyclerView);

    }
}
