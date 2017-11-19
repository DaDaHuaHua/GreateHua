package com.example.song.homework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.song.base.BaseActivity;

import com.example.song.homework.bean.HaffmanTree;

/**
 * <p> Created by 宋华 on 2017/11/16.
 */
public class HaffmanTreeActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HaffmanTree<Integer> data = new HaffmanTree<>(buildData());
        data.printData();

    }

    private  HaffmanTree.HaffmanTreeNode<Integer>[] buildData(){
        HaffmanTree.HaffmanTreeNode[] node = new HaffmanTree.HaffmanTreeNode[10];
        for (int i = 0; i < 10; i++) {
            node[i] = new HaffmanTree.HaffmanTreeNode<>(i,i+1);
        }
        return node;
    }


}
