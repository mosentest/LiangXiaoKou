package org.liangxiaokou.module.register;

import org.liangxiaokou.app.IView;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface IRegisterView extends IView {
    public String getUsername();

    public String getPassword();

    public String getRePassword();
}
