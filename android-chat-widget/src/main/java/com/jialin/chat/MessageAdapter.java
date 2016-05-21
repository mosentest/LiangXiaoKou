package com.jialin.chat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import org.mo.glide.ImageUtils;
import org.mo.widget.R;

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<Message> data = null;

    public MessageAdapter(Context context, List<Message> list) {
        super();
        this.context = context;
        this.data = list;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return this.data.get(position).getIsSend() ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {

        final Message message = data.get(position);
        boolean isSend = message.getIsSend();

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            if (isSend) {
                convertView = LayoutInflater.from(context).inflate(R.layout.msg_item_right, null);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.msg_item_left, null);
            }
            viewHolder.sendDateTextView = (TextView) convertView.findViewById(R.id.sendDateTextView);
            viewHolder.sendTimeTextView = (TextView) convertView.findViewById(R.id.sendTimeTextView);
            viewHolder.userAvatarImageView = (ImageView) convertView.findViewById(R.id.userAvatarImageView);
            viewHolder.userNameTextView = (TextView) convertView.findViewById(R.id.userNameTextView);
            viewHolder.textTextView = (TextView) convertView.findViewById(R.id.textTextView);
            viewHolder.photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
            viewHolder.faceImageView = (ImageView) convertView.findViewById(R.id.faceImageView);
            viewHolder.failImageView = (ImageView) convertView.findViewById(R.id.failImageView);
            viewHolder.sendingProgressBar = (ProgressBar) convertView.findViewById(R.id.sendingProgressBar);


            viewHolder.isSend = isSend;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            String dateString = DateFormat.format("yyyy-MM-dd h:mmaa", message.getTime()).toString();
            String[] t = dateString.split(" ");
            //viewHolder.sendTimeTextView.setText(t[1]);
            if (position == 0) {
                viewHolder.sendDateTextView.setText(t[0]);
                viewHolder.sendDateTextView.setVisibility(View.VISIBLE);
            } else {
                //TODO is same day ?
                Message pmsg = data.get(position - 1);
                if (inSameDay(pmsg.getTime(), message.getTime())) {
                    //viewHolder.sendDateTextView.setVisibility(View.GONE);
                    viewHolder.sendDateTextView.setText(friendlyTime(message.getTime()));
                } else {
                    viewHolder.sendDateTextView.setText(t[0]);
                    //viewHolder.sendDateTextView.setVisibility(View.VISIBLE);
                }

            }

        } catch (Exception e) {
        }

        //设置个人头像
        ImageUtils.loadChatUserImg(context, viewHolder.userAvatarImageView, message.getIsSend() ? Integer.parseInt(message.getFromUserAvatar()) : Integer.parseInt(message.getToUserAvatar()));

        //viewHolder.userNameTextView.setText(message.getToUserName());


        switch (message.getType()) {
            case 0://text
                viewHolder.textTextView.setText(message.getContent());
                viewHolder.textTextView.setVisibility(View.VISIBLE);
                viewHolder.photoImageView.setVisibility(View.GONE);
                viewHolder.faceImageView.setVisibility(View.GONE);
                if (message.getIsSend()) {
                    LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
                    sendTimeTextViewLayoutParams.addRule(RelativeLayout.LEFT_OF, R.id.textTextView);
                    viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);

                    LayoutParams layoutParams = (LayoutParams) viewHolder.failImageView.getLayoutParams();
                    layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.textTextView);
                    if (message.getSendSucces() != null && message.getSendSucces() == false) {
                        viewHolder.failImageView.setVisibility(View.VISIBLE);
                        viewHolder.failImageView.setLayoutParams(layoutParams);
                    } else {
                        viewHolder.failImageView.setVisibility(View.GONE);
                    }

                    if (message.getState() != null && message.getState() == 0) {
                        viewHolder.sendingProgressBar.setVisibility(View.VISIBLE);
                        viewHolder.sendingProgressBar.setLayoutParams(layoutParams);
                    } else {
                        viewHolder.sendingProgressBar.setVisibility(View.GONE);
                    }

                } else {
                    viewHolder.failImageView.setVisibility(View.GONE);
                    viewHolder.sendingProgressBar.setVisibility(View.GONE);

                    LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
                    sendTimeTextViewLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.textTextView);
                    viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);
                }


                break;
            case 1://photo
                viewHolder.textTextView.setVisibility(View.GONE);
                viewHolder.photoImageView.setVisibility(View.VISIBLE);
                viewHolder.faceImageView.setVisibility(View.GONE);

                //TODO set image
                int id = context.getResources().getIdentifier(message.getContent(), "drawable", context.getPackageName());
                viewHolder.photoImageView.setImageResource(id);


                if (message.getIsSend()) {
                    LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
                    sendTimeTextViewLayoutParams.addRule(RelativeLayout.LEFT_OF, R.id.photoImageView);
                    viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);

                    LayoutParams layoutParams = (LayoutParams) viewHolder.failImageView.getLayoutParams();
                    layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.photoImageView);
                    if (message.getSendSucces() != null && message.getSendSucces() == false) {
                        viewHolder.failImageView.setVisibility(View.VISIBLE);
                        viewHolder.failImageView.setLayoutParams(layoutParams);
                    } else {
                        viewHolder.failImageView.setVisibility(View.GONE);
                    }

                    if (message.getState() != null && message.getState() == 0) {
                        viewHolder.sendingProgressBar.setVisibility(View.VISIBLE);
                        viewHolder.sendingProgressBar.setLayoutParams(layoutParams);
                    } else {
                        viewHolder.sendingProgressBar.setVisibility(View.GONE);
                    }

                } else {
                    viewHolder.failImageView.setVisibility(View.GONE);
                    LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
                    sendTimeTextViewLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.photoImageView);
                    viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);
                }


                break;

            case 2://face
                viewHolder.photoImageView.setVisibility(View.GONE);
                viewHolder.textTextView.setVisibility(View.GONE);
                viewHolder.faceImageView.setVisibility(View.VISIBLE);
                int resId = context.getResources().getIdentifier(message.getContent(), "drawable", context.getPackageName());
                ImageUtils.loadImgResourceId(context, viewHolder.faceImageView, resId);
                //viewHolder.faceImageView.setImageResource(resId);

                if (message.getIsSend()) {
                    LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
                    sendTimeTextViewLayoutParams.addRule(RelativeLayout.LEFT_OF, R.id.faceImageView);
                    viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);

                    LayoutParams layoutParams = (LayoutParams) viewHolder.failImageView.getLayoutParams();
                    layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.faceImageView);
                    if (message.getSendSucces() != null && message.getSendSucces() == false) {
                        viewHolder.failImageView.setVisibility(View.VISIBLE);
                        viewHolder.failImageView.setLayoutParams(layoutParams);
                    } else {
                        viewHolder.failImageView.setVisibility(View.GONE);
                    }

                    if (message.getState() != null && message.getState() == 0) {
                        viewHolder.sendingProgressBar.setVisibility(View.VISIBLE);
                        viewHolder.sendingProgressBar.setLayoutParams(layoutParams);
                    } else {
                        viewHolder.sendingProgressBar.setVisibility(View.GONE);
                    }

                } else {
                    viewHolder.failImageView.setVisibility(View.GONE);

                    LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
                    sendTimeTextViewLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.faceImageView);
                    viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);
                }
                break;
            default:
                viewHolder.textTextView.setText(message.getContent());
                viewHolder.photoImageView.setVisibility(View.GONE);
                viewHolder.faceImageView.setVisibility(View.GONE);
                break;
        }

