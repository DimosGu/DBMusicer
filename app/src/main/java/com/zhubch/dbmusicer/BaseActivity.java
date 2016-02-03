package com.zhubch.dbmusicer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BaseActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    public ImageLoader imageLoader;
    protected TextView titleText;
    protected ImageView backBtn;
    protected RadioGroup radioGroup;
    protected View pannel;

    protected void initToolBarAndTabBar(){
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.tab_bar);
        pannel = frameLayout.findViewById(R.id.panel);
        radioGroup = (RadioGroup)frameLayout.findViewById(R.id.tabs_rg);
        radioGroup.setOnCheckedChangeListener(this);
        View toolbar = findViewById(R.id.toolbar);
        backBtn = (ImageView)toolbar.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("tab",0);
                setResult(RESULT_OK, getIntent().putExtras(bundle));
                finish();
            }
        });
        titleText = (TextView)toolbar.findViewById(R.id.title_text);
        titleText.setText("豆瓣音乐人");
    }

    private void showPlayControl(){
        pannel.animate().translationYBy(-50f).setDuration(500).start();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.tab_rb_4){
            showPlayControl();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("tab",checkedId);
        setResult(RESULT_OK, getIntent().putExtras(bundle));
        finish();
        Log.i("zbc","finish");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(configuration);
    }

    public void loadImage(String url,ImageView imgView){
        imageLoader.displayImage(url, imgView);
    }
}
