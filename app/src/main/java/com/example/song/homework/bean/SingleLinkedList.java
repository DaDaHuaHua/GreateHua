package com.example.song.homework.bean;

public class SingleLinkedList<E> {

	public Node<E> first;
	public int size;

	public E get(int index) {
		if (!checkIndex(index)) {
			throwIndexOutOfBound(index);
		}
		return node(index).item;

	}

	public void add(E e) {
		linkLast(e);
	}

	public void addFirst(E e) {
		linkFirst(e);
	}

	public void add(int index, E e) {
		if (!checkIndex(index)) {
			throwIndexOutOfBound(index);
		}
		if (index == 0) {
			linkFirst(e);
		} else {
			Node<E> target = node(index - 1);
			linkAfter(e, target);
		}
	}

	public void remove(int index) {
		if (!checkIndex(index)) {
			throwIndexOutOfBound(index);
		}
		if (index == 0) {
			unLinkFirst();
		} else {
			Node<E> node = node(index - 1);
			unLink(node);
		}
	}

	public void set(int index, E e) {
		if (!checkIndex(index)) {
			throwIndexOutOfBound(index);
		} else {
			node(index).item = e;
		}
	}

	public void revert(){
		first = revert(first);
	}

	private Node<E> revert(Node<E> head){
		if (head == null || head.next == null) {
	        // 到达尾结点
	        return head;
	    }
	    // 一直入栈
	    Node<E> revertHead = revert(head.next);
	    // 出栈并赋值nextNode对象
	    head.next.next = head;
	    head.next = null;
	    // 返回尾结点（逆置后的头结点）
	    return revertHead;

	}

	private void linkLast(E e) {
		Node<E> node = new Node<>(e, null);
		if (first == null) {
			first = node;
		} else {
			node(size - 1).next = node;
		}
		size++;
	}

	private void linkFirst(E e) {
		Node<E> node = new Node<E>(e, first);
		if (first == null) {
			first = node;
		} else {
			node.next = first;
			first = node;
		}
		size++;
	}

	/**
	 * 在target之后连上node
	 * 
	 * @param e
	 * @param target
	 */
	private void linkAfter(E e, Node<E> target) {
		if (target == null) {
			return;
		} else {
			Node<E> after = target.next;
			Node<E> node = new Node<E>(e, after);
			target.next = node;
			size++;
		}
	}

	/**
	 * 移除before之后的node
	 * 
	 * @param before
	 */
	private void unLink(Node<E> before) {
		if (before == null) {
			return;
		} else {
			Node<E> target = before.next;
			if (target == null) {
				return;
			} else {
				before.next = target.next;
				size--;
			}
		}
	}

	private void unLinkFirst() {
		if (first == null) {
			return;
		} else {
			first.next = null;
			first.item = null;
			first = null;
			size--;
		}

	}

	private void throwIndexOutOfBound(int index) {
		throw new IndexOutOfBoundsException(getErrorMsg(index));
	}

	private boolean checkIndex(int index) {
		if (index >= 0 && index <= size) {
			return true;
		} else {
			return false;
		}
	}

	private String getErrorMsg(int index) {
		return "size is " + size + " ,but index is " + index;
	}

	public Node<E> node(int index) {
		Node<E> node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
	}

	public static class Node<E> {
		public E item;
		public Node<E> next;

		public Node(E item, Node<E> next) {
			this.item = item;
			this.next = next;
		}
	}
	
	

}
