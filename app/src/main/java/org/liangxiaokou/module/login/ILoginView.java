package org.liangxiaokou.module.login;

import org.liangxiaokou.app.IView;
import org.liangxiaokou.bean.User;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface ILoginView extends IView {

    public String getUsername();

    public String getPassword();

    /**
     * 获取登录后，用户信息
     *
     * @param user
     */
    public void onSuccess(User user);
}
