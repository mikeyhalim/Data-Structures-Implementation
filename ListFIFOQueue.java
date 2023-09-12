package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;
import java.util.NoSuchElementException;
/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private int size;
    private ListNode<E> frontNode;
    private ListNode<E> backNode;
    private class ListNode<E> {
        private ListNode<E> nextNode;
        private E data;
        public ListNode() {
            this(null, null);
        }
        public ListNode(E data) {
            this(data, null);
        }
        public ListNode(E data, ListNode nextNode) {
            this.data = data;
            this.nextNode = nextNode;
        }
    }
    public ListFIFOQueue() {
        this.frontNode = null;
        this.backNode = null;
        this.size = 0;
    }

    @Override
    public void add(E work) {
        ListNode<E> newNode = new ListNode<>(work);
        if(frontNode == null) {
            frontNode = newNode;
        }
        else {
            backNode.nextNode = newNode;
        }
        backNode = newNode;
        size++;
    }

    @Override
    public E peek() {
        if(!this.hasWork()) {
            throw new NoSuchElementException();
        }
        return this.frontNode.data;
    }

    @Override
    public E next() {
        if(!this.hasWork()) {
            throw new NoSuchElementException();
        }
        E val = this.frontNode.data;
        frontNode = frontNode.nextNode;
        size--;
        if(frontNode == null) {
            backNode = null;
        }
        return val;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.frontNode = null;
        this.backNode = null;
        this.size = 0;
    }
}
