import java.util.*;

class SuffixTree {
    ArrayList<Node> nodes = new ArrayList<>();

    SuffixTree(String str) {
        nodes.add(new Node());                                  //starting root

        for (int i=0; i<str.length; i++) {
            addsuffix(str.substring(i), i, str.length());
        }
    }

    private void addsuffix(String suffix, int pos, int length) {
        int n= 0;
        int i= 0;
        while (i<suffix.length) {
            int x=0;
            int n2=0;
            char q = suffix.charAt(i);
            ArrayList<Integer> childrennodes = nodes.get(n).children;
            while (true) {
                if (x==childrennodes.size()) {                  //new child for this suffix, no matching node
                    n2 = nodes.size();
                        Node temp = new Node();
                        temp.string = suffix.substring(i);
                        temp.start = pos+i;
                        temp.end = length;
                        temp.firstchar = suffix.CharAt(i);
                        temp.prefixfirst = pos;
                        nodes.add(temp);
                        childrennodes.add(n2);
                        return;
                }
                n2 = childrennodes.get(x2);
                    if (nodes.get(n2).string.charAt(0) == q) {
                        break;
                    }
                    x2++;
            }

            String more = nodes.get(n2).string;               //add part not in common with any node's string
                int j = 0;
                while (j < more.length()) {
                    if (suffix.charAt(i + j) != more.charAt(j)) {          //split node
                        int n3 = n2;
                        n2 = nodes.size();
                        Node temp = new Node();
                        temp.string = more.substring(0, j);
                        temp.start = pos+i;
                        temp.end = pos+i+j-1;
                        temp.prefixfirst = null;
                        temp.children.add(n3);
                        nodes.add(temp);
                        nodes.get(n3).string = more.substring(j);           //old node updated
                        nodes.get(n3).start = pos+i+j;
                        nodes.get(n3).end = length;
                        nodes.get(n3).prefixfirst = pos;
                        nodes.get(n3).firstchar = more.charAt(j);
                        nodes.get(n).children.set(x2, n2);
                        break;
                    }
                    j++;
                }
                i += j;
                n = n2;  
        }

    }
    public ArrayList<Integer> lookfor(int n, String pattern) {
        if (pattern.length()==0) {
            System.out.println("No pattern");
            return null;
        }
        int flag = 0;
        ArrayList<Integer> index = new ArrayList<>();

        for (int i=0; i<pattern.size(); i++) {
            if (pattern.charAt(i) == '*')
                flag = 1;
        }

        if (flag = 0) {
            ArrayList childrennodes = nodes.get(n).children;
            for (int i=0; i<childrennodes.size(); i++) {
                if (nodes.get((int)childrennodes.get(i)).firstchar != pattern.charAt(0) && pattern.charAt(0) != '?') {
                    i++;
                }
                else if (nodes.get((int)childrennodes.get(i)).firstchar == pattern.charAt(0) || pattern.charAt(0)=='?') {
                    if (pattern.length()<=nodes.get((int)childrennodes.get(i)).string.length()) {
                        if (pattern.equals(nodes.get((int)childrennodes.get(i)).string.substring(0, pattern.length()))) {
                            
                            Node fin = nodes.get((int)childrennodes.get(i));

                            Queue<Integer> q = new LinkedList<>();
                            q.add((int)childrennodes.get(i));
                            while(!q.isEmpty()) {
                                int k=q.remove();
                                if (nodes.get(k).children==null) {
                                    index.add(nodes.get(k).prefixfirst);
                                }
                                else {
                                    for (int m=0; m<nodes.get(k).size(); m++) {
                                        q.add(nodes.get(k).children(m));
                                    }
                                }
                            }
                            
                                
                            }
                            
                        }
                        else {
                            System.out.println("Pattern not found");
                            break;
                        }
                    
                    if (pattern.length()>nodes.get((int)childrennodes.get(i)).string.length()) {
                        if (pattern.substring(0, nodes.get((int)childrennodes.get(i)).string.length()).equals(nodes.get((int)childrennodes.get(i)).string)) {
                            String subpat = pattern.substring(nodes.get((int)childrennodes.get(i)).string.length());
                            return lookfor(i, subpat);
                        }
                        else {
                            System.out.println("Pattern not found");
                            break;
                        }
                    }
                }
                else {
                    System.out.println("Pattern not found");
                    return null;
                }
            }
            return index;
        }
        else {
            String[] parts = string.split("*");
            if (parts[0] == null && parts[1] == null) {
                for (int i=0; i<nodes.get(1).string.length(); i++) {
                    for (int j=0; i<(nodes.get(1).string.length())/2; i++)
                        System.out.println(j);
                        System.out.println( i);
                }
            }
            else if (parts[0] == null) {
                lookfor(0, parts[1]);

            }
            else if (parts[1] == null) {
                lookfor(0, parts[0]);

            }
            else {
                lookfor(0, parts[0]);
                lookfor(0, parts[1]);
            }
        }
        
        }

}