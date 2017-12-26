package com.example.song.homework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.song.base.BaseActivity;
import com.example.song.homework.bean.BinarySearchTree;

/**
 * <p> Created by 宋华 on 2017/11/16.
 */
public class BinarySearchTreeActivity extends BaseActivity {
    BinarySearchTree<Integer> data = new BinarySearchTree<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


         test01();
//        testDel02();
//        testDel03();
        data.printMidOrder();
    }

    private void test01() {
        data.add(10);
        data.add(2);
        data.add(3);
        data.add(100);
        data.add(1);
        data.add(50);
        data.add(20);
        data.add(120);
        data.add(40);
        data.add(60);
        data.add(110);
        data.add(150);
        data.add(115);
        data.add(14);
        data.set(14, 11);
//        data.delete01(100);
        data.delete01(10);
    }

    private void testDel02() {
        data.add(10);
        data.add(2);
        data.add(100);
        data.add(1);
        data.add(5);
        data.add(4);
        data.add(6);
        data.add(80);
        data.add(120);
        data.add(70);
        data.add(90);
        data.add(110);
        data.add(140);
        data.add(105);
        data.add(115);
        data.add(106);
        data.add(118);
        data.add(117);
        data.delete02(120);
    }

    private void testDel03() {
        data.add(10);
        data.add(2);
        data.add(100);
        data.add(1);
        data.add(5);
        data.add(4);
        data.add(6);
        data.add(80);
        data.add(120);
        data.add(70);
        data.add(90);
        data.add(110);
        data.add(140);
        data.add(105);
        data.add(115);
        data.add(106);
        data.add(118);
        data.add(117);
        data.delete03(100);
        data.delete03(120);
        data.delete03(140);
        data.delete03(105);
        data.delete03(106);
        data.delete03(110);
        data.delete03(115);
        data.delete03(117);
        data.delete03(118);

    }
}
