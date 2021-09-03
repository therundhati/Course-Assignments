import java.util.Scanner;

public class GeneralizedTowerOfHanoi {
	//with recursion
   public static void gtoh_with_recursion (int num_disks, int start_pos, int r, int b) {
      TowerOfHanoi obj = new TowerOfHanoi();
      if (start_pos==r && start_pos==b) {
         return;
      }
      int buffer=0;
      int end_pos=0;
      if (num_disks%2!=0){
         end_pos=b;
        }
        else {
         end_pos=r;
        }
        if (start_pos != end_pos){
         buffer = 6-(start_pos+end_pos);
        }
        else {
         if (num_disks%2!=0) {
            buffer = r;
         }
         else {
            buffer = b;
         }
        }
      if (num_disks==1) {
         obj.toh_with_recursion(num_disks, start_pos, end_pos);
      }
      if (num_disks>1) {
         obj.toh_with_recursion(num_disks-1, start_pos, buffer);
         obj.toh_with_recursion(1, start_pos, end_pos);
         gtoh_with_recursion(num_disks-1, buffer, r, b);
      }  
      
   }
	//with recursion over

	//without recursion
	public static void gtoh_without_recursion (int num_disks, int start_pos, int r, int b) {
        class Frame {
         	public int numdisks;
         	public int startpos;
         	public int r;
         	public int b;
         	public Frame(int numdisks, int startpos, int r, int b) {
         		this.numdisks = numdisks;
         		this.startpos = startpos;
         		this.r = r;
         		this.b = b;
         	}
         }
         	MyStack<Frame> s = new MyStack<Frame>();
         	TowerOfHanoi obj = new TowerOfHanoi();
         	s.push(new Frame (num_disks, start_pos, r, b));
         	while (!s.empty()) {
         		Frame top = s.pop();
         		if (top.numdisks==1) {
         			obj.toh_without_recursion(top.numdisks, top.startpos, top.r);
         		}
         		else {
                  if (top.numdisks%2==0) {
         			   if (top.numdisks > 2) {
         				  gtoh_without_recursion(top.numdisks-2, top.b, top.r, top.b);
         			   }
         			   obj.toh_without_recursion(top.numdisks-1, top.r, top.b);
         			   obj.toh_without_recursion(top.numdisks, top.startpos, top.r);
                  }
                  else {
                     if (top.numdisks > 2) {
                        gtoh_without_recursion(top.numdisks-2, top.r, top.b, top.r);
                     }
                     obj.toh_without_recursion(top.numdisks-1, top.b, top.r);
                     obj.toh_without_recursion(top.numdisks, top.startpos, top.b);
                     }
                  }
         		}
    }
    //without recursion over
}