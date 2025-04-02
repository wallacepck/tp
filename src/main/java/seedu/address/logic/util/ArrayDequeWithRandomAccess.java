package seedu.address.logic.util;

import java.util.ArrayDeque;
import java.util.Objects;

/**
 * A wrapper over {@link ArrayDeque} with {@link #get} to access elements by index.
 * Uses caching to reduce unnecessary memory allocations.
 *
 * @param <E> The type of elements
 */
public class ArrayDequeWithRandomAccess<E> {
    private final ArrayDeque<E> deque;
    private E[] cache;

    /**
     * Constructs a new {@code ArrayDequeList}.
     */
    public ArrayDequeWithRandomAccess() {
        deque = new ArrayDeque<>();
    }

    /**
     * Wrapper of {@link ArrayDeque#addFirst(Object)}.
     */
    public void addFirst(E e) {
        deque.addFirst(e);
        refreshCache();
    }

    /**
     * Wrapper of {@link ArrayDeque#removeLast()}.
     */
    public void removeLast() {
        deque.removeLast();
        refreshCache();
    }

    /**
     * Rebuilds the {@link #cache} based on the wrapped {@link #deque}.
     */
    @SuppressWarnings("unchecked")
    private void refreshCache() {
        if (cache == null) {
            cache = (E[]) deque.toArray();
        } else {
            cache = deque.toArray(cache);
        }
    }

    /**
     * Returns the element at the specified position in the cache, which represents the wrapped deque.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= deque.size())
     */
    public E get(int index) {
        Objects.checkIndex(index, deque.size());
        return cache[index];
    }

    /**
     * Wrapper of {@link ArrayDeque#isEmpty()}.
     */
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    /**
     * Wrapper of {@link ArrayDeque#size()}.
     */
    public int size() {
        return deque.size();
    }

    /**
     * Wrapper of {@link ArrayDeque#toString()}.
     */
    @Override
    public String toString() {
        return deque.toString();
    }
}
