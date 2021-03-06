package com.huanghh.diary.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.huanghh.diary.R;
import com.huanghh.diary.interfaces.IDialogClick;

public class ConfirmDialog extends Dialog implements View.OnClickListener {
    private String mTitle, mContent;
    private IDialogClick mDialogCallback;
    private TextView mTv_title, mTv_content;

    public ConfirmDialog(@NonNull Context context, String title, String content
            , IDialogClick dialogClick) {
        super(context, R.style.dialog);
        this.mTitle = title;
        this.mContent = content;
        mDialogCallback = dialogClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_confirm);
        setCanceledOnTouchOutside(false);
        initView();
    }

    public ConfirmDialog setTitle(String title) {
        mTv_title.setText(title);
        return this;
    }

    public ConfirmDialog setContent(String content) {
        mTv_content.setText(content);
        return this;
    }

    private void initView() {
        mTv_title = findViewById(R.id.tv_title_dialog_cancel);
        mTv_content = findViewById(R.id.tv_content_dialog_cancel);
        findViewById(R.id.btn_confirm_dialog_cancel).setOnClickListener(this);


        if (!TextUtils.isEmpty(mTitle)) {
            mTv_title.setText(mTitle);
        }
        if (!TextUtils.isEmpty(mContent)) {
            mTv_content.setText(mContent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_dialog_cancel:
                mDialogCallback.confirmClickCallback();
                break;
        }
        this.dismiss();
    }
}
