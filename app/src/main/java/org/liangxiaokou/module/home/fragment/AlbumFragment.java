package org.liangxiaokou.module.home.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.liangxiaokou.bean.Album;
import org.liangxiaokou.module.home.adapter.AlbumViewAdapter;
import org.liangxiaokou.module.R;
import org.liangxiaokou.app.GeneralFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 纪念日 日记 传相册
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends GeneralFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
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
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }


    @Override
    public void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.album_swiperefreshlayout);
        mRecyclerView = (XRecyclerView) findViewById(R.id.album_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

    }

    private int refreshTime = 0;
    private int times = 0;

    @Override
    public void initData() {
        // 刷新时，指示器旋转后变化的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.system_color, R.color.system_press);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(false);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        List<Album> mDatas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Random random = new Random();
            int result = random.nextInt(2);
            Album album = new Album();
            album.setUsername(result == 1 ? "男：**qi" : "女：**qi");
            album.setType(result);
            mDatas.add(album);

        }
        mAlbumViewAdapter = new AlbumViewAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(mAlbumViewAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime++;
                times = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mAlbumViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if (times == 5) {
                    mRecyclerView.setNoMore(true);
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mAlbumViewAdapter.notifyDataSetChanged();
                        mRecyclerView.loadMoreComplete();
                    }
                }, 1000);
                times++;
            }
        });
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

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

    }
}
