package org.liangxiaokou.module.home.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.liangxiaokou.bean.Album;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.module.album.Event;
import org.liangxiaokou.module.home.adapter.AlbumViewAdapter;
import org.liangxiaokou.module.R;
import org.liangxiaokou.app.GeneralFragment;
import org.liangxiaokou.util.VolleyLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * 纪念日 日记 传相册
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends GeneralFragment implements XRecyclerView.LoadingListener {

    private XRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AlbumViewAdapter mAlbumViewAdapter;

    public AlbumFragment() {
        // Required empty public constructor
    }


    public static AlbumFragment getInstance(String title) {
        AlbumFragment instance = new AlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        instance.setArguments(bundle);
        return instance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_album;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.album_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        List<Album> mDatas = new ArrayList<>();
        mAlbumViewAdapter = new AlbumViewAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(mAlbumViewAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setLoadingListener(this);
    }

    @Subscribe
    public void toEvent(Event event) {
        if (event.isSuccess()) {
            mRecyclerView.setRefreshing(true);
        } else {
            showToast(event.getEventmsg());
        }
    }

    @Override
    protected void onFirstUserVisible() {
        //执行刷新
        mRecyclerView.setRefreshing(true);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }


    @Override
    public void PreOnStart() {

    }

    @Override
    public void PreOnResume() {

    }

    @Override
    public void PreOnPause() {

    }

    @Override
    public void PreOnStop() {

    }

    @Override
    public void PreOnDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        BmobNetUtils.queryAlbums(getActivity(), 0, new FindListener<Album>() {
            @Override
            public void onSuccess(List<Album> list) {
                Collections.reverse(list);
                mAlbumViewAdapter.refreshData(list);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onError(int i, String s) {
                mRecyclerView.refreshComplete();
            }
        });

    }

    @Override
    public void onLoadMore() {
        mAlbumViewAdapter.notifyDataSetChanged();
        mRecyclerView.loadMoreComplete();
    }
}
