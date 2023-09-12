package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] arr;
    private int topOfStack;

    public ArrayStack() {
        this.arr = (E[]) new Object[10];
        this.topOfStack = -1;
    }

    @Override
    public void add(E work) {
        if(this.topOfStack == this.arr.length - 1) {
            E[] copy = (E[]) new Object[arr.length * 2];
            for(int i = 0; i < this.arr.length; i++) {
                copy[i] = this.arr[i];
            }
            this.arr = copy;
        }
        this.arr[++this.topOfStack] = work;
    }

    @Override
    public E peek() {
        if(this.topOfStack == -1) {
            throw new NoSuchElementException();
        }
        else {
            return this.arr[this.topOfStack];
        }
    }

    @Override
    public E next() {
        if(this.topOfStack == -1) {
            throw new NoSuchElementException();
        }
        else {
            this.topOfStack--;
            return arr[this.topOfStack + 1];
        }
    }

    @Override
    public int size() {
        return this.topOfStack + 1;
    }

    @Override
    public void clear() {
        this.arr = (E[]) new Object[10];
        this.topOfStack = -1;
    }
}
