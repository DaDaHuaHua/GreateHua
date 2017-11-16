package com.example.song.homework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.song.base.BaseActivity;
import com.example.song.homework.bean.SingleLinkedList;

/**
 * <p> Created by 宋华 on 2017/11/16.
 */
public class LinkedListActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //没有界面， 看日志
        test();
    }

    private SingleLinkedList<Integer> data2 = new SingleLinkedList<>();

    private void test() {
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
