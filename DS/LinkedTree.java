import java.util.Scanner;
import java.util.*;
import java.util.Queue;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;

public class LinkedTree{

	public class Node {
		public String employeename;
		public Node parent;
		public int level;
		public Vector<Node> v;  //stores children

//constructor node
		public Node (String employeename, Node parent, int level, Vector v) {
			employeename = employeename;
			parent = parent;
			level = level;
			v = v;
		}

//make new node
		public Node createNode(String employeename, Node parent, int level, Vector v) {
			return new Node(employeename, parent, level, v);
		}
	}

	public class BSTNode {
		public String key;
		public Node value;
		public BSTNode left;
		public BSTNode right;

		public BSTNode(String employeename, Node node, BSTNode left, BSTNode right){
			key = employeename;
			value = node;
			left = left;
			right = right;
		}

		public BSTNode createBSTNode(String employeename, Node node) {
			return new BSTNode(employeename, node, null, null);
		}
	}

	public class Tree {
		Node root;
		Tree(String employeename) {
			root = new Node(employeename, null, 1, null);
		}
		Tree() {
			root = null;
		}
	}

	public class BSTree {
		public BSTNode bstroot;
		BSTree(String employee, Node value) {
			bstroot = new BSTNode(employee, value, null, null);
		}
		BSTree() {
			bstroot = null;
		}

		public Node lookup(BSTNode bstroot, String S) {
			if (bstroot.key==S) {
				return bstroot.value;
			}
			if (bstroot.key==null) {
				throw new IllegalArgumentException();
			}
			else if (S.compareTo(bstroot.key)<0) {
				return lookup(bstroot.left, S);
			}
			else {
				return lookup(bstroot.right, S);
			}
		}

		public void insert(String employeename, Node value) {
			bstroot = insertrecursive(bstroot, employeename, value);
		}
		BSTNode insertrecursive(BSTNode bstroot, String employeename, Node value) {
			if (bstroot==null) {
				bstroot = new BSTNode(employeename, value, null, null);
				return bstroot;
			}
			if (employeename.compareTo(bstroot.key)<0) {
				bstroot.left=insertrecursive(bstroot.left, employeename, value);
			}
			else if (employeename.compareTo(bstroot.key)>0) {
				bstroot.right=insertrecursive(bstroot.right, employeename, value);
			}
			return bstroot;
		}

		public void delete(String employeename) {
			bstroot = deleterecursive(bstroot, employeename);
		}
		BSTNode deleterecursive(BSTNode bstroot, String employeename) {
			if (bstroot==null) {
				return bstroot;
			}
			else if (employeename.compareTo(bstroot.key)<0) {
				bstroot.left = deleterecursive(bstroot.left, employeename);
			}
			else if (employeename.compareTo(bstroot.key)>0) {
				bstroot.right = deleterecursive(bstroot.right, employeename);
			}
			else if (employeename==bstroot.key) {
				//one child node or no child
				if (bstroot.left==null)
					{return bstroot.right;}
				if (bstroot.right==null)
					{return bstroot.left;}
				else {
					bstroot.key = minimum(bstroot.right);
					bstroot.right = deleterecursive(bstroot.right, bstroot.key);
				}
			}
			return bstroot;
		}

		String minimum(BSTNode p) {
			String minv = p.key;
			while (p.left !=null) {
				minv = p.left.key;
				p=p.left;
			}
			return minv;
		}
	}


//Query funtions
	public class Implementation {
		BSTree bst = new BSTree();
		Tree tree = new Tree();

	public void AddEmployee(String S, String  Ss) {
		Node Sboss = bst.lookup(bst.bstroot, Ss);
		Node Newemployee = new Node(S, Sboss, Sboss.level+1, null);
		//Newemployee=Newemployee.createNode(S, Sboss, Sboss.level+1, null);
		Sboss.v.add(Newemployee);
		bst.insert(S, Newemployee);
	}

	public void DeleteEmployee(String S, String  Ss) {
		Node todelete = bst.lookup(bst.bstroot, S);
		Node reassign = bst.lookup(bst.bstroot, Ss);
		todelete.parent.v.remove(todelete);
		if (todelete.v!=null) {
			reassign.v.addAll(todelete.v);
			for (int i=0; i<todelete.v.size(); i++) {
				todelete.v.get(i).parent=reassign;
			}
		}
		bst.delete(S);
	}

	public void PrintEmployees(Node root){
		if (root==null) {return;}
		Queue<Node> q = new LinkedList<>();
		q.add(root);

			int n = q.size();
			while (n>0) {
				Node p = q.peek();
				q.remove();
				System.out.println(p.employeename + " ");
				for (int i=0; i < p.v.size(); i++) {
					q.add(p.v.get(i));
				}
				n--;
			}


	}

	public String LowestCommonBoss(String S, String Ss) {
		Node employee1 = bst.lookup(bst.bstroot, S);
		Node employee2 = bst.lookup(bst.bstroot, Ss);
		if (employee1.level>employee2.level) {
			while (employee1.level>employee2.level) {
				employee1 = employee1.parent;
			}
		}
		else if (employee2.level>employee1.level) {
			while (employee2.level>employee1.level) {
				employee2 = employee2.parent;
			}
		}
		if (employee1.level==employee2.level) {
			if (employee1.parent==employee2.parent) {
				return employee1.parent.employeename;
			}
			else {
				while (employee1.parent != employee2.parent) {
				employee1 = employee1.parent;
				employee2 = employee2.parent;
			}
				;
			}
		}
		return employee1.parent.employeename;
	}

	}


	public void main (String[] args) {
		File f = new File(args[0]);
		try {
		Implementation imp = new Implementation();
		Scanner s = new Scanner(f);

		String line = s.nextLine();
		String[] a = line.split(" ");
		int Num_employees = Integer.parseInt(a[0]);

		String after = s.nextLine();
		String[] m = after.split(" ", 0);
		Tree tree = new Tree(m[1]);
		BSTree bstree = new BSTree(m[1], tree.root);
		imp.AddEmployee(m[0], m[1]);


		for (int i = Num_employees; i>1; i--) {
			String[] x = s.nextLine().split(" ");
			imp.AddEmployee(x[0], x[1]);
		}

		int Num_commands = s.nextInt();
		for (int j=0; j<Num_commands; j++) {
			String[] x = s.nextLine().split(" ");

			if (x[0]=="0") {
				imp.AddEmployee(x[1], x[2]);
			}
			else if (x[0]=="1") {
				imp.DeleteEmployee(x[1], x[2]);
			}
			else if (x[0]=="2") {
				System.out.println(imp.LowestCommonBoss(x[1], x[2]));
			}
			else if (x[0]=="3") {
				imp.PrintEmployees(tree.root);
			}
		}
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	}	
}

