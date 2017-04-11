package com.example.vlada.countryselector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CityActivity extends AppCompatActivity {

    private String city = "";
    private TextView summary;
    private TextView title;
    private TextView link;
    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_info);
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        title = (TextView) findViewById(R.id.title);
        link = (TextView) findViewById(R.id.link);
        image =(ImageView) findViewById(R.id.imageView);

        summary = (TextView) findViewById(R.id.summary);


        fetchCityInfo();
    }

    public void fetchCityInfo() {
        ServiceGenerator.getCountiesService().getCityInfo(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(info -> {
                    title.setText(info.getResult().get(0).getTitle());
                    summary.setText(info.getResult().get(0).getSummary());
                    Glide.with(image.getContext())
                            .load(info.getResult().get(0).getThumbnailImg())
                            .into(image);
                    link.setClickable(true);
                    link.setText(info.getResult().get(0).getWikipediaUrl());
                    link.setOnClickListener(c ->{
                        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://"+ info.getResult().get(0).getWikipediaUrl() ) );
                        startActivity( browse );
                    });
                }, error -> {
                    Log.d("fetchCityInfo", "failure");
                    Toast.makeText(this, "Failed to get info", Toast.LENGTH_SHORT).show();
                });

    }
}