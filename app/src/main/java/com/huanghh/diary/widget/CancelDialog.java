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

public class CancelDialog extends Dialog implements View.OnClickListener {
    private String mTitle, mContent;
    private IDialogClick mDialogCallback;

    public CancelDialog(@NonNull Context context, String title, String content
            , IDialogClick dialogClick) {
        super(context, R.style.dialog);
        this.mTitle = title;
        this.mContent = content;
        mDialogCallback = dialogClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_cancel);
        setCanceledOnTouchOutside(false);
        initView();
    }

    public CancelDialog setTitle(String title) {
        mTitle = title;
        return this;
    }

    public CancelDialog setContent(String content) {
        mContent = content;
        return this;
    }

    private void initView() {
        TextView tvTitle = findViewById(R.id.tv_title_dialog_cancel);
        TextView tvContent = findViewById(R.id.tv_content_dialog_cancel);
        findViewById(R.id.btn_cancel_dialog_cancel).setOnClickListener(this);
        findViewById(R.id.btn_confirm_dialog_cancel).setOnClickListener(this);


        if (!TextUtils.isEmpty(mTitle)) {
            tvTitle.setText(mTitle);
        }
        if (!TextUtils.isEmpty(mContent)) {
            tvContent.setText(mContent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_dialog_cancel:
                break;
            case R.id.btn_confirm_dialog_cancel:
                mDialogCallback.confirmClickCallback();
                break;
        }
        this.dismiss();
    }
}
