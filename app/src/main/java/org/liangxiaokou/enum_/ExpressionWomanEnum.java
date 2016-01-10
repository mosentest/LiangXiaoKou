package org.liangxiaokou.enum_;

import org.liangxiaokou.module.R;

/**
 * Created by moziqi on 2015/12/17 0017.
 * 表情库
 */
public enum ExpressionWomanEnum {

    BlackWidow(R.color.black_widow, R.mipmap.black_widow_48px),
    Kika(R.color.kika, R.mipmap.kika_48px),
    Mera(R.color.mera, R.mipmap.mera_48px),
    MsMarvel(R.color.ms_marvel, R.mipmap.ms_marvel_48px),
    Spiderwoman(R.color.spiderwoman, R.mipmap.spiderwoman_48px);

    private int ResColor;//颜色
    private int ResImg;//图片

    ExpressionWomanEnum(int resColor, int resImg) {
        ResColor = resColor;
        ResImg = resImg;
    }

    public int getResColor() {
        return ResColor;
    }

    public int getResImg() {
        return ResImg;
    }

}
