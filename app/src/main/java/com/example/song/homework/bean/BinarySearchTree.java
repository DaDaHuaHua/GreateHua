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

    /**
     * 方法一：
     *
     * @param t
     */
    public void delete01(T t) {
        TreeNode<T> node = get(t);
        if (node == null) {
            throw new NoSuchElementException("没有该元素");
        }
        TreeNode<T> parent = node.parent;

        //是叶子节点
        if (node.leftChild == null && node.rightChild == null) {
            if (parent == null) {
                mRoot = null;
            } else {
                //在parent的左边
                if (parent.leftChild == node) {
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                }
            }
        }//只有左子节点
        else if (node.leftChild != null && node.rightChild == null) {
            //根节点
            if (mRoot == null) {
                mRoot = node.leftChild;
                node.leftChild.parent = null;
                node.leftChild = null;
            } else {
                //在parent的左边
                if (parent.leftChild == node) {
                    parent.leftChild = node.leftChild;
                } else {
                    parent.rightChild = node.leftChild;
                }
                node.leftChild.parent = parent;
                node.leftChild = null;
            }
        }//只有右子节点
        else if (node.leftChild == null && node.rightChild != null) {
            if (parent == null) {
                mRoot = node.rightChild;
                node.rightChild.parent = null;
            } else {
                //在parent左边
                if (parent.leftChild == node) {
                    parent.leftChild = node.rightChild;
                } else {
                    parent.rightChild = node.rightChild;
                }
                node.rightChild.parent = parent;
            }
        }//既有左子节点， 也有右子节点
        else {
            //右子节点 没有左子节点
            if (node.rightChild.leftChild == null) {
                if (parent == null) {
                    mRoot = node.rightChild;
                } else {
                    if (parent.rightChild == node) {
                        parent.rightChild = node.rightChild;
                    } else {
                        parent.leftChild = node.rightChild;
                    }
                    node.rightChild.parent = parent;
                }
                node.leftChild.parent = node.rightChild;
                node.rightChild.leftChild = node.leftChild;
            } else {
                TreeNode<T> minNode = getMinNode(node.rightChild);

                minNode.leftChild = node.leftChild;
                node.leftChild.parent = minNode;

                minNode.parent.leftChild = minNode.rightChild;
                if (minNode.rightChild != null) {
                    minNode.rightChild.parent = minNode.parent;
                }

                minNode.rightChild = node.rightChild;
                node.rightChild.parent = minNode;

                if (parent == null) {
                    mRoot = minNode;
                } else {
                    if (parent.rightChild == node) {
                        parent.rightChild = minNode;
                    } else {
                        parent.leftChild = minNode;
                    }
                }
                minNode.parent = parent;
            }
        }
        node.leftChild = null;
        node.rightChild = null;
        node.parent = null;
    }

    /**
     * 方法二
     * 当删除节点既有左子树也有右子树时，先转移是数据，再删除
     */
    public void delete02(T t) {
        TreeNode<T> node = get(t);
        if (node == null) {
            throw new NoSuchElementException("没有该元素");
        }
        TreeNode<T> parent = node.parent;

        //是叶子节点
        if (node.leftChild == null && node.rightChild == null) {
            if (parent == null) {
                mRoot = null;
            } else {
                //在parent的左边
                if (parent.leftChild == node) {
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                }
            }
        }//只有左子节点
        else if (node.leftChild != null && node.rightChild == null) {
            //根节点
            if (mRoot == null) {
                mRoot = node.leftChild;
                node.leftChild.parent = null;
                node.leftChild = null;
            } else {
                //在parent的左边
                if (parent.leftChild == node) {
                    parent.leftChild = node.leftChild;
                } else {
                    parent.rightChild = node.leftChild;
                }
                node.leftChild.parent = parent;
                node.leftChild = null;
            }
        }//只有右子节点 或 既有左子节点也有右子节点
        else if (node.leftChild == null && node.rightChild != null) {
            if (parent == null) {
                mRoot = node.rightChild;
                node.rightChild.parent = null;
            } else {
                //在parent左边
                if (parent.leftChild == node) {
                    parent.leftChild = node.rightChild;
                } else {
                    parent.rightChild = node.rightChild;
                }
                node.rightChild.parent = parent;
            }
        }//既有左子节点， 也有右子节点
        else {
            //右子节点 没有左子节点
            if (node.rightChild.leftChild == null) {
                if (parent == null) {
                    mRoot = node.rightChild;
                } else {
                    if (parent.rightChild == node) {
                        parent.rightChild = node.rightChild;
                    } else {
                        parent.leftChild = node.rightChild;
                    }
                    node.rightChild.parent = parent;
                }
                node.leftChild.parent = node.rightChild;
                node.rightChild.leftChild = node.leftChild;
            } else {
                //1.找出右子树的最小节点
                TreeNode<T> minMode = getMinNode(node.rightChild);
                //2.将最小节点的值设置到删除的节点上
                node.data = minMode.data;
                //3.删除最小节点
                TreeNode<T> minParent = minMode.parent;
                //3.1 最小节点有右子树
                if (minMode.rightChild != null) {
                    minMode.rightChild.parent = minParent;
                    minParent.leftChild = minMode.rightChild;
                    minMode.rightChild = null;
                } else {
                    minParent.leftChild = null;
                }
                minMode.parent = null;
            }
        }
    }

    /**
     * 方法三
     * 优点：1 当删除的节点存在左右子节点时，不用考虑删除的是否为根节点
     * 2.移动数据后再删除最大值，不存在节点的移动，效率更高
     * 当删除节点既有左子树也有右子树时，先转移是数据，再删除
     */
    public void delete03(T t) {
        TreeNode<T> node = get(t);
        if (node == null) {
            throw new NoSuchElementException("没有该元素");
        }
        TreeNode<T> parent = node.parent;

        //是叶子节点
        if (node.leftChild == null && node.rightChild == null) {
            if (parent == null) {
                mRoot = null;
            } else {
                //在parent的左边
                if (parent.leftChild == node) {
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                }
            }
        }//只有左子节点
        else if (node.leftChild != null && node.rightChild == null) {
            //根节点
            if (mRoot == null) {
                mRoot = node.leftChild;
                node.leftChild.parent = null;
                node.leftChild = null;
            } else {
                //在parent的左边
                if (parent.leftChild == node) {
                    parent.leftChild = node.leftChild;
                } else {
                    parent.rightChild = node.leftChild;
                }
                node.leftChild.parent = parent;
                node.leftChild = null;
            }
        }//只有右子节点
        else if (node.leftChild == null && node.rightChild != null) {
            if (parent == null) {
                mRoot = node.rightChild;
                node.rightChild.parent = null;
            } else {
                //在parent左边
                if (parent.leftChild == node) {
                    parent.leftChild = node.rightChild;
                } else {
                    parent.rightChild = node.rightChild;
                }
                node.rightChild.parent = parent;
            }
        }//既有左子节点， 也有右子节点
        else {
            //1.先找左子树的最大值
            TreeNode<T> maxNode = getMaxNode(node.leftChild);
            //2.将该最大值赋给节点
            node.data = maxNode.data;
            //3.删除最大值的节点
            TreeNode<T> maxParent = maxNode.parent;
            //3.1 有左节点
            if (maxNode.leftChild != null) {
                maxNode.leftChild.parent = maxParent;
                maxParent.rightChild = maxNode.leftChild;
                maxNode.leftChild = null;
            } else {//3.2没有左节点（叶子节点）
                maxParent.rightChild = null;
            }
            maxNode.parent = null;
        }
    }


    private TreeNode<T> getMinNode(TreeNode<T> start) {
        if (start == null) {
            return null;
        }
        TreeNode<T> node = start;
        while (node.leftChild != null) {
            node = node.leftChild;
        }
        return node;
    }

    private TreeNode<T> getMaxNode(TreeNode<T> start) {
        if (start == null) {
            return null;
        }
        TreeNode<T> node = start;
        while (node.rightChild != null) {
            node = node.rightChild;
        }
        return node;
    }

    public TreeNode<T> get(T t) {
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
