package connection.cache;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

/*
 * Class type T MUST implement equals() method
 */
public class Cache<T> {

	public static final int MAX_ITEMS_LIST = 10;
	private Deque<T> elements;
	private int maxSize;

	public Cache() {
		maxSize = MAX_ITEMS_LIST;
		elements = new ArrayDeque<T>(maxSize);
	}

	public void changeSize(int newSize) {
		Collection<T> aux = elements;
		maxSize = newSize;
		elements = new ArrayDeque<T>(maxSize);
		addAll(aux);
	}

	public void addAll(Collection<T> elemnts) {
		for (T e : elemnts) {
			remove(e);
			add(e);
		}
	}

	public void add(T element) {
		remove(element);
		if (elements.size() >= maxSize) {
			elements.poll();
		}
		elements.addLast(element);
	}

	public boolean contains(T element) {
		return elements.contains(element);
	}

	public T get(T element) {
		Iterator<T> it = elements.iterator();
		while (it.hasNext()) {
			T current = it.next();
			if (current.equals(element)) {
				elements.remove(current);
				elements.addLast(current);
				return current;
			}
		}
		return null;
	}

	public void remove(T element) {
		if (contains(element)) {
			elements.remove(element);
		}
	}

}