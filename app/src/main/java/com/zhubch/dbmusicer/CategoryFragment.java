package com.zhubch.dbmusicer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.zhubch.dbmusicer.Model.Api;
import com.zhubch.dbmusicer.Utils.HttpUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment {

    private View rootView;

    private ListView listView;

    private ArrayList<ArrayList<String>> categorys;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTitle(){
        return "分类浏览";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category,container,false);
        listView = (ListView)rootView.findViewById(R.id.category_list);
        loadData();
        return rootView;
    }
    private void loadData(){
        HttpUtils.get(Api.CATE_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("zbc",responseString);
                String responseInfo = responseString.substring(28, responseString.length() - 2);
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(responseInfo, new TypeToken<Map<String, Object>>(){}.getType());
                categorys = (ArrayList<ArrayList<String>>)map.get("genrelist");
                CategoryArrayAdapter adapter = null;
                if (categorys != null) {
                    adapter = new CategoryArrayAdapter(CategoryFragment.this.getActivity(), R.layout.list_item_category, categorys);
                }
                listView.setAdapter(adapter);
            }
        });
    }


    class CategoryArrayAdapter extends ArrayAdapter<ArrayList<String>> {
        private int resourceId;


        public CategoryArrayAdapter(Context context, int resource, List<ArrayList<String>> objects) {
            super(context, resource, objects);
            this.resourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CategoryViewHolder holder = null;
            View view = null;
            if (convertView == null) {
                view = LayoutInflater.from(getActivity()).inflate(resourceId,parent,false);
                holder = new CategoryViewHolder();
                holder.name = (TextView) view.findViewById(R.id.category_name);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (CategoryViewHolder) view.getTag();
            }
            ArrayList<String> a = getItem(position);
            holder.name.setText(a.get(1));
            return view;
        }
    }

    class CategoryViewHolder {
        TextView name;
    }
}
