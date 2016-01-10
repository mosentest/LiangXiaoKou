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

import org.liangxiaokou.module.R;
import org.liangxiaokou.app.GeneralFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class SetLoveDateActivityFragment extends GeneralFragment implements TextWatcher {

    private LoveDateTextListener mLoveDateTextListener;

    private TextInputLayout textInputDate;

    public SetLoveDateActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_love_date, container, false);
    }

    @Override
    protected void initView() {
        textInputDate = (TextInputLayout) findViewById(R.id.textInput_date);
    }

    @Override
    protected void initData() {
//        textInputDate.setHint("请输入，例如：2015-02-14");
        EditText editTextDate = textInputDate.getEditText();
        editTextDate.addTextChangedListener(this);
    }

    @Override
    protected void PreOnStart() {

    }

    @Override
    protected void PreOnResume() {

    }

    @Override
    protected void PreOnPause() {

    }

    @Override
    protected void PreOnStop() {

    }

    @Override
    protected void PreOnDestroy() {

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
