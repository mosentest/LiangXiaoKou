package org.liangxiaokou.enum_;

import org.liangxiaokou.module.R;

/**
 * Created by moziqi on 2015/12/17 0017.
 * 表情库
 */
public enum ExpressionManEnum {

    Batman(R.color.batman, R.mipmap.batman_48px),
    Deathstroke(R.color.death_stroke, R.mipmap.death_stroke_48px),
    DukeNukem(R.color.duke_nukem, R.mipmap.duke_nukem_48px),
    TwoFace(R.color.two_face, R.mipmap.two_face_48px),
    Wolverine(R.color.wolverine, R.mipmap.wolverine_48px);

    private int ResColor;//颜色
    private int ResImg;//图片

    ExpressionManEnum(int resColor, int resImg) {
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
