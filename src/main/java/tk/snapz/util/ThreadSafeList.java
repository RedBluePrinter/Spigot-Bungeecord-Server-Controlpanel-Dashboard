package tk.snapz.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ThreadSafeList<E> extends AbstractList<E> {

    private List<E> elements = null;

    public ThreadSafeList() {
        elements = new ArrayList<E>();
    }

    private boolean doWait = false;

    @Override
    public int size() {
        while (doWait);
        doWait = true;
        int size = 0;
        try {
            size = elements.size();
        } catch (Exception exception) {}
        doWait = false;
        return size;
    }

    @Override
    public E get(int index) {
        while (doWait);
        doWait = true;
        E element = null;
        try {
            element = elements.get(index);
        } catch (Exception exception) {}
        doWait = false;
        return element;
    }

    @Override
    public boolean add(E e) {
        boolean cache = true;
        while (doWait);
        doWait = true;
        try {
            elements.add(e);
        } catch (Exception exception) {
            cache = false;
        }
        doWait = false;
        return cache;
    }

    @Override
    public E set(int index, E element) {
        while (doWait);
        E e = null;
        doWait = true;
        try {
            elements.set(index, element);
        } catch (Exception exception) {
            e = null;
        }
        doWait = false;
        return element;
    }

    @Override
    public void add(int index, E element) {
        while (doWait);
        doWait = true;
        try {
            elements.add(index, element);
        } catch (Exception exception) {}
        doWait = false;
    }

    @Override
    public E remove(int index) {
        while (doWait);
        doWait = true;
        E element = null;
        try {
            element = elements.remove(index);
        } catch (Exception exception) {}
        doWait = false;
        return element;
    }
}
