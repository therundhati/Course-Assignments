import java.util.*;
import java.io.*;

public class Compress {
    public static void main(String[] args) {
        try{
            Compress obj = new Compress();

            InputStream is = new FileInputStream(args[0]); 
            BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
            String line = buf.readLine(); 
            StringBuilder sb = new StringBuilder(); 
            while(line != null){ 
                sb.append(line).append("\n"); 
                line = buf.readLine(); 
            } 
            String pooristring = sb.toString();
            obj.compress(pooristring, args[1]);
        }
        catch(IOException g) {

        }
    }
    void compress(String f, String out) throws IOException {
        OutputStream os = new FileOutputStream(out);

        int counter = 128;
        Dictionary dict = new Dictionary();

        String first = "";
        for (int i=0; i<f.length(); i++){
            String next =  f.charAt(i)+"";
            String find = first+next;
            if (dict.searchdict(find) != null) {
                first = first + next;
            }
            else {
                int s = dict.searchdict(first).number;
                byte[] code = new byte[2];
                int a = s/256;
                int b = s%256;
                code[0] = (byte)(b);
                code[1] = (byte)(a);
                os.write(code);

                first = next;
                dict.addtodict(find, counter);
                counter++;
            }
        }
        if(!first.equals("")){
            int s = dict.searchdict(first).number;
            byte[] code = new byte[2];
            code[0] = (byte)(s/256);
            code[1] = (byte)(s%256);
            os.write(code);}
    }
}