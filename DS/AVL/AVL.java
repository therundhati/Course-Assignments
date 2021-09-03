import java.util.*;

public class AVL<T extends Comparable<T>> {
    private AVLNode<T> root;
    
    public AVL() {
        this.root = null; //root of the tree
    }
    
    public AVLNode<T> getRoot() {
        return this.root;
    }
   
	public T getmax(){
		AVLNode<T> tempo = this.root;
		T ret = null;
		
		while (tempo != null) {
            ret = tempo.getdata();
			tempo = tempo.getRight();
        }
		
		return ret;
	}
    public AVLNode<T> search(T val){
        AVLNode<T> tempo = this.root;
        
        if (this.root == null) {
            return null;
        }
        
        while (tempo != null) {
            if (tempo.compareTo(val) == 0) {
                return tempo;
            } else if (tempo.compareTo(val) <= -1) {
                tempo = tempo.getRight();
            } else {
                tempo = tempo.getLeft();
            }
        }
        return null;
    }
    
    public void insert(T val) {
		AVLNode<T> node = new AVLNode<T>();
		node.setdata(val);
		
        if (this.root == null) {
            this.root = node;
            return;
        } else {
            AVLNode<T> tempo = this.root;
            
            while (true) {
                if (tempo.compareTo(val) == 0) {
                    return; 
                } else if (tempo.compareTo(val) <= -1) {
                   
                    if (tempo.getRight() == null) {
                        tempo.setRightChild(node);
                        node.setParent(tempo);
                        
						
                        this.balanceTreeIns(node);
                        
                        return;
                    } else {
                        tempo = tempo.getRight();
                    }
                } else {
					
                    if (tempo.getLeft() == null) {
                        tempo.setLeftChild(node);
                        node.setParent(tempo);
                        
						
                        this.balanceTreeIns(node);
                        
                        return;
                    } else {
                        tempo = tempo.getLeft();
                    }
                }
            }
        }
    }
    
    public void delete(T val) {
        AVLNode<T> todel = this.search(val); 
		
        AVLNode<T> todelParent; 
        
        if (todel != null) {
			
            if (todel.getLeft() == null && todel.getRight() == null) {
				
                if (todel != this.root) {
                    todelParent = todel.getParent();
					
                    if (todelParent.getLeft() == todel) {
                        todelParent.setLeftChild(null);
                    } else {
                        todelParent.setRightChild(null);
                    }
					
                    todel.setParent(null);
					
                    this.balanceTreeDel(todelParent);
                } else {
                    this.root = null;
                }
				
            } else if(todel.leftHeight == 1 && todel.rightHeight == 0) {
                AVLNode<T> todelLeftChild = todel.getLeft();
                todelParent = todel.getParent();
				
                if (todel != this.root) {
                    if (todelParent.getLeft() == todel) {
                        todelParent.setLeftChild(todelLeftChild);
                    } else {
                        todelParent.setRightChild(todelLeftChild);
                    }
					
                    todelLeftChild.setParent(todelParent);
					
                    this.balanceTreeDel(todel.getLeft());
                } else {
					
                    todelLeftChild.setParent(todelParent);
					
                    this.root = todel.getLeft();
                }
                todel.setLeftChild(null); 
                todel.setParent(null); 
				
            } else if (todel.leftHeight == 0 && todel.rightHeight == 1) {
                AVLNode<T> todelRightChild = todel.getRight(); 
                todelParent = todel.getParent();
				
                if (todel != this.root) {
                    if (todelParent.getLeft() == todel) {
                        todelParent.setLeftChild(todelRightChild);
                    } else {
                        todelParent.setRightChild(todelRightChild);
                    }
					
                    todelRightChild.setParent(todelParent);
					
                    this.balanceTreeDel(todel.getRight());
                } else {
					
                    todelRightChild.setParent(todelParent);
					
                    this.root = todelRightChild;
                }
                todel.setRightChild(null); 
                todel.setParent(null); 
				
            } else {
                todelParent = todel.getParent();
				
                AVLNode<T> todel2 = this.get_extra(todel);
                AVLNode<T> tempNode;
                
				
                if (todel == todel2.getParent()) {
                    tempNode = todel2;
                } else {
                    tempNode = todel2.getParent();
                }
                
				
                if (todel2.getParent().getLeft() == todel2) {
                    if (todel2.getRight() != null) {
                        todel2.getParent().setLeftChild(todel2.getRight()); 
                        todel2.getRight().setParent(todel2.getParent()); 
                    } else {
                        todel2.getParent().setLeftChild(null);
                    }
                    todel2.setParent(null);
                } else {
                    if (todel2.getLeft() != null) {
                        todel2.getParent().setRightChild(todel2.getLeft());
                        todel2.getLeft().setParent(todel2.getParent());
                    } else {
                        todel2.getParent().setRightChild(null);
                    }
                    todel2.setParent(null);
                }
                
				
                if (todelParent != null) {
                    todel2.setParent(todelParent); 
					
                    if (todelParent.getLeft() == todel) {
                        todelParent.setLeftChild(todel2);
                    } else {
                        todelParent.setRightChild(todel2);
                    }
                } else {
                    this.root = todel2;
                }
                
                if (todel.getLeft() != null) {
                    todel2.setLeftChild(todel.getLeft());
                    todel.getLeft().setParent(todel2);
                }
                if (todel.getRight() != null) {
                    todel2.setRightChild(todel.getRight());
                    todel.getRight().setParent(todel2);
                }
                
                this.balanceTreeDel(tempNode);
                
                todel.setParent(null);
                todel.setLeftChild(null);
                todel.setRightChild(null);
            }
        } 
        return ;
    }
    
