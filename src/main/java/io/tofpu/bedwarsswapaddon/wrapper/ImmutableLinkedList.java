package io.tofpu.bedwarsswapaddon.wrapper;

import java.util.Collection;
import java.util.LinkedList;

public class ImmutableLinkedList<T> extends LinkedList<T> {
    public static <T> ImmutableLinkedList<T> of(final LinkedList<T> list) {
        return new ImmutableLinkedList<>(list);
    }

    public ImmutableLinkedList(final LinkedList<T> list) {
        super.addAll(size(), list);
    }

    @Override
    public boolean add(final T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final int index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(final int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(final Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(final int index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addFirst(final T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addLast(final T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(final T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T removeFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T removeLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T poll() {
        throw new UnsupportedOperationException();
    }
}
