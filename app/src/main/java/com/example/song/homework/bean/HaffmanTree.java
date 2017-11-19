package com.example.song.homework.bean;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by 宋华 on 2017/11/19.
 * 思路：
 * 1.将数据从小到大排序
 * 2.将前两个数据生成一个树，根节点parent作为新的权
 * 3.从第三个数据开始遍历，每个数据与parent构建出一个新的树
 * <p>
 * 代码描述：
 * 外部输入单元：HaffmanTreeNode：只有数据和权重
 * 树的数据单元：InnerNode ，继承自HaffmanTreeNode，增加了左子树指针，右子树指针， 父节点指针
 */

public class HaffmanTree<T> {

    private InnerNode<T> mRoot;
    private HaffmanTreeNode<T>[] mData;

    public HaffmanTree(HaffmanTreeNode<T>[] data) {
        if (data == null) {
            return;
        }
        //1.先将数组按照权重排序
        Arrays.sort(data, comparator);
        mData = data;
        //2.根据节点生成树
        InnerNode<T> parent = null;
        //2.1 只有一个数据
        if (data[1] != null) {
            mRoot = new InnerNode<>(data[1]);
        } else {
            return;
        }
        //2.2 有2或2以上个数据
        if (data[2] != null) {
            parent = buildParent(new InnerNode<>(data[1]), new InnerNode<T>(data[2]));
        } else {
            return;
        }

        //3 从第3个数据开始，循环构建树
        for (int i = 2; i < data.length; i++) {
            HaffmanTreeNode<T> node = data[i];
            if (node == null) {
                return;
            }
            InnerNode<T> leftNode = new InnerNode<>(node);
            //3.1连接新节点和parent生成新的parent
            parent = buildParent(leftNode, parent);
        }
    }

    /**
     * 根据传入左节点和右树根节点生成根节点创建出一个新的树
     *
     * @param left
     * @param right
     */
    private InnerNode<T> buildParent(InnerNode<T> left, InnerNode<T> right) {
        if (right == null) {
            return left;
        }
        InnerNode<T> parent = new InnerNode<T>();
        parent.weight = left.weight + right.weight;
        parent.leftChild = left;
        parent.rightChild = right;
        left.parent = parent;
        right.parent = parent;
        mRoot = parent;
        return parent;
    }

    public void printData(){
        midOrderTraverse(mRoot);
    }


    private void midOrderTraverse(InnerNode<T> node) {
        if (node == null) {
            return;
        }
        midOrderTraverse(node.leftChild);
        System.out.println("data ="+node.data + " , weight =" + node.weight);
        midOrderTraverse(node.rightChild);
    }

    public static class HaffmanTreeNode<T> {

        /**
         * 数据
         */
        T data;
        /**
         * 权重
         */
        int weight;

        public HaffmanTreeNode() {

        }

        public HaffmanTreeNode(T data, int weight) {
            this.data = data;
            this.weight = weight;
        }
    }

    private static class InnerNode<T> extends HaffmanTreeNode {
        InnerNode<T> parent;
        InnerNode<T> leftChild;
        InnerNode<T> rightChild;

        public InnerNode() {
        }

        public InnerNode(HaffmanTreeNode<T> node) {
            this.data = node.data;
            this.weight = node.weight;
        }
    }

    Comparator<HaffmanTreeNode> comparator = new Comparator<HaffmanTreeNode>() {
        @Override
        public int compare(HaffmanTreeNode o1, HaffmanTreeNode o2) {
            return o1.weight - o2.weight;
        }
    };
}