    private boolean rotate(AVLNode<T> node) {
        if (node.BalanceFactor() >= 2) {
            if (node.getRight().leftHeight > node.getRight().rightHeight) {
                this.rightLeftRotation(node);
            } else {
                this.leftRotation(node);
            }
			return true;
        }
		
        else if (node.BalanceFactor() <= -2) {
			
            if (node.getLeft().rightHeight > node.getLeft().leftHeight) {
				
                this.leftRightRotation(node);
            } else {
				
                this.rightRotation(node);
            }
			return true;
        }
		
		else return false;
    }
    
    private void balanceTreeDel(AVLNode<T> node) {
		
        while (node != null) {
			
            node.update();			
            this.rotate(node);
            node = node.getParent();
        }
    }
	
	private void balanceTreeIns(AVLNode<T> node) {
		
        while (node != null) {
			
            node.update();
            boolean ans = this.rotate(node);
			if(ans == true)break;
            node = node.getParent();
        }
    }
    
    private void leftRotation(AVLNode<T> node) {
        AVLNode<T> rightNode = node.getRight(); 
		
        AVLNode<T> parentNode = node.getParent(); 
		
        if (parentNode == null) {
            rightNode.setParent(null);
            this.root = rightNode;
        } else if (parentNode.getRight() == node) {   
		
            parentNode.setRightChild(rightNode);
        } else {                         
		
            parentNode.setLeftChild(rightNode);
        }
		
        rightNode.setParent(node.getParent());
		
        node.setParent(rightNode);
        
		
        if (rightNode.getLeft() == null) {
			
            node.setRightChild(null);
			
            rightNode.setLeftChild(node);
        } else {
            rightNode.getLeft().setParent(node);
            node.setRightChild(rightNode.getLeft());
            rightNode.setLeftChild(node);
        }
		
        node.update();
        node.getParent().update();
    }
    
    private void rightRotation(AVLNode<T> node) {
        AVLNode<T> leftNode = node.getLeft(); 
        AVLNode<T> parentNode = node.getParent(); 
        if (parentNode == null) {
            leftNode.setParent(null);
            this.root = leftNode;
        } else if (parentNode.getLeft() == node) {   
		
            parentNode.setLeftChild(leftNode);
        } else {                          
            parentNode.setRightChild(leftNode);
        }
		
        leftNode.setParent(node.getParent());
		
        node.setParent(leftNode);
        
		
        if (leftNode.getRight() == null) {
			
            node.setLeftChild(null);
			
            leftNode.setRightChild(node);
        } else {
            leftNode.getRight().setParent(node);
            node.setLeftChild(leftNode.getRight());
            leftNode.setRightChild(node);
        }
		
        node.update();
        node.getParent().update();
    }
    
    private void leftRightRotation(AVLNode<T> node) {
        this.leftRotation(node.getLeft());
        this.rightRotation(node);
    }
    
    private void rightLeftRotation(AVLNode<T> node) {
        this.rightRotation(node.getRight());
        this.leftRotation(node);
    }
    
    private AVLNode<T> get_extra(AVLNode<T> node) {
        AVLNode<T> tempNode;
		
        if (node.getRight() == null) {
            tempNode = node.getLeft(); 
            while (true) {
				
                if (tempNode.getRight() != null) {
                    tempNode = tempNode.getRight();
                } else {
					
                    break;
                }
            }
			
            return tempNode;
        } else {
			
            tempNode = node.getRight();
            while (true) {
                if (tempNode.getLeft() != null) {
                    tempNode = tempNode.getLeft();
                } else {
                    break;
                }
            }
            return tempNode;
        }
    }
    
	public void print(){
		System.out.println("Count = "+printHelper(this.root));
	}
	
	private int printHelper(AVLNode<T> temp){
		if(temp == null)return 1;
		int a,b;
		a = printHelper(temp.getLeft());
		System.out.println(temp.getdata().toString());
		b = printHelper(temp.getRight());
		
		return AVL.max(a,b)+1;
	}
	
	static int max(int a,int b){
		if(a>b)return a;
		else return b;
	}
}

class AVLNode<T extends Comparable<T>> implements Comparable<T> {

	private T data;
	private AVLNode<T> left, right, parent; 
    int leftHeight, rightHeight; 

    public AVLNode() {
        this.left = null;
        this.right = null;
        this.parent = null;
        this.leftHeight = 0;
        this.rightHeight = 0;
    }
    
	 public void setdata(T data){
		 this.data = data;
	 }
	 public T getdata(){
		 return this.data;
	 }
    public int BalanceFactor() {
        return (this.rightHeight - this.leftHeight);
    }
    
    public int nodeHeight() {
        return AVL.max(this.rightHeight,this.leftHeight);
    }
    
    public void update() {
        if (this.right == null) {
            this.rightHeight = 0;
        } else {
            this.rightHeight = this.right.nodeHeight() + 1;
        }
        
        if (this.left == null) {
            this.leftHeight = 0;
        } else {
            this.leftHeight = this.left.nodeHeight() + 1;
        }
    }
    
	@Override
    public int compareTo(T o){
		return this.data.compareTo(o);
	}
    
    @Override
    public String toString(){
		return data.toString();
	}
    
    public AVLNode<T> getLeft() {
        return left;
    }

    public void setLeftChild(AVLNode<T> left) {
        this.left = left;
    }

    public AVLNode<T> getRight() {
        return right;
    }

    public void setRightChild(AVLNode<T> right) {
        this.right = right;
    }

    public AVLNode<T> getParent() {
        return parent;
    }

    public void setParent(AVLNode<T> parent) {
        this.parent = parent;
    }

}