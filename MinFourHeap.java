package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;
import java.util.NoSuchElementException;
import java.util.Comparator;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {

    private static final int INITIAL_CAPACITY = 10;

    private E[] data;
    private int size;
    private Comparator<E> comparator;

    public MinFourHeap(Comparator<E> comparator) {
        this.data = (E[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.comparator = comparator;
    }

    @Override
    public boolean hasWork() {
        return size > 0;
    }

    @Override
    public void add(E work) {
        if(size == data.length) {
            resizeDataArray(data.length * 2);
        }
        int hole = percolateUp(size, work);
        data[hole] = work;
        size++;
    }

    @Override
    public E peek() {
        if(hasWork()) {
            return data[0];
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public E next() {
        if(hasWork()) {
            E result = data[0];
            size--;
            if(size > 0) {
                E last = data[size];
                data[0] = last;
                percolateDown(0, last);
            }
            return result;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        data = (E[]) new Object[INITIAL_CAPACITY];
    }

    private int percolateUp(int hole, E value) {
        while(hole > 0) {
            int parent = (hole - 1) / 4;
            E parentValue = data[parent];
            if(comparator.compare(value, parentValue) >= 0) {
                break;
            }
            data[hole] = parentValue;
            hole = parent;
        }
        return hole;
    }

    private void percolateDown(int hole, E value) {
        while(hole * 4 + 1 < size) {
            int child = hole * 4 + 1;
            int lastChild = Math.min(child + 3, size - 1);
            for(int i = child + 1; i <= lastChild; i++) {
                if (comparator.compare(data[i], data[child]) < 0) {
                    child = i;
                }
            }
            if(comparator.compare(value, data[child]) <= 0) {
                break;
            }
            data[hole] = data[child];
            hole = child;
        }
        data[hole] = value;
    }

    private void resizeDataArray(int newSize) {
        E[] newData = (E[]) new Object[newSize];
        for(int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

}


