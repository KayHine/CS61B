package editorTester;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import editor.FastLinkedList;

import javax.xml.soap.Text;

public class FastLinkedListTest {

    public static void main(String[] args) {

        FastLinkedList<Integer> newList = new FastLinkedList<>();

        newList.addChar(10);
        newList.addChar(1);
        newList.addChar(15);
        System.out.println("Current position: " + newList.currentPos());
        newList.printList();                // 10 1 15
        System.out.println(newList.get(0)); // 10
        System.out.println("Current Node: " + newList.getCurrentNode().val);   // 15
        newList.deleteChar();
        System.out.println("Current Node: " + newList.getCurrentNode().val);   // 1
        newList.deleteChar();
        newList.printList();                // 10

//        LinkedList<Integer> nList = new LinkedList<>();
//        nList.add(10);
//        nList.add(1);
//        nList.add(15);
//
//        System.out.println(nList);

    }
}
