package connection.cache;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * Class type T MUST implement equals() method
 */
public class Cache<T> {

	public static int MAX_ITEMS_LIST = 10;
	private Deque<T> elements;

	public Cache() {
		elements = new ArrayDeque<T>(MAX_ITEMS_LIST);
	}

	public void add(T element) {
		if (elements.offerLast(element)) {
			elements.remove();
			elements.offerLast(element);
		}
	}

	public boolean contains(T element) {
		return elements.contains(element);
	}

	public T get(T element) {
		if (contains(element)) {
			for (T cached : elements) {
				if (cached.equals(element)) {
					return cached;
				}
			}
		}
		return null;
	}

}
