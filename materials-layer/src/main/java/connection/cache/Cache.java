package connection.cache;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/*
 * Class type T MUST implement equals() method
 */
public class Cache<T> {

	public static final int MAX_ITEMS_LIST = 10;
	private Deque<T> elements;

	public Cache() {
		elements = new ArrayDeque<T>(MAX_ITEMS_LIST);
	}

	public void add(T element) {
        if (elements.size() >= MAX_ITEMS_LIST) {                 
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

}
