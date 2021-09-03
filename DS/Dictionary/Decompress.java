import java.util.*;
import java.io.*;

public class Decompress {
    public static void main(String[] args) {
        try{
            File f=  new File(args[0]);
            File o= new File(args[1]);
            Decompress obj = new Decompress();

            InputStream is = new FileInputStream(f);
            PrintStream fileOut = new PrintStream(args[1]);
	        System.setOut(fileOut);
            obj.decompress(args[0]);
        }
        catch(IOException g) {

        }
    }
    

    void decompress(String f) throws IOException {
        String[] dedict = new String[65536];

        for (int i = 0; i < 128; i++) {
            dedict[i] = Character.toString((char) i);
        }
        InputStream inputStream = new FileInputStream(f);
        byte[] buffer = new byte[2];
        inputStream.read(buffer);
        int dfirst = getvalue(buffer[0], buffer[1]);
        System.out.print(dedict[dfirst]);

        int old = dfirst;
        int neww = 0;
        int size = 128;
            while (inputStream.read(buffer) != -1) {

                neww = getvalue(buffer[0], buffer[1]);
                if (neww == size) {
                    if (size<65536)
                    {dedict[size] = dedict[old] + Character.toString(dedict[old].charAt(0));}
                    size++;
                    System.out.print(dedict[old] + dedict[old].charAt(0));
                }
                else if (neww<size) {
                    if (size < 65536) {
                        dedict[size] = dedict[old] + Character.toString(dedict[neww].charAt(0));
                    }
                    size++;
                    System.out.print(dedict[neww]);
                }
                old = neww;
            }
        }

    public int getvalue(byte b1, byte b2) {
        int b11,b22;
        b11 = b1;
        b22 = b2;
        if (b1<0)
            b11 = (int)b1+256;
        if (b2<0)
            b22 = (int)b2+256;

        return (b11*256 + b22);
    }


        // String temp1 = Integer.toBinaryString(b1);
        // String temp2 = Integer.toBinaryString(b2);

        // while (temp1.length() < 8) {
        //     temp1 = "0" + temp1;
        // }
        // if (temp1.length() == 16) {
        //     temp1 = temp1.substring(8, 16);
        // }
        // while (temp2.length() < 8) {
        //     temp2 = "0" + temp2;
        // }
        // if (temp2.length() == 16) {
        //     temp2 = temp2.substring(8, 16);
        // }
        
        // // if (onleft) {
        // //     return Integer.parseInt(temp1 + temp2.substring(0, 4), 2);
        // } else {
        //     return Integer.parseInt(temp1.substring(4, 8) + temp2, 2);
        // }


}