package com.example.song.homework.bean;

import java.util.NoSuchElementException;

/**
 * <p> Created by 宋华 on 2017/11/16.
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private TreeNode<T> mRoot;
    private int size;

    public void add(T t) {
        TreeNode<T> newNode = new TreeNode<>();
        newNode.data = t;
        if (mRoot == null) {
            mRoot = newNode;
        } else {
            TreeNode<T> target = mRoot;
            TreeNode<T> parent = null;
            while (target != null) {
                parent = target;
                //t<target,向左找
                if (t.compareTo(target.data) < 0) {
                    target = target.leftChild;
                } else {//t>target,向右找
                    target = target.rightChild;
                }
            }
            if (t.compareTo(parent.data) < 0) {
                parent.leftChild = newNode;
            } else {
                parent.rightChild = newNode;
            }
            newNode.parent = parent;
        }
        size++;
    }

    public void set(T t, T newValue) {
        TreeNode<T> target = get(t);
        if (target == null) {
            throw new NoSuchElementException("没有该元素");
        } else {
            target.data = newValue;
        }
    }

    public void delete(T t){
        TreeNode<T> node = get(t);
        if(node == null ){
            throw new NoSuchElementException("没有该元素");
        }
        TreeNode<T> parent = node.parent;
        //是根节点
        if(parent == null){
            mRoot = null;
        }else{
            //是叶子节点
            if(node.leftChild == null && node.rightChild == null ){
                //在parent的左边
                if(node.data.compareTo(parent.leftChild.data)< 0 ){
                    parent.leftChild = null;
                }else{
                    parent.rightChild =null;
                }
                node.parent = null;
            }//只有左子节点
            else if(node.leftChild != null ){
                //在parent的左边
                if(node.data.compareTo(parent.leftChild.data)<0){
                    parent.leftChild = node.leftChild;
                    node.leftChild.parent = parent;
                }else{
                    parent.rightChild = node.leftChild;
                    node.leftChild.parent = parent;
                }
                node.parent = null;
            }//只有右子节点
            else  if(node.rightChild != null){
                if(node.data.compareTo(parent.leftChild))
            }
        }

    }

    private TreeNode<T> get(T t) {
        TreeNode<T> target = mRoot;
        while (target != null) {
            if (t.compareTo(target.data) < 0) {
                target = target.leftChild;
            } else if (t.compareTo(target.data) > 0) {
                target = target.rightChild;
            } else {
                return target;
            }
        }
        return null;
    }

    public void printMidOrder() {
        midOrderTraverse(mRoot);
    }

    private void midOrderTraverse(TreeNode<T> node) {
        if (node == null) {
            return;
        }
        midOrderTraverse(node.leftChild);
        System.out.println(node.data + " ,");
        midOrderTraverse(node.rightChild);
    }


    private static class TreeNode<T> {
        TreeNode<T> parent;
        TreeNode<T> leftChild;
        TreeNode<T> rightChild;
        T data;
    }
}
