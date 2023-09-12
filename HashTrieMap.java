package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
        this.size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V returnVal;
        HashTrieNode curr = (HashTrieNode) this.root;
        if(curr == null) {
            curr = new HashTrieNode();
            this.root = curr;
        }

        for(A bit:key) {
            if(!curr.pointers.containsKey(bit)) {
                curr.pointers.put(bit, new HashTrieNode());
            }
            curr = curr.pointers.get(bit);
        }
        returnVal = curr.value;
        curr.value = value;
        if(returnVal == null) {
            this.size++;
        }
        return returnVal;
    }

    @Override
    public V find(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode curr = (HashTrieNode) this.root;
        for(A bit:key) {
            curr = curr.pointers.get(bit);
            if(curr == null) {
                return null;
            }
        }
        return curr.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode curr = (HashTrieNode) this.root;
        for(A bit: key) {
            curr = curr.pointers.get(bit);
            if(curr == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        HashTrieNode curr = (HashTrieNode) this.root;
        HashTrieNode lastDelete = curr;
        A lastDeleteBit = null;
        Iterator<A> keyIterator = key.iterator();
        while(keyIterator.hasNext()) {
            A part = keyIterator.next();
            if(curr == null) {
                return;
            }
            if(curr.value != null || curr.pointers.size() > 1) {
                lastDelete = curr;
                lastDeleteBit = part;
            }
            if(!curr.pointers.isEmpty()) {
                curr = curr.pointers.get(part);
            }
            else {
                return;
            }
        }
        if(curr != null && curr.value != null) {
            if(!curr.pointers.isEmpty()) {
                curr.value = null;
            }
            else if (lastDeleteBit != null) {
                lastDelete.pointers.remove(lastDeleteBit);
            }
            else {
                this.root = new HashTrieNode();
            }
            this.size--;
        }
    }

    @Override
    public void clear() {
        this.size = 0;
        this.root = new HashTrieNode();
    }
}
