package com.example.song.mytest.demo.dialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.song.R;
import com.example.song.base.CommonActivity;
import butterknife.OnClick;

/**
 * Created by zz on 2017/4/11.
 *
 */

public class DialogFragmentTest extends CommonActivity {
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
        mDialog.showAllowingStateLoss(getSupportFragmentManager(),null);
    }

    private void changeAndShow(){
        mDialog.showAllowingStateLoss(getSupportFragmentManager(),null);
        mDialog.setModel(new Model("i have been changed"));
    }


}