//		viewHolder.textTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        return convertView;
    }


    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }


    public static String friendlyTime(Date time) {
        String dateString = DateFormat.format("yyyy-MM-dd h:mmaa", time).toString();
        String[] split = dateString.split(" ");
        //获取time距离当前的秒数
        int ct = (int) ((System.currentTimeMillis() - time.getTime()) / 1000);
        if (ct == 0) {
            return "刚刚";
        } else if (ct > 0 && ct < 60) {
            return ct + "秒前";
        } else if (ct >= 60) {
            return split[1];
        }
//        } else if (ct >= 60 && ct < 3600) {
//            return Math.max(ct / 60, 1) + "分钟前";
//        } else if (ct >= 3600 && ct < 86400)
//            return ct / 3600 + "小时前";
//        else if (ct >= 86400 && ct < 2592000) { //86400 * 30
//            int day = ct / 86400;
//            return day + "天前";
//        } else if (ct >= 2592000 && ct < 31104000) { //86400 * 30
//            return ct / 2592000 + "月前";
//        }
        return ct / 31104000 + "年前";
    }

    public static boolean inSameDay(Date date1, Date Date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(Date2);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        if ((year1 == year2) && (month1 == month2) && (day1 == day2)) {
            return true;
        }
        return false;
    }


    /**
     * 局部更新的方式
     *
     * @param listView
     * @param index
     * @param sendStatus
     */
    @Deprecated
    public void onUpDataItem(ListView listView, int index, int sendStatus) {
        int firstVisiblePosition = listView.getFirstVisiblePosition();//可见区第一个Item脚标
        int lastVisiblePosition = listView.getLastVisiblePosition();//可见区最后一个Item脚标
        if (index - firstVisiblePosition >= 0 && index <= lastVisiblePosition) {
            // 更新目标view
            View view = listView.getChildAt(index - firstVisiblePosition);
            if (view == null)
                return;
            // 从view中取得holder
            ViewHolder holder = (ViewHolder) view.getTag();
            if (sendStatus == 3) {
                holder.sendingProgressBar.setVisibility(View.GONE);
                LayoutParams layoutParams = (LayoutParams) holder.failImageView.getLayoutParams();
                layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.faceImageView);
                holder.failImageView.setVisibility(View.VISIBLE);
                holder.failImageView.setLayoutParams(layoutParams);
            } else {
                holder.sendingProgressBar.setVisibility(View.GONE);
                holder.failImageView.setVisibility(View.GONE);
            }
        }
    }

    static class ViewHolder {

        public ImageView userAvatarImageView;
        public TextView sendDateTextView;
        public TextView userNameTextView;

        public TextView textTextView;
        public ImageView photoImageView;
        public ImageView faceImageView;

        public ImageView failImageView;
        public TextView sendTimeTextView;
        public ProgressBar sendingProgressBar;

        public boolean isSend = true;
    }


}
