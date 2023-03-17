import java.util.UUID;

class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String msg) {
        super(msg);
    }
}

public class TransactionLinkedList implements TransactionList {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private Transaction data;
        private Node next;
        private Node prev;

        public Node(Transaction elm) {
            data = elm;
            next = null;
            prev = null;
        }
    }
    
    public TransactionLinkedList() {}

    @Override
    public void addTransaction(Transaction transaction) {
        Node elm = new Node(transaction);
        if (head == null) {
            head = elm;
            tail = elm;
        } else {
            tail.next = elm;
            elm.prev = tail;
            tail = elm;
        }
        ++size;
    }

    @Override
    public void removeTransactionById(UUID id) {
        delete(findNode(id));
        //throw new UserNotFoundExpection("Transaction with id " + id + " not found");
    }

    private Node findNode(UUID id) {
        Node iter = head;
        for (; iter != null; iter = iter.next) {
            if (iter.data.getIdentifier().equals(id)) {
                break;
            }
        }
        return iter;
    }

    private void delete(Node node) {
        if (head == node) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (tail == node) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        --size;
    }

    public void print() {
        for (Node iter = head; iter != null; iter = iter.next) {
            iter.data.print();
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public Transaction[] toArray() {
        if (size == 0)
            return null;
        Transaction[] result = new Transaction[size];
        int i = 0;
        for (Node iter = head; iter != null; iter = iter.next) {
            result[i++] = iter.data;
        }
        return result;
    }
}
