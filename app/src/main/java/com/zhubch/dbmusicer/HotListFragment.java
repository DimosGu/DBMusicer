package com.zhubch.dbmusicer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.zhubch.dbmusicer.Model.Api;
import com.zhubch.dbmusicer.Model.Music;
import com.zhubch.dbmusicer.Utils.HttpUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HotListFragment extends BaseFragment {

    private ViewPager viewPager;
    private View rootView;
    private ListView musicList;

    private ArrayList<Music> musics;

    public HotListFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTitle(){
        return "豆瓣音乐人";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hot_list,container,false);

        final ArrayList<View> views = new ArrayList<View>();

        musicList = new ListView(getActivity());
        views.add(musicList);

        ListView list2 = new ListView(getActivity());
        views.add(list2);
        viewPager = (ViewPager)rootView.findViewById(R.id.view_pager);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public int getItemPosition(Object object) {

                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "";
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        });
        return rootView;
    }

    private void loadData(){
        HttpUtils.get(Api.MUSIC_LIST, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                String responseInfo = responseString.substring(28, responseString.length() - 2);
                Log.i("zbc",""+responseInfo.length());
//                responseInfo = "{\"songs\":[{\"count\":3599,\"picture\":\"http://img3.douban.com/view/site/median/public/1a774de56fb4bf2.jpg\",\"name\":\"微风曲\",\"artist\":\"好妹妹\",\"rank\":1,\"id\":\"634129\",\"length\":\"3:30\",\"artist_id\":\"105827\",\"src\":\"http://mr3.douban.com/201601181131/ede1f0f2bedfaa9b95dd63d239b3d7e9/view/musicianmp3/mp3/x18044848.mp3\",\"widget_id\":\"191023424\"}]}";
                Gson gson = new Gson();
                Log.i("zbc", responseInfo);

                Map<String,ArrayList<Music>> map = gson.fromJson(responseInfo, new TypeToken<Map<String,ArrayList<Music>>>() {
                }.getType());
                musics = map.get("songs");
                MusicArrayAdapter adapter = null;
                if (musics != null) {
                    System.out.println(musics);
                    adapter = new MusicArrayAdapter(HotListFragment.this.getActivity(), R.layout.list_item_music, musics);
                }
                musicList.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loadData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class MusicArrayAdapter extends ArrayAdapter<Music> {
        private int resourceId;


        public MusicArrayAdapter(Context context, int resource, List<Music> objects) {
            super(context, resource, objects);
            this.resourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            View view = null;
            if (convertView == null) {
                view = View.inflate(getActivity(), resourceId, null);
                holder = new ViewHolder();
                holder.headPic = (ImageView) view.findViewById(R.id.image_view);
                holder.title = (TextView) view.findViewById(R.id.music_name);
                holder.singer = (TextView) view.findViewById(R.id.singer);
                holder.playBtn = (ImageButton) view.findViewById(R.id.play_btn);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            Music m = getItem(position);
            holder.title.setText(m.name);
            holder.singer.setText(m.artist);
            loadImage(m.picture,holder.headPic);
            return view;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
    }

    class ViewHolder {
        ImageView headPic;
        TextView title;
        TextView singer;
        ImageButton playBtn;
    }
}
