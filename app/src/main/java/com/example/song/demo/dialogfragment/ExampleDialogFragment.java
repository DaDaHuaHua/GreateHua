package com.example.song.mytest.demo.dialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.song.R;
import com.example.song.base.BaseDialogFragment;

import butterknife.BindView;

/**
 * Created by zz on 2017/4/11.
 *
 */

public class ExampleDialogFragment extends BaseDialogFragment {

    private TextView mTvDesc;
    private View mContent;
    private Model mModel;

    public static  ExampleDialogFragment newInstance( ){
        return  new ExampleDialogFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("model", mModel);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mModel != null) {
            return;
        }
        if (savedInstanceState != null) {
            mModel = savedInstanceState.getParcelable("model");
        } else {
            dismissAllowingStateLoss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.content_dialog_fragment, container, false);
        init();
        return mContent;
    }

    private void init(){
        mTvDesc = $(mContent,R.id.tv_desc);
        mTvDesc.setText(mModel.desc);
    }

    @Override
    protected int getDialogHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getDialogWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getWindowGravity() {
        return 0;
    }

    public void setModel(Model model){
        this.mModel = model;
    }


}
