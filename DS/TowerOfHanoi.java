import java.util.Scanner;

public class TowerOfHanoi{
	//with recursion
	public static void toh_with_recursion (int num_disks, int start_pos, int end_pos) {
		if (start_pos == end_pos){
			return;
		}

		else if (start_pos != end_pos){
			int buffer = 6 - (start_pos + end_pos);

		if (num_disks==1) {
			System.out.println (start_pos + " " + end_pos);
			return;
		}
		else {
			toh_with_recursion (num_disks-1, start_pos, buffer);
			System.out.println(start_pos + " " + end_pos);
			toh_with_recursion (num_disks-1, buffer, end_pos);
		}
	}
	}
	//with recursion over

	//without recursion
	public static void toh_without_recursion(int num_disks, int start_pos, int end_pos) {
		
         class Frame {
         	public int numdisks;
         	public int startpos;
         	public int endpos;
         	public Frame(int numdisks, int startpos, int endpos) {
         		this.numdisks = numdisks;
         		this.startpos = startpos;
         		this.endpos = endpos;
         	}
         }
            if (start_pos != end_pos) {
            MyStack<Frame> s = new MyStack<Frame>();
              s.push(new Frame (num_disks, start_pos, end_pos));
              while (!s.empty()) {
                Frame top = s.pop();
                if (top.numdisks==1) {
                  System.out.println(top.startpos+" "+top.endpos);
               }
                else {
                  int buffer = 6 - (top.startpos + top.endpos);
                  s.push (new Frame (top.numdisks-1, buffer, top.endpos));
                  s.push (new Frame (1, top.startpos, top.endpos));
                  s.push (new Frame (top.numdisks-1, top.startpos, buffer));
                }
              }
            }
            else {
               return;
            }
    }
    //without recursion over
}