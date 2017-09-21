package com.example.song.demo.views.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.example.song.R;
import com.example.song.base.BaseActivity;

/**
 * <p> Created by 宋华 on 2017/9/21.
 */
public class DialogTest extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simle_dialog);
        showDialog();
    }

    private AlertDialog mDialog;

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        builder.setView(R.layout.simple_dialog);
        mDialog = builder.create();
        mDialog.show();
    }
}
