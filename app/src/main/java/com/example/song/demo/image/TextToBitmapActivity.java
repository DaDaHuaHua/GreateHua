package com.example.song.demo.image;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.commonlibrary.mobile.DensityUtil;
import com.example.commonlibrary.util.BitmapUtil;
import com.example.commonlibrary.util.StringUtil;
import com.example.commonlibrary.util.bean.TextConfig;
import com.example.song.R;
import com.example.song.base.BaseActivity;
import com.example.song.homework.bean.HashMap;
import com.example.song.homework.bean.SingleLinkedList;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 33105 on 2017/10/18.
 */

public class TextToBitmapActivity extends BaseActivity {
    @BindView(R.id.et_content)
    EditText mEtText;
    @BindView(R.id.iv_show)
    ImageView mIvShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_bitmap);
        //reverse();
        testHashMap();
    }


    private void testHashMap() {
        HashMap<String, String> data = new HashMap<>(8);
        //测试put和get
        System.out.println("测试put和get:");
        data.put("1", "1");
        data.put("2", "2");
        data.put("3", "3");
        data.put("4", "4");
        data.put("4", "4");
        data.put("4", "4");
        System.out.println("size = " + data.size());
        for (int i = 1; i <= 4; i++) {
            System.out.println("hashMap: key=" + i + "时,   value=" + data.get(i + ""));
        }
        //===========================================
        //测试remove和set
        System.out.println("\n\n测试remove和set:");
        data.remove("1");
        System.out.println("hashMap: key=1时,   value=" + data.get("1"));
        data.put("1", "1");
        System.out.println("hashMap: key=1时,   value=" + data.get("1"));
        data.set("1", "-1");
        System.out.println("hashMap: key=1时,   value=" + data.get("1"));
        //=============================================================
        //测试扩容
        System.out.println("\n\n测试扩容:");
        data.put("5", "5");
        data.put("6", "6");
        data.put("7", "7");
        data.put("8", "8");
        data.put("9", "9");

        int size = data.size();
        System.out.println("size = " + size);
        for (int i = 1; i <= size; i++) {
            System.out.println("hashMap: key=" + i + "时,   value=" + data.get(i + ""));
        }
        //==========================================================
        //测试put null值
        System.out.println("\n\n测试key为null :");
        data.put(null, "null");
        System.out.println("null 值为："+data.get(null));
        data.put(null, "null,null");
        System.out.println("null 值为："+data.get(null));
        System.out.println("put null 后的Size =="+data.size());

    }


    @OnClick(R.id.tv_create_bitmap)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_create_bitmap:
                Bitmap bitmap = createBitmap();
                if (bitmap == null) {
                    mIvShow.setImageResource(R.drawable.bg_watermark);
                } else {
                    mIvShow.setImageBitmap(createBitmap());
                }
                break;
            default:
                break;
        }
    }

    private Bitmap createBitmap() {
        String text = mEtText.getText().toString();
        if (StringUtil.isBlank(text)) {
            text = "掌门全球直播";
        }
        TextConfig config = new TextConfig();
        config.textColor = Color.WHITE;
        config.text = text;
        return BitmapUtil.createBitmap(getResources(), R.drawable.icon_text_simple, config, Color.WHITE, DensityUtil.sp2px(15));
    }

    private SingleLinkedList<Integer> data2 = new SingleLinkedList<>();

    private void reverse() {
        data2.add(1);
        data2.add(2);
        data2.add(3);
        data2.add(4);
        data2.add(5);
        data2.add(0, 0);
        data2.add(6, 6);
        data2.remove(4);
        data2.add(4, 4);
        data2.revert();
        System.out.println("first:" + data2.first.item + "  size=" + data2.size);
        for (int i = 0; i < data2.size; i++) {
            printSingleLinkedList(i);
        }
    }

    public void printSingleLinkedList(int i) {
        SingleLinkedList.Node<Integer> node = data2.node(i);
        if (node == null) {
            System.out.println("node is null");
        } else {
            SingleLinkedList.Node<Integer> next = data2.node(i).next;
            System.out.println("第" + i + "个元素为:" + node.item + "  next:" + (next == null ? "null " : next.item));
        }
    }
}
