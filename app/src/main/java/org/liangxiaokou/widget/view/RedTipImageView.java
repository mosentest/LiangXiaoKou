package org.liangxiaokou.widget.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import org.liangxiaokou.module.R;

public class RedTipImageView extends ImageView {
    private int tipVisibility = 0;
    private TipType mTipType = TipType.RED_TIP_INVISIBLE;
    private Context mContext;

    public RedTipImageView(Context context) {
        this(context, null, 0);
    }

    public RedTipImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedTipImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context, attrs);
    }


    /**
     * 利用配置文件操作
     *
     * @param context
     * @param attrs
     */
    public void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RedTipImageView);
            tipVisibility = typedArray.getInt(R.styleable.RedTipImageView_redTipsVisibility, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight = measureHeight(heightMeasureSpec);
        int measuredWidth = measureWidth(widthMeasureSpec);
        setMeasuredDimension(measuredHeight, measuredWidth);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Default size if no limits are specified.
        int result = 0;
        if (specMode == MeasureSpec.EXACTLY) {
            // If your control can fit within these bounds return that value.
            result = specSize;
        } else {
            result = 60;
            if (specMode == MeasureSpec.AT_MOST) {
                // Calculate the ideal size of your
                // control within this maximum size.
                // If your control fills the available
                // space return the outer bound.
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Default size if no limits are specified.
        int result = 0;
        if (specMode == MeasureSpec.EXACTLY) {
            // If your control can fit within these bounds return that value.
            result = specSize;
        } else {
            result = 60;
            if (specMode == MeasureSpec.AT_MOST) {
                // Calculate the ideal size of your
                // control within this maximum size.
                // If your control fills the available
                // space return the outer bound.
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (tipVisibility == TipType.RED_TIP_VISIBLE.getCode() || mTipType == TipType.RED_TIP_VISIBLE) {
            int width = getWidth();
            //http://www.jb51.net/article/36636.htm
            int y = dipToPixels(10);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawCircle(width - y, y, y / 2.0f, paint);
        }
    }


    private int dipToPixels(int dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }

    public void setTipVisibility(int visibility) {
        tipVisibility = visibility;
        invalidate();
    }

    public void setTipVisibility(TipType tipType) {
        if (tipType == null) {
            throw new NullPointerException();
        }
        this.mTipType = tipType;
        invalidate();
    }

    /**
     * 红点类型
     */
    public enum TipType {
        RED_TIP_INVISIBLE(0),//不显示
        RED_TIP_VISIBLE(1),//显示
        RED_TIP_GONE(2);//不显示
        private int code;

        TipType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}