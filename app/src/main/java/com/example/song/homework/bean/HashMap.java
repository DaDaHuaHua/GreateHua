package com.example.song.homework.bean;


public class HashMap<K, V> {

	/**
	 * 默认散列表容量为10
	 */
	private static final int DEFAULT_INITIAL_CAPACITY = 8;
	/**
	 * 默认装填因子为0.75
	 */
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	/**
	 * 散列表
	 */
	private Entry<K, V>[] dataEntrys = null;
	private int size;

	private float loadFactory;
	/**
	 * 阈值 = loadFactory * capacity
	 * 在添加元素时，当超过了阈值，散列表扩容两倍，然后重新给阈值赋值
	 */
	private int threshold;

	public HashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	public HashMap(int capacity) {
		this(capacity, DEFAULT_LOAD_FACTOR);
	}

	public HashMap(int capacity, float loadFactory) {
		this.loadFactory = loadFactory;
		this.threshold = (int) (capacity * loadFactory);
		if (dataEntrys == null) {
			dataEntrys = new Entry[capacity];
		}
	}

	public void put(K key, V value) {
		if (key == null) {
			putNullKey(value);
			return;
		}
		int hash = getHashCode(key);
		int index = getIndex(hash, dataEntrys.length);
		//如果存在key，直接修改value
		for (Entry entry = dataEntrys[index]; entry != null; entry = entry.next) {
			if (entry.hashCode == hash && (key == entry.key || key.equals(entry.key))) {
				entry.value = value;
				return;
			}
		}
		//不存在key就添加Entry
		putEntry(hash, key, value, index);
	}

	public V get(K key) {
		if (key == null) {
			return getNullKey();
		}
		int hash = getHashCode(key);
		int index = getIndex(hash, dataEntrys.length);
		Entry<K, V> entry = getEntry(hash, key, index);
		return entry == null ? null : entry.value;
	}

	/**
	 * 根据key修改value
	 * @param key
	 * @param value
	 */
	public void set(K key, V value) {
		int hash = getHashCode(key);
		int index = getIndex(hash, dataEntrys.length);
		Entry<K, V> entry = getEntry(hash, key, index);
		if (entry != null) {
			entry.value = value;
		} else {
//			throw new NullPointerException("没有此Key!!!");
		}
	}

	/**
	 * 方法一
	 * @param key
	 * @return
	 */
//	public void remove(K key) {
//		int hash = getHashCode(key);
//		int index = getIndex(hash, dataEntrys.length);
//		Entry resultEntry = null;
//		int resultTimes = 0;
//		Entry<K, V> nextEntry = null;
//		for (Entry<K, V> entry = dataEntrys[index]; entry != null; entry = entry.next) {
//			if (entry.hashCode == hash && (entry.key == key || entry.key.equals(key))) {
//				resultEntry = entry;
//				nextEntry = resultEntry.next;
//				// 当前Entry在dataEntry上
//				if (resultTimes == 0) {
//					dataEntrys[index] = resultEntry.next;
//				} else {
//					// 向后找resultRTimes-1的位置
//					Entry<K, V> preEntry = null;
//					do {
//						preEntry = dataEntrys[index].next;
//						resultTimes -= 1;
//					} while (resultTimes == 1);
//					preEntry.next = nextEntry;
//				}
//				entry.next = null;
//				size--;
//				return;
//			}
//			resultTimes++;
//		}
//	}


	/**
	 * 方法二
	 * @param key
	 * @return
	 */
	public V remove(K key) {
		int hash = getHashCode(key);
		int index = getIndex(hash, dataEntrys.length);
		Entry<K, V> entryStart = dataEntrys[index];
		//不存在该key
		if (entryStart == null) {
			return null;
		}
		//key处于数组中
		if(entryStart.hashCode == hash && (entryStart.key == key || entryStart.key.equals(key))){
			dataEntrys[index] = entryStart.next;
			entryStart.next = null;
			size --;
			return entryStart.value;
		}
		//key处于数组后的链表中
		for (Entry<K, V> entry = entryStart; entry != null; entry = entry.next) {
			Entry<K, V> entryNext = entry.next;
			if (entryNext != null && entryNext.hashCode == hash && (entryNext.key == key ||entryNext.key.equals(key))) {
				entry.next = entryNext.next;
				entryNext.next = null;
				size --;
				return entryNext.value;
			}
		}
		return null;
	}

	public int size() {
		return size;
	}

	/**
	 * 翻倍增长，重新赋值
	 */
	private void grow() {
		// 新数组长度为原来的两倍
		int newCapacity = dataEntrys.length << 2;
		Entry<K, V>[] newArray = new Entry[newCapacity];
		// 复制原数组，重新计算hash,填充到新数组
		copyToNewArray(newArray);
		dataEntrys = newArray;
		// 阈值重新赋值
		threshold = (int) (newCapacity * loadFactory);
	}

	private void copyToNewArray(Entry<K, V>[] newArray) {
		int newCapacity = newArray.length;
		for (Entry<K, V> e : dataEntrys) {
			while (null != e) {
				Entry<K, V> next = e.next;
				e.hashCode = null == e.key ? 0 : getHashCode(e.key);
				int i = getIndex(e.hashCode, newCapacity);
				e.next = newArray[i];
				newArray[i] = e;
				e = next;
			}
		}
	}

	private void putNullKey(V value) {
		for (Entry<K, V> entry = dataEntrys[0]; entry != null; entry = entry.next) {
			// 找到了nullKey ,替换内容
			if (entry.key == null) {
				entry.value = value;
				return;
			}
		}
		// 没找到就创建nullKey的entry
		putEntry(0, null, value, 0);
	}

	private V getNullKey() {
		for (Entry<K, V> entry = dataEntrys[0]; entry != null; entry = entry.next) {
			if (entry.key == null) {
				return entry.value;
			}
		}
		return null;
	}

	private Entry<K, V> getEntry(int hashCode, K key, int index) {
		for (Entry<K, V> entry = dataEntrys[index]; entry != null; entry = entry.next) {
			if (entry.hashCode == hashCode && (entry.key == key || entry.key.equals(key))) {
				return entry;
			}
		}
		return null;
	}

	private void putEntry(int hashCode, K key, V value, int index) {
		// 如果超过了阈值，翻倍增加
		if (size >= threshold) {
			grow();
			//扩容后需要重新计算hashCode
			int newHash = getHashCode(key);
			int newIndex = getIndex(hashCode,dataEntrys.length);
			putInto(newHash,key,value,newIndex);
		}else{
			putInto(hashCode,key,value,index);
		}
	}


	private void putInto(int hashCode, K key, V value, int index){
		Entry<K, V> newEntry = new Entry<>();
		Entry<K, V> oldEntry = dataEntrys[index];
		newEntry.hashCode = hashCode;
		newEntry.key = key;
		newEntry.value = value;
		newEntry.next = oldEntry;
		dataEntrys[index] = newEntry;
		size++;
	}



	/**
	 * 根据hash值计算下标
	 * index=[0,size-1]
	 * @param hash
	 * @param size
	 * @return
	 */
	private int getIndex(int hash, int size) {
		return hash & size - 1;
	}

	static class Entry<K, V> {
		int hashCode;
		K key;
		V value;
		Entry<K, V> next;
	}

	/**
	 * 直接使用Object中的hashCode
	 * @param key
	 * @return
	 */
	private int getHashCode(K key) {
		return key.hashCode();
	}
}
