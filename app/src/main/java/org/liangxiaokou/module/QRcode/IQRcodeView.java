package org.liangxiaokou.module.QRcode;

import org.json.JSONObject;
import org.liangxiaokou.app.IView;

/**
 * Created by Administrator on 2016/4/26.
 */
public interface IQRcodeView extends IView {

    public String getTableName();

    public void onSuccess(JSONObject jsonObject);
}
