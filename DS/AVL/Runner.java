import java.util.*; 
import java.io.*;
import javafx.util.Pair;

public class Runner{
	public static void main(String args[]){
		
		long startTime = System.nanoTime();
		try{
			
			//File file = new File(args[0]); 
			File file = new File("new_large.txt"); 
			
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
		
		/*DoubleLL<Integer> test = new DoubleLL<>();
		nodeLL<Integer> hi;
		AVL<Integer> test = new AVL<Integer>();
		test.insert(15);
		test.insert(18);
		test.insert(25);
		test.insert(28);
		test.insert(30);
		test.insert(30);
		test.insert(30);
		test.insert(30);
		test.insert(34);
		test.insert(39);
		test.insert(48);
		test.insert(57);
		test.insert(58);
		test.insert(59);
		System.out.println(test.traverse());
		//test.delete(a);
		//test.print();*/
		
	}
}