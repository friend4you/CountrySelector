package com.example.vlada.countryselector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vlada.countryselector.api.ServiceGenerator;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CityActivity extends AppCompatActivity {

    private TextView summary;
    private TextView title;
    private TextView link;
    private ImageView image;
    private String city = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_info);
        Intent intent;
        if (getIntent() != null) {
            intent = getIntent();
            city = intent.getStringExtra("city");
        }else{
            Toast.makeText(this, "Info not found", Toast.LENGTH_SHORT).show();
            return;
        }

        title = (TextView) findViewById(R.id.title);
        link = (TextView) findViewById(R.id.link);
        image = (ImageView) findViewById(R.id.imageView);
        summary = (TextView) findViewById(R.id.summary);

        fetchCityInfo();
    }

    public void fetchCityInfo() {
        ServiceGenerator.getCountiesService().getCityInfo(city)
                .flatMap(info -> Observable.from(info.getResult()))
                .first()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(info -> {
                    title.setText(info.getTitle());
                    summary.setText(info.getSummary());
                    Glide.with(image.getContext())
                            .load(info.getThumbnailImg())
                            .into(image);
                    link.setClickable(true);
                    link.setText(info.getWikipediaUrl());
                    link.setOnClickListener(c -> {
                        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + info.getWikipediaUrl()));
                        startActivity(browse);
                    });
                }, error -> {
                    Log.e("fetchCityInfo", "failure", error);
                    Toast.makeText(this, "Info not found", Toast.LENGTH_SHORT).show();
                });

    }
}