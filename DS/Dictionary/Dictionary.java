import java.util.*;

public class Dictionary{
    Arraynode[] bucket;
    static final int MaxSize = 100003;

    public Dictionary() {
        bucket = new Arraynode[MaxSize]; //declare

        for(int i = 0; i < 128; i++) {      //initialize
            bucket[i] = new Arraynode();
            bucket[i].value = Character.toString((char) i);
            bucket[i].number = i;
        }
    }
    
    void addtodict(String s, int key) {
        
        int tmp = Dictionary.hashval(s);
        int i = tmp, h = 1;
        do
        {
            if (bucket[i] == null)
            {
                bucket[i] = new Arraynode(s,key);
                return;
            }        
            i = (i + 2*h-1) % MaxSize;  
            h++;          
        } while (h!=MaxSize);       
    }

    Arraynode searchdict(String s) {
        int k = Dictionary.hashval(s);
        int i = k, h = 1;
        do
        {
            if (bucket[i] == null)
                return null;
            else if(s.equals(bucket[i].value))
            {
              return bucket[i];
            }        
            i = (i + 2*h-1) % MaxSize;  
            h++;          
        } while (h!=MaxSize);
        return null;
        
    }

    static int hashval(String str) {
        int hash=0;
        for(int i=0; i<str.length(); i++)
        hash = ((128*hash)%MaxSize + str.charAt(i))% MaxSize;
        return hash;
    }

}