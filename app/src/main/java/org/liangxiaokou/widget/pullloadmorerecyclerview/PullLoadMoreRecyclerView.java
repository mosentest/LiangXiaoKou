package org.liangxiaokou.widget.pullloadmorerecyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import org.liangxiaokou.module.R;

/**
 * Created by moziqi on 2016/1/7.
 */
public class PullLoadMoreRecyclerView extends LinearLayout {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PullLoadMoreListener mPullLoadMoreListener;
    private SwipeRefreshLayoutOnRefresh mSwipeRefreshLayoutOnRefresh;
    private RecyclerViewOnScroll mRecyclerViewOnScroll;
    //默认有更多数据
    private boolean hasMore = true;
    //用于监听滚动的时候，是否在刷新状态
    private boolean isRefresh = false;
    //用于监听滚动的时候，是否在加载更多状态
    private boolean isLoadMore = false;
    //默认可以刷新
    private boolean pullRefreshEnable = true;
    //底部加载更多视图
    private LinearLayout mFooterView;
    //上下文对象
    private Context mContext;
 
    public PullLoadMoreRecyclerView(Context context) {
        super(context);
        initView(context);
    }
 
    public PullLoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
 
    private void initView(Context context) {
        mContext = context;
        //初始化
        View view = LayoutInflater.from(context).inflate(R.layout.view_pull_loadmore_layout, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //设置主题
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        //设置刷新回调
        mSwipeRefreshLayoutOnRefresh = new SwipeRefreshLayoutOnRefresh(this);
        //防止滚动时候，用户滑动view
        mSwipeRefreshLayout.setOnRefreshListener(mSwipeRefreshLayoutOnRefresh);
        //隐藏滚动条
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //提高性能
        mRecyclerView.setHasFixedSize(true);
        //设置效果
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //分割线
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        //设置底部刷新
        mRecyclerViewOnScroll = new RecyclerViewOnScroll(this);
        mRecyclerView.addOnScrollListener(mRecyclerViewOnScroll);
        //防止滚动的时候，用户滑动view
        mRecyclerView.setOnTouchListener(new onTouchRecyclerView());
        //加载更多，默认不显示
        mFooterView = (LinearLayout) view.findViewById(R.id.footer_linearlayout);
        mFooterView.setVisibility(View.GONE);
        this.addView(view);
    }
 
    /**
     * LinearLayoutManager
     */
    public void setLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
 
    /**
     * GridLayoutManager
     */
    public void setGridLayout(int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, spanCount);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }
 
 
    /**
     * StaggeredGridLayoutManager
     */
    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }
 
    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }
 
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
 
    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }
 
 
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }
 
    /**
     * 设置是否可以下拉刷新
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        pullRefreshEnable = enable;
        setSwipeRefreshEnable(enable);
    }
 
    public boolean getPullRefreshEnable() {
        return pullRefreshEnable;
    }
 
    public void setSwipeRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }
 
    /**
     * 获取是否可以下拉刷新
     *
     * @return
     */
    public boolean getSwipeRefreshEnable() {
        return mSwipeRefreshLayout.isEnabled();
    }
 
    /**
     * 设置SwipeRefreshLayout主题
     *
     * @param colorResIds
     */
    public void setColorSchemeResources(int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }
 
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }
 
    public void setRefreshing(final boolean isRefreshing) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (pullRefreshEnable) {
                    mSwipeRefreshLayout.setRefreshing(isRefreshing);
                }
            }
        });
 
    }
 
    /**
     * Solve IndexOutOfBoundsException exception
     */
    public class onTouchRecyclerView implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (isRefresh || isLoadMore) {
                return true;
            } else {
                return false;
            }
        }
    }
 
    public void refresh() {
        if (mPullLoadMoreListener != null) {
            mPullLoadMoreListener.onRefresh();
        }
    }
 
    public void loadMore() {
        if (mPullLoadMoreListener != null && hasMore) {
            mFooterView.setVisibility(View.VISIBLE);
            mPullLoadMoreListener.onLoadMore();
        }
    }
 
    public void setPullLoadMoreCompleted() {
        isRefresh = false;
        mSwipeRefreshLayout.setRefreshing(false);
        isLoadMore = false;
        mFooterView.setVisibility(View.GONE);
 
    }
 
    public void setOnPullLoadMoreListener(PullLoadMoreListener listener) {
        mPullLoadMoreListener = listener;
    }
 
    public boolean isLoadMore() {
        return isLoadMore;
    }
 
    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }
 
    public boolean isRefresh() {
        return isRefresh;
    }
 
    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }
 
    public boolean isHasMore() {
        return hasMore;
    }
 
    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
 
    /**
     * 外部回调这个
     */
    public interface PullLoadMoreListener {
        public void onRefresh();
 
        public void onLoadMore();
    }
    /**
     * 下来刷新回调
     */
    class SwipeRefreshLayoutOnRefresh implements SwipeRefreshLayout.OnRefreshListener {
        private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

        public SwipeRefreshLayoutOnRefresh(PullLoadMoreRecyclerView pullLoadMoreRecyclerView) {
            this.mPullLoadMoreRecyclerView = pullLoadMoreRecyclerView;
        }

        /**
         * 防止滚动时候，用户滑动view
         *
         * @see PullLoadMoreRecyclerView.onTouchRecyclerView
         */
        @Override
        public void onRefresh() {
            if (!mPullLoadMoreRecyclerView.isRefresh()) {
                mPullLoadMoreRecyclerView.setIsRefresh(true);
                mPullLoadMoreRecyclerView.refresh();
            }
        }

        public void onDestroy() {
            mPullLoadMoreRecyclerView = null;
        }
    }

    /**
     * 监听滚动RecyclerView
     */
    class RecyclerViewOnScroll extends RecyclerView.OnScrollListener {
        private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

        public RecyclerViewOnScroll(PullLoadMoreRecyclerView pullLoadMoreRecyclerView) {
            this.mPullLoadMoreRecyclerView = pullLoadMoreRecyclerView;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //最后一个item
            int lastVisibleItem = 0;
            //第一个item
            int firstVisibleItem = 0;
            //获取布局
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            //获取当前布局的item数量
            int totalItemCount = layoutManager.getItemCount();
            //判断布局类型
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
                //Position to find the final item of the current LayoutManager
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                firstVisibleItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
                // since may lead to the final item has more than one StaggeredGridLayoutManager the particularity of the so here that is an array
                // this array into an array of position and then take the maximum value that is the last show the position value
                int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItem = findMax(lastPositions);
                firstVisibleItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)[0];
            }
            //判断滚动到哪里
            if (firstVisibleItem == 0) {
                if (mPullLoadMoreRecyclerView.getPullRefreshEnable())
                    mPullLoadMoreRecyclerView.setSwipeRefreshEnable(true);
            } else {
                mPullLoadMoreRecyclerView.setSwipeRefreshEnable(false);
            }
            if (!mPullLoadMoreRecyclerView.isRefresh() && mPullLoadMoreRecyclerView.isHasMore() && (lastVisibleItem >= totalItemCount - 1)
                    && !mPullLoadMoreRecyclerView.isLoadMore() && (dx > 0 || dy > 0)) {
                mPullLoadMoreRecyclerView.setIsLoadMore(true);
                mPullLoadMoreRecyclerView.loadMore();
            }

        }
        //To find the maximum value in the array

        private int findMax(int[] lastPositions) {

            int max = lastPositions[0];
            for (int value : lastPositions) {
                //       int max    = Math.max(lastPositions,value);
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }

        public void onDestroy() {
            mPullLoadMoreRecyclerView = null;
        }
    }
 }