package com.example.song.demo.views.dialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.song.R;
import com.example.song.base.BaseActivity;
import butterknife.OnClick;

/**
 * Created by zz on 2017/4/11.
 *
 */

public class DialogFragmentMain extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fragment);
    }

    @OnClick({R.id.btn_show_dialog,R.id.btn_change})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_show_dialog:
                showDialogFragment();
                break;
            case R.id.btn_change:
                changeAndShow();
                break;
        }
    }

    private ExampleDialogFragment mDialog;

    private void showDialogFragment() {
        if (mDialog == null) {
            mDialog = ExampleDialogFragment.newInstance();
        }
        mDialog.setModel(new Model("i am initial"));
        mDialog.showAllowingStateLoss(getSupportFragmentManager(),null);
    }

    private void changeAndShow(){
        mDialog.setModel(new Model("i have been changed"));
        mDialog.showAllowingStateLoss(getSupportFragmentManager(),null);
    }


}
