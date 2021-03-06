package com.example.song.demo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.song.R;
import com.example.song.base.BaseActivity;
import com.example.song.demo.views.custom_view.CustomViewActivity;
import com.example.song.demo.views.view_property.ViewPropertyActivity;
import com.example.song.demo.views.dialogfragment.DialogFragmentMain;
import com.example.song.demo.views.dragsort.DSMain;
import com.example.song.demo.views.floating_window.FloatingWindowMain;
import com.example.song.demo.views.material_design.CoordinatorLayoutMain;
import com.example.song.demo.views.rebound.ReboundActivity;
import com.example.song.demo.views.recycler_view.RecyclerViewMain;
import com.example.song.demo.views.shared_element.SharedElementActivityA;
import com.example.song.demo.views.vector.VectorActivity;
import com.example.song.demo.views.view_pager.ViewPagerMain;

import butterknife.BindView;

/**
 * <p> Created by 宋华 on 2017/9/13.
 */
public class ViewsMain extends BaseActivity {
    @BindView(R.id.list)
    ListView mLv;

    private String[] menu = {"DialogFragment", "RecyclerView", "CoordinatorLayoutDemo", "DSLV / DSGV",
            "facebook-Rebound", "ViewPager", "自定义View", "FloatingWindow", "Vector", "SharedElement","clipChild"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);
        mLv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu) {
        });
        mLv.setOnItemClickListener(new ViewsMain.MyItemClickListener());
    }

    private void startActivity(Class<?> c) {
        startActivity(new Intent(this, c));
    }

    private class MyItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {

                case 0:
                    startActivity(DialogFragmentMain.class);
                    break;
                case 1:
                    startActivity(RecyclerViewMain.class);
                    break;
                case 2:
                    startActivity(CoordinatorLayoutMain.class);
                    break;
                case 3:
                    startActivity(DSMain.class);
                    break;
                case 4:
                    startActivity(ReboundActivity.class);
                    break;
                case 5:
                    startActivity(ViewPagerMain.class);
                    break;
                case 6:
                    startActivity(CustomViewActivity.class);
                    break;
                case 7:
                    startActivity(FloatingWindowMain.class);
                    break;
                case 8:
                    startActivity(VectorActivity.class);
                    break;
                case 9:
                    startActivity(SharedElementActivityA.class);
                    break;
                case 10:
                    startActivity(ViewPropertyActivity.class);

            }
        }
    }

    private static final String TAG = "ViewsMain";

}
