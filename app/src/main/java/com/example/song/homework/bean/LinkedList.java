package com.example.song.homework.bean;

public class LinkedList<E> {

	public Node<E> first;
	public Node<E> last;
	public int size;

	public void add(E e) {
		linkLast(e);
	}

	public void addFirst(E e) {
		linkFirst(e);
	}

	public void add(int index, E e) {
		// 方法1：
		// linkBefore(index, e);
		// 方法2：
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("size is:" + size + ", but index is" + index);
		} else {

			if (index == size) {
				linkLast(e);
				return;
			}
			linkBefore(e, node(index));
		}
	}

	public void remove(int index) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("size is:" + size + ", but index is" + index);
		} else {
			unLink(node(index));
		}
	}

	public void set(int index , E e){
		if(index < 0 || index > size ){
			throw new IndexOutOfBoundsException("size is:" + size + ", but index is" + index);
		}else{
			Node<E> target = node(index);
			target.item = e;
		}
	}
	
	private void linkLast(E e) {
		Node<E> newNode = new Node<E>(last, e, null);
		if (last != null) {
			last.next = newNode;
			last = newNode;
		} else {
			first = last = newNode;

		}
		size++;
	}

	/***
	 * 在index的位置插入Node 时间复杂度比{@link #linkBefore(Object, Node) 多了n}}
	 * 
	 * @param index
	 * @param e
	 */
	private void linkBefore(int index, E e) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("size is:" + size + ", but index is" + index);
		}
		if (index == 0) {
			linkFirst(e);
			return;
		}
		if (index == size) {
			linkLast(e);
			return;
		}

		Node<E> before = node(index - 1);
		Node<E> after = node(index);
		// 第一步：newNode指向前后
		Node<E> newNode = new Node(before, e, after);

		// 第二步：断开before.next
		before.next = newNode;
		// 第三步：断开after.prev
		after.prev = newNode;
		size++;
	}

	/**
	 * 
	 * 在target之前插入Node
	 * 
	 * @param e
	 * @param target
	 */

	private void linkBefore(E e, Node<E> target) {

		Node<E> prev = target.prev;
		// 第一步：newNode指向前后
		Node<E> newNode = new Node(prev, e, target);
		// 第二步：target指向newNode
		target.prev = newNode;
		if (prev == null) {
			first = newNode;
		} else {
			// 第三步：prev指向newNode
			prev.next = newNode;
		}
		size++;
	}

	private void linkFirst(E e) {
		Node<E> newNode = new Node<E>(null, e, first);
		if (first != null) {
			first.prev = newNode;
			first = newNode;
		} else {
			first = last = newNode;
		}
		size++;
	}

	private void unLink(Node<E> target) {
		Node<E> prev = target.prev;
		Node<E> next = target.next;
		if (prev == null) {
			first = next;
		} else {
			prev.next = target;
			target.prev = null;
		}
		if (next == null) {
			last = prev;

		} else {
			next.prev = prev;
			target.next = null;
		}
		target.item = null;
		size--;

	}

	public Node<E> node(int index) {
		// 从前往后找
		if (index < (size >> 1)) {
			Node<E> result = first;
			for (int i = 0; i < index; i++) {
				result = result.next;
			}
			return result;
		} // 从后往前找
		else {
			Node<E> result = last;
			for (int i = size - 1; i > index; i--) {
				result = result.prev;
			}
			return result;
		}

	}

	public E get(int index) {
		return node(index).item;
	}

	public static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

}
