package com.example.song.homework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.song.base.BaseActivity;
import com.example.song.homework.bean.BinarySearchTree;

/**
 * <p> Created by 宋华 on 2017/11/16.
 */
public class BinarySearchTreeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BinarySearchTree<Integer> data = new BinarySearchTree<>();
        data.add(2);
        data.add(12);
        data.add(3);
        data.add(5);
        data.add(1);
        data.add(13);
        data.set(14,14);
        data.printMidOrder();
    }
}
