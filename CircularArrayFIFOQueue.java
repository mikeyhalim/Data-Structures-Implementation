package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    public E[] arr;
    private int size;
    private int pointer;
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.arr = (E[]) new Comparable[capacity];
        this.size = 0;
        this.pointer = 0;
    }

    @Override
    public void add(E work) {
        if(this.isFull()) {
            throw new IllegalStateException();
        }
        this.arr[(this.size + this.pointer) % this.arr.length] = work;
        this.size++;
    }

    @Override
    public E peek() {
        return this.peek(0);
    }

    @Override
    public E peek(int i) {
        if(this.size <= 0) {
            throw new NoSuchElementException();
        }
        if(i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        return this.arr[(this.pointer + i) % this.arr.length];
    }

    @Override
    public E next() {
        if(this.size <= 0) {
            throw new NoSuchElementException();
        }
        E val = this.arr[this.pointer];
        this.pointer = (this.pointer + 1) % this.arr.length;
        this.size--;
        return val;
    }

    @Override
    public void update(int i, E value) {
        if(this.size <= 0) {
            throw new NoSuchElementException();
        }
        if(i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.arr[(this.pointer + i) % this.arr.length] = value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.pointer = 0;
        this.size = 0;
        this.arr = (E[]) new Comparable[this.capacity()];
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        int commonSize = Math.min(this.size(), other.size());
        for(int i = 0; i < commonSize; i++) {
            int comparisonResult = this.peek(i).compareTo(other.peek(i));
            if(comparisonResult != 0) {
                return comparisonResult;
            }
        }
        return Integer.compare(this.size(), other.size());
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            if(this.size() != other.size()) {
                return false;
            } else {
                for(int i = 0; i < this.size(); i++) {
                    if(this.peek(i) != other.peek(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int result = 1;
        for (Object element : this.arr) {
            result = prime * result + (element == null ? 0 : element.hashCode());
        }
        return result;
    }
}
