package org.liangxiaokou.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by moziqi on 2015/12/28 0028.
 */
public class MScrollView extends ScrollView {
    public MScrollView(Context context) {
        this(context, null, 0);
    }

    public MScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.scrollViewStyle);
    }

    public MScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

}
