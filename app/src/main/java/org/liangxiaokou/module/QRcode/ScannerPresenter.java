package org.liangxiaokou.module.QRcode;

import android.content.Context;

import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobNetUtils;

import java.util.List;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/26.
 */
public class ScannerPresenter {

    private IScannerView iScannerView;

    public ScannerPresenter(IScannerView iScannerView) {
        this.iScannerView = iScannerView;
    }

    public void toSaveFriend(final Context context, final String friendUserId) {
        iScannerView.showLoading();
        //检查是否已经有另一半
        BmobNetUtils.queryHasFriend(context.getApplicationContext(), new FindListener<Friend>() {
            @Override
            public void onSuccess(List<Friend> list) {
                if (list != null && list.size() == 0) {
                    BmobNetUtils.saveFriend(context.getApplicationContext(), friendUserId, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            updateUserHaveLove(context);
                            iScannerView.onSuccess();
                            iScannerView.hideLoading();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            iScannerView.onFailure(i, s);
                            iScannerView.hideLoading();
                        }
                    });
                } else {
                    updateUserHaveLove(context);
                    iScannerView.onFailure(-1, "当前用户已有另一半");
                    iScannerView.hideLoading();
                }
            }

            @Override
            public void onError(int i, String s) {
                iScannerView.onFailure(i, s);
                iScannerView.hideLoading();
            }
        });
    }

    /**
     * 实现更新用户的状态
     *
     * @param context
     */
    private void updateUserHaveLove(Context context) {
        User user = new User();
        user.setHaveLove(true);
        User currentUser = User.getCurrentUser(context.getApplicationContext(), User.class);
        //执行更新用户信息
        BmobNetUtils.updateUserInfo(context, user, currentUser.getObjectId(), null);
    }
}
