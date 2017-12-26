package editor;

import javafx.scene.text.Text;


public class FastLinkedList{

    public class Node {
        public Text val;
        public Node next;
        public Node prev;

        public Node(Text t, Node p, Node n) {
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
    void addChar(Text x) {
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
    void deleteChar() {
        if (size == 1) {
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
            currentNode = sentinel;
            size--;
            currentPos--;
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

    int currentPos() {
        return currentPos;
    }

}
