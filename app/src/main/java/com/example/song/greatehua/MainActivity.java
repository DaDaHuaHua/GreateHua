package com.example.song.greatehua;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.song.greatehua.base.CommonActivity;
import com.example.song.greatehua.testdialogfragment.MyDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends CommonActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.tv_show_dialog)
    protected TextView mTvShowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private MyDialogFragment mDialogFragment;

    @OnClick(R.id.tv_show_dialog)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_show_dialog:
                if (mDialogFragment == null) {
                    mDialogFragment = new MyDialogFragment();
                    mDialogFragment.setData("this is new fragment");
                  // mDialogFragment.show(getSupportFragmentManager(), null);
                    getSupportFragmentManager().beginTransaction().show(mDialogFragment).commit();
                } else {
                    mDialogFragment.setData("already exist , just show");
                    getSupportFragmentManager().beginTransaction().remove(mDialogFragment).commit();
                    getSupportFragmentManager().beginTransaction().show(mDialogFragment).commit();
                    //mDialogFragment.show(getSupportFragmentManager(), null);
                }
                break;
        }
    }
}
