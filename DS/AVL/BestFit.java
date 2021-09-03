import java.util.*;
import java.io.*;
import javafx.util.Pair; 

class BestFit{
	
	AVL<NodeObj> ObjTree;
	AVL<NodeBin> BinTree;
	AVL<NodeCap> CapTree;
	
    public BestFit(){
            ObjTree = new AVL<NodeObj>();
			BinTree = new AVL<NodeBin>();
			CapTree = new AVL<NodeCap>();;
        }
    public void add_bin(int bin_id, int capacity){
		NodeBin o1 = new NodeBin(bin_id,capacity);
		NodeCap o2 = new NodeCap(bin_id,capacity);
		o1.Cap = o2; o2.Bin = o1;
		
		BinTree.insert(o1);
		CapTree.insert(o2);
    }

    public int add_object(int obj_id, int size) throws ObjectTooBig{
		NodeCap max = CapTree.getmax();
		NodeObj o = new NodeObj(obj_id,size);
		
		if(max.binsize < size){
			throw new ObjectTooBig("Object with object id = "+obj_id+" is too big for any bin");
		}
		
		o.Bin = max.Bin;
		max.Bin.binsize -= size;
		Pair <Integer, Integer> ans = new Pair <Integer, Integer> (obj_id,size); 
		o.inbin = max.Bin.dhol.insert(ans);
		ObjTree.insert(o);
		CapTree.delete(max);
		max.binsize -= size;
		CapTree.insert(max);
		
		return max.binid;
    }

    public int delete_object(int obj_id) throws ObjectNotPresent{
		NodeObj temp = new NodeObj(obj_id,null);
		if(ObjTree.search(temp) == null){
			ObjTree.print();
			throw new ObjectNotPresent("Object with obj id = "+obj_id+" is not present");
		}
		NodeObj delobj = ObjTree.search(temp).getdata();
		ObjTree.delete(delobj);
		NodeBin delbin = delobj.Bin;
		NodeCap delcap = delbin.Cap;
		
		int size = delobj.objsize;
		delbin.binsize += size;
		CapTree.delete(delcap);
		delcap.binsize += size;
		CapTree.insert(delcap);
		delbin.dhol.delete(delobj.inbin);
		
		return delbin.binid;
    }

    public List<Pair<Integer, Integer> > contents(int bin_id){
		NodeBin temp = new NodeBin(bin_id,null);
		NodeBin reqbin = BinTree.search(temp).getdata();
		return reqbin.dhol.traverse();
	}
   public static void main(String args[]){
		String inputfileName = args[0];
		
		long startTime = System.nanoTime();
		try{
			
			File file = new File(inputfileName); 
			//File file = new File("new_large.txt"); 
			
			PrintStream fileOut = new PrintStream("./output.txt");
			System.setOut(fileOut);
			
			Scanner input = new Scanner(file);
			BestFit IITD = new BestFit();
			
			while(input.hasNext()){
				int choice = input.nextInt();
				
				if(choice == 1){
					int id,c;
					id = input.nextInt();
					c = input.nextInt();
					IITD.add_bin(id,c);
				}
				else if(choice == 2){
					int id,s;
					id = input.nextInt();
					s = input.nextInt();
					System.out.println(IITD.add_object(id,s));
				}
				else if(choice == 3){
					int id;
					//System.out.println("HI");
					id = input.nextInt();
					System.out.println(IITD.delete_object(id));
				}
				else if(choice == 4){
					int id;
					id = input.nextInt();
					List<Pair<Integer, Integer> > parchi = IITD.contents(id);
					for (Pair <Integer,Integer> temp : parchi) 
						{ 
							// Get the score of Student 
							int val = temp.getValue(); 
							int key = temp.getKey();
							System.out.println(key+" "+val);
						} 
				}
			}
		}catch(FileNotFoundException e){
			System.out.println("File Not Found");
		}catch(ObjectTooBig e){
			System.out.println(e);
		}catch(ObjectNotPresent e){
			System.out.println(e);
		}
		
		long endTime = System.nanoTime();
		
		System.out.println("Took "+((float)(endTime-startTime)/1000000000)+" s");
	}
}