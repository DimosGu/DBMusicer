package com.zhubch.dbmusicer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhubch.dbmusicer.Model.Artist;

public class ArtistActivity extends BaseActivity {

    private ImageView headImageView;
    private TextView nameText;
    private TextView styleText;
    private TextView memberText;
    private TextView followsText;

    private Artist artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        initToolBarAndTabBar();

        headImageView = (ImageView)findViewById(R.id.head_pic);
        nameText = (TextView)findViewById(R.id.artist_name);
        styleText = (TextView)findViewById(R.id.style);
        artist = (Artist)getIntent().getSerializableExtra("artist");
        nameText.setText(artist.name);
        loadImage(artist.picture, headImageView);

        backBtn.setVisibility(View.VISIBLE);
    }
}
