import java.util.*;

	class nodeLL<E>{
		E data;
		nodeLL<E> next;
		nodeLL<E> prev;
		
		nodeLL(E Data,nodeLL<E> Next,nodeLL<E> Prev){
			data = Data;
			next = Next;
			prev = Prev;
		}
		
		nodeLL(){
			this(null,null,null);
		}
	}

	public class DoubleLL<E>{
		
		nodeLL<E> head;
		nodeLL<E> rear;
		
		DoubleLL(nodeLL<E> head,nodeLL<E> rear){
			this.head = head;
			this.rear = rear;
		}
		
		DoubleLL(){
			this(null,null);
		}
		
		nodeLL<E> insert(E element){
			nodeLL<E> ptr = new nodeLL<E>();
			ptr.data = element;
			
			if(head == null){
					head = rear = ptr;
			}
			else{
				ptr.prev = rear;
				rear.next = ptr;
				rear = ptr;
			}
			return ptr;
		}
		
		E delete(nodeLL<E> todel){
			if(todel == null){
				return null;
			}
			else if(head == rear){
				E ret = head.data;
				head = rear = null;
				return ret;
			}
			else{
				E ret = todel.data;
				if(todel == head){
					head = head.next;
					head.prev = null;
					todel.next = null;
				}
				else if(todel == rear){
					rear = rear.prev;
					rear.next = null;
					todel.prev = null;
				}
				else{
					todel.next.prev = todel.prev;
					todel.prev.next = todel.next;
				}
				return ret;
			}
		}
		
		List<E> traverse(){
			nodeLL<E> ptr = head;
			ArrayList<E> ret = new ArrayList<E>();
			while(ptr != null){
				//System.out.print((ptr.data).toString()+" -> ");
				ret.add(ptr.data);
				ptr=ptr.next;
			}
			return ret;
		}
	}