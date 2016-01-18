package com.zhubch.dbmusicer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.zhubch.dbmusicer.Model.Artist;
import com.zhubch.dbmusicer.Model.Music;
import com.zhubch.dbmusicer.Utils.HttpUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HotListFragment extends BaseFragment implements View.OnClickListener{

    private ViewPager viewPager;
    private View rootView;
    private TextView segment_music;
    private TextView segment_artist;
    private ListView musicList;
    private ListView artistList;

    private ArrayList<Music> musics;
    private ArrayList<Artist> artists;

    private int currentPage;

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
    public void onClick(View view) {
        segment_music.setBackgroundColor(R.color.colorAccent);
        int selectPage = view.getId() == R.id.segment_music ? 0 : 1;
        if (currentPage != selectPage){
            viewPager.setCurrentItem(selectPage,true);
            currentPage = selectPage;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hot_list,container,false);

        segment_artist = (TextView)rootView.findViewById(R.id.segment_artist);
        segment_music = (TextView)rootView.findViewById(R.id.segment_music);
        segment_artist.setOnClickListener(this);
        segment_music.setOnClickListener(this);
        final ArrayList<View> views = new ArrayList<View>();

        musicList = new ListView(getActivity());
        views.add(musicList);

        artistList = new ListView(getActivity());
        views.add(artistList);

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
                Gson gson = new Gson();

                Map<String, ArrayList<Music>> map = gson.fromJson(responseInfo, new TypeToken<Map<String, ArrayList<Music>>>() {
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

        HttpUtils.get(Api.ARTIST_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                String responseInfo = responseString.substring(27, responseString.length() - 2);
                Gson gson = new Gson();

                Map<String, ArrayList<Artist>> map = gson.fromJson(responseInfo, new TypeToken<Map<String, ArrayList<Artist>>>() {
                }.getType());
                artists = map.get("artists");
                ArtistArrayAdapter adapter = null;
                if (artists != null) {
                    System.out.println(musics);
                    adapter = new ArtistArrayAdapter(HotListFragment.this.getActivity(), R.layout.list_item_artist, artists);
                }
                artistList.setAdapter(adapter);
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
            MusicViewHolder holder = null;
            View view = null;
            if (convertView == null) {
                view = View.inflate(getActivity(), resourceId, null);
                holder = new MusicViewHolder();
                holder.headPic = (ImageView) view.findViewById(R.id.image_view);
                holder.title = (TextView) view.findViewById(R.id.music_name);
                holder.singer = (TextView) view.findViewById(R.id.singer);
                holder.playBtn = (ImageButton) view.findViewById(R.id.play_btn);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (MusicViewHolder) view.getTag();
            }
            Music m = getItem(position);
            holder.title.setText(m.name);
            holder.singer.setText(m.artist);
            loadImage(m.picture,holder.headPic);
            return view;
        }
    }

    class MusicViewHolder {
        ImageView headPic;
        TextView title;
        TextView singer;
        ImageButton playBtn;
    }

    class ArtistArrayAdapter extends ArrayAdapter<Artist> {
        private int resourceId;


        public ArtistArrayAdapter(Context context, int resource, List<Artist> objects) {
            super(context, resource, objects);
            this.resourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ArtistViewHolder holder = null;
            View view = null;
            if (convertView == null) {
                view = View.inflate(getActivity(), resourceId, null);
                holder = new ArtistViewHolder();
                holder.headPic = (ImageView) view.findViewById(R.id.image_view);
                holder.title = (TextView) view.findViewById(R.id.artist_name);
                holder.style = (TextView) view.findViewById(R.id.style);
                holder.follows = (TextView) view.findViewById(R.id.follows);
                holder.love = (ImageButton) view.findViewById(R.id.love_btn);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ArtistViewHolder) view.getTag();
            }
            Artist a = getItem(position);
            holder.title.setText(a.name);
            holder.follows.setText(a.follower);
            holder.style.setText(a.style);
            loadImage(a.picture,holder.headPic);
            return view;
        }
    }

    class ArtistViewHolder {
        ImageView headPic;
        TextView title;
        TextView style;
        TextView follows;
        ImageButton love;
    }
}
