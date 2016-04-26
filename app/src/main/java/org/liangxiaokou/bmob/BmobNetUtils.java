package org.liangxiaokou.bmob;

import android.content.Context;

import org.liangxiaokou.app.MApplication;
import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by moziqi on 16-4-9.
 */
public class BmobNetUtils {


    /**
     * 注册用户
     *
     * @param context
     * @param email
     * @param password
     * @param saveListener
     */
    public static void signUp(Context context, String email, String password, SaveListener saveListener) {
        User user = new User();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
        user.setNick("");//默认为空
        user.setSex(1);//默认设置为女
        user.setIsOk(false);//默认没完善信息
        user.setHaveLove(false);
        //邮箱验证
        user.setEmailVerified(true);
        user.signUp(context.getApplicationContext(), saveListener);
    }

    /**
     * 登录
     *
     * @param context
     * @param username
     * @param password
     * @param saveListener
     */
    public static void login(Context context, String username, String password, SaveListener saveListener) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(context.getApplicationContext(), saveListener);
    }

    /**
     * 登录
     *
     * @param context
     * @param username
     * @param password
     * @param saveListener
     */
    public static void loginByAccount(Context context, String username, String password, LogInListener saveListener) {
        User.loginByAccount(context.getApplicationContext(), username, password, saveListener);
    }

    /**
     * 通过账号查询用户信息
     *
     * @param context
     * @param findListener
     */
    public static void findUserInfo(Context context, FindListener<User> findListener) {
        String username = "";
        User currentUser = User.getCurrentUser(context.getApplicationContext(), User.class);
        if (currentUser != null) {
            username = currentUser.getUsername();
        }
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("username", username);
        query.findObjects(context.getApplicationContext(), findListener);
    }

    /**
     * 更新用户信息
     *
     * @param context
     * @param user
     * @param userId
     * @param updateListener
     */
    public static void updateUserInfo(Context context, User user, String userId, UpdateListener updateListener) {
        user.update(context, userId, updateListener);
    }

    /**
     * 查询是否已经有关联的好友
     *
     * @param context
     * @param findListener
     */
    public static void queryHasFriend(Context context, FindListener<Friend> findListener) {
        BmobQuery<Friend> friendBmobQuery = new BmobQuery<>();
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = friendBmobQuery.hasCachedResult(context, Friend.class);
        if (isCache) {//--此为举个例子，并不一定按这种方式来设置缓存策略
            friendBmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
        } else {
            friendBmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        friendBmobQuery.setMaxCacheAge(TimeUnit.DAYS.toMillis(1));//此表示缓存一天
        String currentUserId = "";
        User currentUser = User.getCurrentUser(context.getApplicationContext(), User.class);
        if (currentUser != null) {
            currentUserId = currentUser.getObjectId();
        }
        friendBmobQuery.addWhereEqualTo("currentUserId", currentUserId);
        friendBmobQuery.addWhereEqualTo("isLove", 1);
        friendBmobQuery.findObjects(context, findListener);
    }

    /**
     * 保存好友
     *
     * @param context
     * @param friendUserId
     * @param saveListener
     */
    public static void saveFriend(Context context, String friendUserId, SaveListener saveListener) {
        String currentUserId = "";
        User currentUser = User.getCurrentUser(context.getApplicationContext(), User.class);
        if (currentUser != null) {
            currentUserId = currentUser.getObjectId();
        }
        Friend friend = new Friend();
        friend.setIsLove(1);//设置为有爱人
        friend.setCurrentUserId(currentUserId);
        friend.setFriendUserId(friendUserId);
        friend.save(context.getApplicationContext(), saveListener);
    }

}
