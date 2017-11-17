package com.example.song.homework.bean;

import java.util.NoSuchElementException;

/**
 * <p> Created by 宋华 on 2017/11/17.
 */
public class BinarySearchTreeTeacher {

    public TreeNode root;

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode put(int data) {
        if (root == null) {
            TreeNode node = new TreeNode(data);
            root = node;
            return node;
        }

        TreeNode parent = null;
        TreeNode node = root;
        for(; node != null;) {
            parent = node;
            if (data < node.data) {
                node = node.leftChild;
            } else if(data > node.data) {
                node = node.rightChild;
            } else {
                return node;
            }
        }
        TreeNode newNode = new TreeNode(data);
        if (data < parent.data) {
            parent.leftChild = newNode;
        } else {
            parent.rightChild = newNode;
        }

        // �п�
        newNode.parent = parent;
        return newNode;

    }

    //
    public void midOrderTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        midOrderTraverse(root.leftChild);
        System.out.print(root.data + "  ");
        midOrderTraverse(root.rightChild);
    }


    //	public void preOrderTraverse(TreeNode root) {
//		if (root == null) {
//			return;
//		}
//		System.out.println("pre Order: " + root.data);
//		preOrderTraverse(root.leftChild);
//		preOrderTraverse(root.rightChild);
//	}
//
    public TreeNode searchNode (int data) {
        if (root == null) {
            return null;
        }
        TreeNode node = root;
        while (node != null) {
            if (node.data == data) {
                return node;
            } else if (node.data < data) {
                node = node.rightChild;
            } else if(node.data > data) {
                node = node.leftChild;
            }
        }

        return null;
    }

    public class TreeNode{
        int data;
        TreeNode leftChild;
        TreeNode rightChild;
        TreeNode parent;
        public TreeNode(int data) {
            super();
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
            this.parent = null;
        }
        public int getData() {
            return data;
        }
        public void setData(int data) {
            this.data = data;
        }
        public TreeNode getLeftChild() {
            return leftChild;
        }
        public void setLeftChild(TreeNode leftChild) {
            this.leftChild = leftChild;
        }
        public TreeNode getRightChild() {
            return rightChild;
        }
        public void setRightChild(TreeNode rightChild) {
            this.rightChild = rightChild;
        }
        public TreeNode getParent() {
            return parent;
        }
        public void setParent(TreeNode parent) {
            this.parent = parent;
        }
    }

    /**
     * ɾ��һ���ڵ�
     * @param node
     */
    private void delNode(TreeNode node){
        if(node == null) {
            throw new NoSuchElementException();
        } else {
            TreeNode parent = node.parent;
            //1��û�к���
            if (node.leftChild ==null && node.rightChild ==null) {
                if (parent == null) {
                    root = null;
                } else if (parent.rightChild == node) {
                    parent.rightChild = null;
                } else if(parent.leftChild == node) {
                    parent.leftChild = null;
                }
                node.parent = null;
            } else if (node.leftChild != null && node.rightChild == null) {
                //2��ֻ������
                if (parent == null) {
                    node.parent = null;
                    node.leftChild.parent = null;
                    root = node.leftChild;
                } else {
                    if (parent.leftChild == node) {
                        parent.leftChild = node.leftChild;
                    } else {
                        parent.rightChild = node.leftChild;
                    }
                    node.parent = null;
                }
            } else if (node.leftChild == null && node.rightChild != null) {//3��ֻ���Һ���
                if (parent == null) {
                    node.parent = null;
                    node.rightChild.parent = null;
                    root = node.rightChild;
                } else {
                    if (parent.leftChild == node) {
                        parent.leftChild = node.rightChild;
                    } else {
                        parent.rightChild = node.rightChild;
                    }
                    node.parent = null;
                }
            } else {//4����������������
                //1��ɾ���ڵ�����������������Ƿ�Ϊ�գ����Ϊ�գ����Ҫɾ���ڵ����������Ϊɾ�������������������
                if (node.rightChild.leftChild == null) {
                    node.rightChild.leftChild = node.leftChild;
                    if (parent == null) {
                        root = node.rightChild;
                    } else {
                        if (parent.leftChild == node) {
                            parent.leftChild = node.rightChild;
                        } else {
                            parent.rightChild = node.rightChild;
                        }
                    }
                    node.parent = null;
                } else {
                    // ��������
                    TreeNode leftNode = getMinLeftTreeNode(node.rightChild);
                    // 001
                    leftNode.leftChild = node.leftChild;
                    //002
                    TreeNode leftNodeP = leftNode.parent;
                    leftNodeP.leftChild = leftNode.rightChild;

                    //003
                    leftNode.rightChild = node.rightChild;
                    //004
                    if (parent == null) {
                        root = leftNode;
                    } else {
                        if (parent.leftChild == node) {
                            parent.leftChild = leftNode;
                        } else {
                            parent.rightChild = leftNode;
                        }
                    }
                }
            }
        }
    }

    private TreeNode getMinLeftTreeNode(TreeNode node) {
        TreeNode curRoot = null;
        if (node == null) {
            return null;
        } else {
            curRoot = node;
            while(curRoot.leftChild != null) {
                curRoot = curRoot.leftChild;
            }
        }
        return curRoot;
    }

//    public static void main(String[] args) {
//        int[] arrays = {12, 3 ,23, 5 ,8, 1, 19};
//        SearchBinaryTree tree = new SearchBinaryTree();
//        for(int i: arrays) {
//            tree.put(i);
//        }·  ·
//        tree.midOrderTraverse(tree.root);
//        System.out.println();
//        TreeNode node = tree.searchNode(1);
////		System.out.println(node !=null ? node.data: null);
//        tree.delNode(node);
//        tree.midOrderTraverse(tree.root);
//    }
}
