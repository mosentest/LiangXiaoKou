package org.liangxiaokou.module.QRcode;

import android.content.Context;

import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.config.Constants;
import org.liangxiaokou.util.VolleyLog;

import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/26.
 */
public class ScannerPresenter implements IScannerPresenter {

    private IScannerView iScannerView;

    public ScannerPresenter(IScannerView iScannerView) {
        this.iScannerView = iScannerView;
    }

    @Override
    public void toSaveFriend(final Context context, final String friendUserId, final String friendUserName) {
        iScannerView.showLoading();
        //检查是否已经有另一半
        BmobNetUtils.queryHasFriend(context.getApplicationContext(), new SQLQueryListener<Friend>() {
            @Override
            public void done(BmobQueryResult<Friend> bmobQueryResult, BmobException e) {
                VolleyLog.e("toSaveFriend %s", bmobQueryResult.getResults());
                if (e != null) {
                    iScannerView.onFailure(Constants.scanner_code, e.getMessage());
                    iScannerView.hideLoading();
                    return;
                }
                switch (bmobQueryResult.getCount()) {
                    case 0:
                        //检查是否有记录，如果没有就添加
                        BmobNetUtils.saveFriendBatch(context.getApplicationContext(), friendUserId, friendUserName, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                iScannerView.onSuccess();
                                iScannerView.hideLoading();
                                updateUserHaveLove(context.getApplicationContext());
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                iScannerView.onFailure(i, s);
                                iScannerView.hideLoading();
                            }
                        });
                        break;
                    case 1:
                        //这种情况会不会发生呢？
                        //如果只有一条记录的话，反向，把他加入
                        String currentUserId = "";
                        User currentUser = User.getCurrentUser(context.getApplicationContext(), User.class);
                        if (currentUser != null) {
                            currentUserId = currentUser.getObjectId();
                        }
                        Friend friend = bmobQueryResult.getResults().get(0);
                        if (currentUserId.equals(friend.getCurrentUserId())) {
                            //如果查询的currentUserId  == 当前用户id
                            BmobNetUtils.saveOneFriend(context.getApplicationContext(), friend.getFriendUserId(), currentUser.getObjectId(), currentUser.getNick(), new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    iScannerView.onSuccess();
                                    iScannerView.hideLoading();
                                    updateUserHaveLove(context.getApplicationContext());
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    iScannerView.onFailure(i, s);
                                    iScannerView.hideLoading();
                                }
                            });
                        } else {
                            //如果查询的currentUserId != 当前用户id
                            BmobNetUtils.saveOneFriend(context.getApplicationContext(), currentUserId, friend.getCurrentUserId(), friend.getCurrentName(), new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    iScannerView.onSuccess();
                                    iScannerView.hideLoading();
                                    updateUserHaveLove(context.getApplicationContext());
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    iScannerView.onFailure(i, s);
                                    iScannerView.hideLoading();
                                }
                            });
                        }
                        break;
                    case 2:
                        iScannerView.onSuccess();
                        iScannerView.hideLoading();
                        //如果已经存在2条记录的话，直接直接跳入主界面
                        break;
                    default:
                        iScannerView.hideLoading();
                        break;
                }
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
