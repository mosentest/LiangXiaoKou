package org.liangxiaokou.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by moziqi on 2016/1/5 0005.
 */
public class SweetHeart extends BmobObject {
    private BmobDate loveDate;//恋爱日期
    private BmobFile lovePhone;//大头照

    public SweetHeart() {
    }

    public BmobDate getLoveDate() {
        return loveDate;
    }

    public void setLoveDate(BmobDate loveDate) {
        this.loveDate = loveDate;
    }

    public BmobFile getLovePhone() {
        return lovePhone;
    }

    public void setLovePhone(BmobFile lovePhone) {
        this.lovePhone = lovePhone;
    }

}
