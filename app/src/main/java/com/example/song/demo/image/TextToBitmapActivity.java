package com.example.song.demo.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.commonlibrary.util.StringUtil;
import com.example.song.R;
import com.example.song.base.BaseActivity;
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
        reverse();
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
            default:break;
        }
    }

    private Bitmap createBitmap() {
        String text = mEtText.getText().toString();
        if (StringUtil.isBlank(text)) {
            text = "掌门全球直播";
        }
//        return BitmapUtil.createBitmapFromText(text, Color.WHITE, DensityUtil.sp2px(15));
//        return BitmapUtil.createBitmap(getResources(),R.drawable.icon_text_simple,text,Color.WHITE,DensityUtil.sp2px(15));
        return null;
    }
    private SingleLinkedList<Integer> data2 = new SingleLinkedList<>();
    private void reverse(){
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
    public  void printSingleLinkedList(int i) {
        SingleLinkedList.Node<Integer> node = data2.node(i);
        if (node == null) {
            System.out.println("node is null");
        } else {
            SingleLinkedList.Node<Integer> next = data2.node(i).next;
            System.out.println("第" + i + "个元素为:" + node.item + "  next:" + (next == null ? "null " : next.item));
        }
    }
}
