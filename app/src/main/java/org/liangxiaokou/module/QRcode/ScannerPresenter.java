package org.liangxiaokou.module.QRcode;

import android.content.Context;

import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.config.Constants;
import org.liangxiaokou.util.VolleyLog;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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
                        BmobQuery<User> query = new BmobQuery<User>();
                        query.addWhereEqualTo("objectId", friendUserId);
                        query.findObjects(context, new FindListener<User>() {
                            @Override
                            public void onSuccess(List<User> object) {
                                if (object != null && object.size() > 0) {
                                    //更新自己用户信息
                                    User user = new User();
                                    user.setHaveLove(true);
                                    user.setLoveDateObjectId(object.get(0).getLoveDateObjectId());
                                    User currentUser = User.getCurrentUser(context.getApplicationContext(), User.class);
                                    //执行更新用户信息
                                    BmobNetUtils.updateUserInfo(context, user, currentUser.getObjectId(), new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            //检查是否有记录，如果没有就添加
                                            BmobNetUtils.saveFriendBatch(context.getApplicationContext(), friendUserId, friendUserName, new SaveListener() {
                                                @Override
                                                public void onSuccess() {
                                                    iScannerView.onSuccess();
                                                    iScannerView.hideLoading();
                                                }

                                                @Override
                                                public void onFailure(int i, String s) {
                                                    iScannerView.onFailure(i, s);
                                                    iScannerView.hideLoading();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            iScannerView.onFailure(i, s);
                                            iScannerView.hideLoading();
                                        }
                                    });
                                } else {
                                    iScannerView.onFailure(-1, "不存在该用户id");
                                    iScannerView.hideLoading();
                                }
                            }

                            @Override
                            public void onError(int code, String msg) {
                                iScannerView.onFailure(code, msg);
                                iScannerView.hideLoading();
                            }
                        });
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
}
