package org.liangxiaokou.widget.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


import org.liangxiaokou.module.R;
import org.liangxiaokou.util.DensityUtils;


/**
 * @author http://blog.csdn.net/finddreams
 * @Description: 带进度条的WebView
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

    private ProgressBar progressbar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
        progressbar = new ProgressBar(context.getApplicationContext(), null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, DensityUtils.dp2px(context.getApplicationContext(), 5), 0, 0));

        Drawable drawable = context.getResources().getDrawable(R.drawable.mo_progress_bar_states);
        progressbar.setProgressDrawable(drawable);
        addView(progressbar);
        setWebViewClient(new InWebViewClient() {
        });
        setWebChromeClient(new WebChromeClient());
        //是否支持缩放
        //getSettings().setSupportZoom(true);
        //getSettings().setBuiltInZoomControls(true);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //通过获取Web页中的title用来设置自己界面中的title
            if (mChangeTitle != null) {
                mChangeTitle.onChangeTitle(title);
            }
        }
    }

    public class InWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    ChangeTitle mChangeTitle;

    public interface ChangeTitle {
        public void onChangeTitle(String title);
    }

    public void onChange(ChangeTitle changeTitle) {
        mChangeTitle = changeTitle;
    }

}