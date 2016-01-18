package com.zhubch.dbmusicer;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private ArrayList fragments;
    private RadioGroup radioGroup;
    private Toolbar toolbar;

    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new ArrayList<>();
        fragments.add(new HotListFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new MyLikeFragment());

        LinearLayout ll = (LinearLayout)findViewById(R.id.tab_bar);
        radioGroup = (RadioGroup)ll.findViewById(R.id.tabs_rg);
        radioGroup.setOnCheckedChangeListener(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("豆瓣音乐人");
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.tab_rb_4){
            return;
        }
        for(int i = 0; i < radioGroup.getChildCount(); i++){
            if(radioGroup.getChildAt(i).getId() == checkedId){
                BaseFragment fragment = (BaseFragment)fragments.get(i);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content,fragment).commit();
                currentIndex = i;
                toolbar.setTitle(fragment.getTitle());
            }
        }
    }
}
