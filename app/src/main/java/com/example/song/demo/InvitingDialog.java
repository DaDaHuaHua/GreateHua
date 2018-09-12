package com.example.song.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.song.R;

/**
 * info:
 * <p>
 * Created by songhua on 2018/9/10.
 */
public class InvitingDialog extends AlertDialog {


    protected InvitingDialog(Context context) {
        super(context, R.style.CommonDialog);
    }

    protected InvitingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected InvitingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        if (dialogWindow != null) {

            dialogWindow.setWindowAnimations(R.style.FullDialogAnim);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.BOTTOM);
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.dimAmount = 0.6f;
            dialogWindow.setAttributes(lp);
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.simple_dialog, null);
        setContentView(view);
    }
}
