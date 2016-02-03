package com.zhubch.dbmusicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private ArrayList<BaseFragment> fragments;

    private int currentIndex;

    private int checkedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new ArrayList<BaseFragment>();
        fragments.add(new HotListFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new MyLikeFragment());

        initToolBarAndTabBar();
        radioGroup.setOnCheckedChangeListener(this);
        currentIndex = -1;
        switchFragment(0);
        String s1 = "waeginsapnaabangpisebbasepgnccccapisdnfngaabndlrjngeuiogbbegbuoecccc";
        String s2 = "a+b+c-";
        findSerial(s1,s2);
    }

    private void findSerial(String s1,String s2){
        ArrayList<Integer>
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < s2.length() / 2;i++){
            char a[] = {s2.charAt(i*2),s2.charAt(i*2)};
            char b[] = {s2.charAt(i*2),s2.charAt(i*2),s2.charAt(i*2),s2.charAt(i*2)};

            String target = s2.charAt(i*2+1)=='+' ? new String(a) : new String(b);
            list.add(target);
            s1.indexOf()
        }
    }

    private void switchFragment(int index){
        if (currentIndex == index){
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (currentIndex >= 0){
            Fragment old = fragments.get(currentIndex);
            ft.hide(old);
        }
        BaseFragment fragment = fragments.get(index);
        titleText.setText(fragment.getTitle());
        if (fragment.isAdded()){
            ft.show(fragment).commit();
        }else {
            ft.add(R.id.content,fragment).commit();
        }
        currentIndex = index;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = data.getExtras();
        checkedId = bundle.getInt("tab");
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (checkedId != 0){
            for(int i = 0; i < radioGroup.getChildCount(); i++){
                if(radioGroup.getChildAt(i).getId() == checkedId){
                    radioGroup.check(checkedId);
                    switchFragment(i);
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.tab_rb_4){
            super.onCheckedChanged(radioGroup,checkedId);
            return;
        }
        for(int i = 0; i < radioGroup.getChildCount(); i++){
            if(radioGroup.getChildAt(i).getId() == checkedId){
                switchFragment(i);
            }
        }
    }
}
