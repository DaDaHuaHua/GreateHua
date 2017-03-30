package com.example.song.greatehua.testdialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.commonlibrary.base.fragment.BaseDialogFragment;
import com.example.song.greatehua.R;


/**
 * Created by Song on 2017/1/20.
 *
 */

public class MyDialogFragment extends BaseDialogFragment {
    private TextView mTvData;
    private String mData ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.test_dialog_fragment, container, false);
        mTvData = $(root, R.id.tv_data);
        return root;
    }

    @Override
    protected int getWindowGravity() {
        return 0;
    }

    public void setData(String str) {
        this.mData = str;
        if(mTvData != null ){
            mTvData.setText(mData);
        }
    }
}
