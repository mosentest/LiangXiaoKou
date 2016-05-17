package org.liangxiaokou.module.setlovedate;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bigkoo.pickerview.TimePickerView;

import org.liangxiaokou.module.R;
import org.liangxiaokou.app.GeneralFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class SetLoveDateActivityFragment extends GeneralFragment implements TextWatcher {

    private LoveDateTextListener mLoveDateTextListener;

    private TextInputLayout textInputDate;

    private TimePickerView pvTime;

    public SetLoveDateActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_set_love_date, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyLoad();
    }

    @Override
    public void initView() {
        textInputDate = (TextInputLayout) findViewById(R.id.textInput_date);
    }

    @Override
    public void initData() {
//        textInputDate.setHint("请输入，例如：2015-02-14");
        EditText editTextDate = textInputDate.getEditText();
        editTextDate.setFocusable(false);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
        editTextDate.addTextChangedListener(this);
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                textInputDate.getEditText().setText(getTime(date));
            }
        });
    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void PreOnStart() {

    }

    @Override
    public void PreOnResume() {

    }

    @Override
    public void PreOnPause() {
        pvTime.dismiss();
    }

    @Override
    public void PreOnStop() {
        pvTime.dismiss();
    }

    @Override
    public void PreOnDestroy() {
        pvTime.dismiss();
        pvTime = null;
    }

    @Override
    protected void lazyLoad() {
//        if (!isPrepared || !isVisible) {
//            return;
//        }
        initView();
        initData();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mLoveDateTextListener.onTextChange(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface LoveDateTextListener {
        public void onTextChange(CharSequence text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mLoveDateTextListener = (LoveDateTextListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnArticleSelectedListener");
        }
    }
}
