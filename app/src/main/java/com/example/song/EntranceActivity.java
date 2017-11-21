package com.example.song;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.song.base.BaseActivity;
import com.example.song.kotlin.MethodTest;
import com.example.song.kotlin.NullSafe;
import com.example.song.kotlin.OperatorTest;
import com.example.song.kotlin.StringTest;

import butterknife.BindView;
import butterknife.OnClick;

public class EntranceActivity extends BaseActivity {

    @BindView(R.id.tv_to_app)
    TextView mTvToApp;
    @BindView(R.id.tv_to_test)
    TextView mTvToTest;
    @BindView(R.id.tv_about)
    TextView mBtnAbout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        /***
         * 测试kotlin打印
         */
        OperatorTest operatorTest = new OperatorTest();
        operatorTest.main();
        MethodTest methodTest = new MethodTest();
        methodTest.main();
        StringTest stringTest = new StringTest();
        stringTest.main();
        NullSafe nullSafe = new NullSafe();
        nullSafe.main();


    }

    @OnClick({R.id.tv_to_app, R.id.tv_to_test,R.id.tv_about})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_to_app:

                break;
            case R.id.tv_to_test:
                startActivity(new Intent(EntranceActivity.this, TestMainActivity.class));
                break;
            case R.id.tv_about:
                showAbout();
                break;
            default:
                break;
        }
    }

    private AlertDialog mAboutDialog;


    private void showAbout(){
        if(mAboutDialog == null ){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View content = getLayoutInflater().inflate(R.layout.dialog_about,null);
            TextView tvVersionName = (TextView) content.findViewById(R.id.tv_version_name);
            TextView tvVersionCode = (TextView) content.findViewById(R.id.tv_version_code);
            TextView tvAppId = (TextView) content.findViewById(R.id.tv_app_id);
            TextView tvBuildType = (TextView) content.findViewById(R.id.tv_build_type);
            TextView tvFlavor = (TextView) content.findViewById(R.id.tv_flavor);
            tvVersionName.append(BuildConfig.VERSION_NAME);
            tvVersionCode.append(BuildConfig.VERSION_CODE+"");
            tvAppId.append(BuildConfig.BUGLY_APP_ID);
            tvBuildType.append(BuildConfig.BUILD_TYPE);
            tvFlavor.append(BuildConfig.FLAVOR);
            builder.setCancelable(true);
            builder.setView(content);
            mAboutDialog = builder.create();
        }
        mAboutDialog.show();
    }
}
