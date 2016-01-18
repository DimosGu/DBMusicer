package com.zhubch.dbmusicer;


import android.support.v4.app.Fragment;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    public void loadImage(String url,ImageView imgView){
        if (getActivity() instanceof BaseActivity){
            BaseActivity activity = (BaseActivity)getActivity();
            activity.loadImage(url,imgView);
        }
    }

    public BaseFragment() {
        // Required empty public constructor
    }

    public String getTitle(){
        return "";
    }

}
