import java.util.*;

public class MyStack<E> {

	private Node head;
	private class Node {
		E content;
		Node next;
	}

	public void push (E item) {
		Node prevhead = head;
		head = new Node();
		head.content = item;
		head.next = prevhead;
	}

	public E pop() {
		if (head==null) {
			throw new EmptyStackException();
		}
		E content = head.content;
		head = head.next;
		return content;
	}

	public E peek() {
		if (head==null) {
			throw new EmptyStackException();
		}
		else {
			E content = head.content;
		    return content;
	    }
	}

	public boolean empty() {
		if (head==null) {
			return true;
		}
		else {
			return false;
		}
	}
}