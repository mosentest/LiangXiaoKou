package org.liangxiaokou.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import org.liangxiaokou.app.GeneralActivity;
import org.liangxiaokou.bmob.BmobIMNetUtils;
import org.liangxiaokou.module.R;
import org.liangxiaokou.module.feedback.FeedBackActivity;
import org.liangxiaokou.module.home.fragment.AlbumFragment;
import org.liangxiaokou.module.home.fragment.MeFragment;
import org.liangxiaokou.module.home.fragment.OtherFragment;
import org.liangxiaokou.util.BaiduLBSutils;
import org.liangxiaokou.util.LogUtils;
import org.liangxiaokou.util.SnackBarUtils;
import org.liangxiaokou.util.ThirdUtils;
import org.liangxiaokou.util.ToastUtils;
import org.liangxiaokou.util.ViewPagerAdapter;
import org.liangxiaokou.util.VolleyLog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.exception.BmobException;

/**
 * http://docs.bmob.cn/android/developdoc/index.html?menukey=develop_doc&key=develop_android
 * http://docs.bmob.cn/android/faststart/index.html?menukey=fast_start&key=start_android#index_获取应用密钥和下载SDK
 * http://www.jianshu.com/p/67ab63723e54
 */
public class HomeActivity extends GeneralActivity implements
        ViewPager.OnPageChangeListener,
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener,
        UmengUpdateListener,
        BDLocationListener,
        OnGetGeoCoderResultListener {

    private final static String TAG = "HomeActivity";
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFloatingActionButton;
    private NavigationView mNavigationView;

    private ViewPagerAdapter mViewPagerAdapter;
    private String[] tabTitle;
    private List<Fragment> fragments;

    /**
     * 百度lbs相关api
     */
    private LocationClient mLocationClient;
    private GeoCoder mGeoCoder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ThirdUtils.bmobInit(getApplicationContext());
        ThirdUtils.umengInit(this, true, false, this);
        //http://blog.csdn.net/shineflowers/article/details/40426361，http://blog.csdn.net/javensun/article/details/7334230
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("org.liangxiaokou.receiver.xlk_action");
        sendBroadcast(broadcastIntent);
        //百度定位
        mLocationClient = BaiduLBSutils.locationStart(this, this);
        //连接Bmob服务器
        BmobIMNetUtils.connect(this, new ConnectListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    VolleyLog.e("%s", "connect success");
                } else {
                    VolleyLog.e("%s", e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void PreOnStart() {

    }

    @Override
    public void PreOnResume() {
        ThirdUtils.statisticsInActivityResume(this);
    }

    @Override
    public void PreOnRestart() {

    }

    @Override
    public void PreOnPause() {
        ThirdUtils.statisticsInActivityPause(this);
    }

    @Override
    public void PreOnStop() {
        BaiduLBSutils.locationStop(mLocationClient);

    }

    @Override
    public void PreOnDestroy() {
        if (mGeoCoder != null) {
            mGeoCoder.destroy();
        }
    }

    @Override
    public boolean PreOnKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.id_coordinatorlayout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.id_appbarlayout);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.id_floatingactionbutton);
        mFloatingActionButton.setVisibility(View.GONE);
        mNavigationView = (NavigationView) findViewById(R.id.id_navigationview);
    }

    @Override
    public void initData() {
        tabTitle = getResources().getStringArray(R.array.tab_titles);
        fragments = new ArrayList<>();
        OtherFragment otherFragment = OtherFragment.getInstance(getString(R.string.other));
        AlbumFragment albumFragment = AlbumFragment.getInstance(getString(R.string.album));
        MeFragment meFragment = MeFragment.getInstance(getString(R.string.me));
        fragments.add(otherFragment);
        fragments.add(albumFragment);
        fragments.add(meFragment);
        //设置背景颜色
        mNavigationView.setBackgroundColor(getResources().getColor(R.color.gray));
        configViews();
    }

    private void configViews() {
        // 设置显示Toolbar
        setSupportActionBar(mToolbar);
        // 设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mNavigationView.setNavigationItemSelectedListener(this);

        // 初始化ViewPager的适配器，并设置给它
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, tabTitle);
        mViewPager.setAdapter(mViewPagerAdapter);
        // 设置ViewPager最大缓存的页面个数
        mViewPager.setOffscreenPageLimit(3);
        // 给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）
        mViewPager.addOnPageChangeListener(this);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        //mTabLayout.setTabTextColors(getResources().getColor(R.color.dark), getResources().getColor(R.color.system_color));//设置文本在选中和为选中时候的颜色
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.other)), true);//添加 Tab,默认选中
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
        mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);//给Tabs设置适配器

        // 设置FloatingActionButton的点击事件
        mFloatingActionButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //是否修改标题
        //mToolbar.setTitle(tabTitle[position]);
        switch (position) {
            case 1:
                mFloatingActionButton.setVisibility(View.VISIBLE);
                break;
            default:
                mFloatingActionButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // FloatingActionButton的点击事件
            case R.id.id_floatingactionbutton:
                SnackBarUtils.show(v, getString(R.string.plusone), 0);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Menu item点击后选中，并关闭Drawerlayout
        item.setChecked(false);
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.nav_menu_home:
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_menu_feedback:
                startActivity(FeedBackActivity.class);
                break;
            case R.id.nav_menu_update:
                mDrawerLayout.closeDrawers();
                ThirdUtils.umengInit(this, false, false, new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        //0表示有更新，1表示无更新，2表示非wifi状态，3表示请求超时
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(getApplicationContext(), updateInfo);
                                break;
                            case UpdateStatus.No: // has no update
                                ToastUtils.toast(getApplicationContext(), "亲，没有新版本哦！");
                                break;
                            case UpdateStatus.Timeout: // time out
                                ToastUtils.toast(getApplicationContext(), "你这个笨蛋，快打开数据！");
                                break;
                        }
                    }
                });
                UmengUpdateAgent.forceUpdate(getApplicationContext());
                break;
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    /**
     * umeng
     *
     * @param updateStatus
     * @param updateInfo
     */
    @Override
    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
        //0表示有更新，1表示无更新，2表示非wifi状态，3表示请求超时
        switch (updateStatus) {
            case UpdateStatus.Yes: // has update
                UmengUpdateAgent.showUpdateDialog(this, updateInfo);
                break;
        }
    }

    private long mkeyTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                ToastUtils.toast(getApplicationContext(), "再按一次退出程序");
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * baidu
     *
     * @param bdLocation
     */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null) {
            return;
        }
        LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        mGeoCoder = BaiduLBSutils.getInstance(latLng, this);
    }

    /**
     * 地理编码查询结果回调函数
     *
     * @param geoCodeResult
     */
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
    }

    /**
     * 反地理编码查询结果回调函数
     *
     * @param reverseGeoCodeResult
     */
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            // 没有检测到结果
            return;
        }
        ReverseGeoCodeResult.AddressComponent addressDetail = reverseGeoCodeResult.getAddressDetail();
        if (addressDetail != null) {
            StringBuilder address = new StringBuilder()
                    .append(addressDetail.province)
                    .append(addressDetail.district)
                    .append(addressDetail.street)
                    .append(addressDetail.streetNumber);
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //设置页面
        mViewPager.setCurrentItem(intent.getIntExtra("LoginActivity_code", 0));
    }
}
