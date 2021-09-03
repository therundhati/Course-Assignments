import java.util.*;
import java.io.*;

public class Runner{
	public static void main(String args[]){
		
		long startTime = System.nanoTime();
		try{
			
			File file = new File(args[0]);
			
			PrintStream fileOut = new PrintStream(args[1]);
			System.setOut(fileOut);
			
			Scanner s = new Scanner(file);
			SuffixTree find = new SuffixTree(file.nextline() + "$");
			
			
                int number = s.nextInt();
                int i = 0;
                while (i<number) {
                    String pat = file.nextline();
                    ArrayList<Integer> print = find.lookfor(0, pat);
                    i++;
                    for(int l=0; l<print.size(); l++) {
                        System.out.println(print.get(l) + print.get(l)+pat.length());
                    }
                }
			
		}catch(FileNotFoundException e){
			System.out.println("File Not Found");
		}
		
		long endTime = System.nanoTime();
		
		System.out.println("Took "+((float)(endTime-startTime)/1000000000)+" s");
		
	}
}