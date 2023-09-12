package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;
import java.util.NoSuchElementException;


/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;

    public MinFourHeapComparable() {
        this.size = 0;
        this.capacity = 10;
        this.data = (E[]) new Comparable[capacity];
    }

    @Override
    public boolean hasWork() {
        return(size > 0);
    }

    @Override
    public void add(E work) {
        if(size == capacity) {
            E[] newData = (E[]) new Comparable[capacity*2];
            for(int i = 0; i < size; i++) {
                newData[i] = data[i];
            }
            this.data = newData;
            this.capacity *= 2;
        }
        data[size] = work;
        this.size++;
        int currentIndex = size - 1;
        while(currentIndex > 0 && data[(currentIndex - 1) / 4].compareTo(data[currentIndex]) > 0) {
            E temp = data[currentIndex];
            data[currentIndex] = data[(currentIndex - 1) / 4];
            data[(currentIndex - 1) / 4] = temp;
            currentIndex = (currentIndex - 1) / 4;
        }
    }

    @Override
    public E peek() {
        if(!hasWork()) {
            throw new NoSuchElementException();
        }
        return this.data[0];
    }

    @Override
    public E next() {
        if(!hasWork()) throw new NoSuchElementException();
        E item = this.data[0];
        this.data[0] = data[size - 1];
        this.data[size - 1] = null;
        this.size--;
        int index = 0;
        while((4 * index) + 1 < size) {
            int smallestIndex = (4 * index) + 1;
            if((4 * index) + 2 < size && data[(4 * index) + 2].compareTo(data[smallestIndex]) < 0) {
                smallestIndex = (4 * index) + 2;
            }
            if((4 * index) + 3 < size && data[(4 * index) + 3].compareTo(data[smallestIndex]) < 0) {
                smallestIndex = (4 * index) + 3;
            }
            if((4 * index) + 4 < size && data[(4 * index) + 4].compareTo(data[smallestIndex]) < 0) {
                smallestIndex = (4 * index) + 4;
            }
            if(data[index].compareTo(data[smallestIndex]) >= 0) {
                E temp = data[index];
                data[index] = data[smallestIndex];
                data[smallestIndex] = temp;
                index = smallestIndex;
            }
            else {
                break;
            }
        }
        return item;
    }
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
        E[] aux = (E[]) new Comparable[capacity];
        this.data = aux;
    }
}
