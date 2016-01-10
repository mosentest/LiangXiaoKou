package org.liangxiaokou.module.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yalantis.starwars.interfaces.TilesFrameLayoutListener;

import org.liangxiaokou.module.home.HomeActivity;
import org.liangxiaokou.module.R;
import org.liangxiaokou.app.GeneralFragment;
import org.liangxiaokou.widget.view.GiftRainView;

/**
 * A placeholder fragment containing a simple view.
 */
public class SplashScreenActivityFragment extends GeneralFragment implements TilesFrameLayoutListener {

    private GiftRainView giftRainView;
    private boolean isStart = false;


    public SplashScreenActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    protected void initView() {
        //giftRainView = (GiftRainView) findViewById(R.id.gift_rain_view);

    }

    @Override
    protected void initData() {
//        giftRainView.setImages(R.mipmap.ic_launcher, R.mipmap.boy, R.mipmap.gril);
//        giftRainView.startRain();
//        isStart = true;
        Handler handler = new Handler();
        handler.postDelayed(new SplashHandler(), 3000);
    }

    @Override
    protected void PreOnStart() {

    }

    @Override
    protected void PreOnResume() {
//        if (!isStart) {
//            giftRainView.startRain();
//        }
    }

    @Override
    protected void PreOnPause() {
//        if (isStart) {
//            giftRainView.stopRainDely();
//        }
    }

    @Override
    protected void PreOnStop() {

    }

    @Override
    protected void PreOnDestroy() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.gift_rain_view:
//                break;
        }
    }

    @Override
    public void onAnimationFinished() {
    }


    class SplashHandler implements Runnable {
        public void run() {
//            if (giftRainView != null) {
//                giftRainView.stopRainDely();
//            }
            startActivity(new Intent(getActivity(), HomeActivity.class));
            getActivity().finish();
        }
    }
}
