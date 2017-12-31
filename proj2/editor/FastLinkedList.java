package editor;

import java.util.LinkedList;
import java.util.Iterator;
import javafx.scene.Node;

public class FastLinkedList<Item> extends LinkedList<Item> implements Iterable<Item> {

    public class Node {
        public Item val;
        public Node next;
        public Node prev;

        public Node(Item t, Node p, Node n) {
            val = t;
            prev = p;
            next = n;
        }
    }

    private Node sentinel;
    private int currentPos;
    private Node currentNode;
    private int size;

    public FastLinkedList() {
        sentinel = new Node(null, null, null);
        currentNode = sentinel;
        currentPos = 0;
        size = 0;
    }

    //This adds the given character to the linked list after the currentNode.
    public void addChar(Item x) {
        // when currentPos = 0
        if (currentNode == sentinel) {
            sentinel.next = new Node(x, sentinel, sentinel);
            currentNode = sentinel.next;
            currentPos = 0;
            size++;
        } else {
            Node next = currentNode.next;
            currentNode.next = new Node(x, currentNode, next);
            next.prev = currentNode.next;
            currentNode = currentNode.next;
            currentPos++;
            size++;
        }
    }

    //Deletes the currentNode
    public void deleteChar() {
        if (size <= 1) {
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
            currentNode = sentinel;
            size = 0;
            currentPos = 0;
        } else {
            Node prevNode = currentNode.prev;
            Node nextNode = currentNode.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            currentNode = prevNode;
            size--;
            currentPos--;
        }
    }

    public int currentPos() {
        return currentPos;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public class fastIterator implements Iterator<Item> {
        private Node index;

        public fastIterator() {
            index = sentinel;
        }

        public boolean hasNext() {
            return (index.next != sentinel);
        }

        public Item next() {
            Node current = index.next;
            index = index.next;
            return current.val;
        }
    }

    public Iterator<Item> iterator() {
        return new fastIterator();
    }

    /** Implementing get() method for debugging purposes. */
    public Item get(int index) {
        if (index == 0) {
            return sentinel.next.val;
        }

        Node currentNode = sentinel.next.next;
        while(index > 1 && currentNode.next != null) {
            index -= 1;
            currentNode = currentNode.next;
        }

        return currentNode.val;
    }

    /** Implementing print() method for debugging purposes. */
    public void printList() {
        Node p = sentinel.next;
        int count = 0;

        while(p != null && count < size) {
            System.out.print(p.val + " ");
            p = p.next;
            count++;
        }
        System.out.println();
    }

}
