import java.util.*;
import javafx.util.*;

class NodeObj implements Comparable<NodeObj>{
	Integer objid,objsize;
	nodeLL<Pair<Integer, Integer> > inbin;
	NodeBin Bin;
	
	NodeObj(){
		this(null,null);
	}
	
	NodeObj(Integer objid,Integer objsize){
		this.objid = objid;
		this.objsize = objsize;
	}
	
	@Override
    public int compareTo(NodeObj o){
		return this.objid.compareTo(o.objid);
	}
}

class NodeBin implements Comparable<NodeBin>{
	Integer binid,binsize;
	NodeCap Cap;
	DoubleLL<Pair<Integer, Integer> > dhol;
	
	NodeBin(){
		this(null,null);
	}
	
	NodeBin(Integer binid,Integer binsize){
		this.binid = binid;
		this.binsize = binsize;
		dhol = new DoubleLL<Pair<Integer, Integer> >();
	}
	
	@Override
    public int compareTo(NodeBin o){
		return this.binid.compareTo(o.binid);
	}
}

class NodeCap implements Comparable<NodeCap>{
	Integer binid,binsize;
	NodeBin Bin;
	
	NodeCap(){
		this(null,null);
	}
	
	NodeCap(Integer binid,Integer binsize){
		this.binid = binid;
		this.binsize = binsize;
	}
	
	@Override
    public int compareTo(NodeCap o){
		int comp = this.binsize.compareTo(o.binsize);
		
		if(comp!=0)
			return comp;
		else
			return this.binid.compareTo(o.binid);
	}
}

class ObjectTooBig extends Exception{
   String str;
 
   ObjectTooBig(String error_message) {
	this.str=error_message;
   }
   public String toString(){ 
	return str ;
   }
}

class ObjectNotPresent extends Exception{
   String str;
 
   ObjectNotPresent(String error_message) {
	this.str=error_message;
   }
   public String toString(){ 
	return str ;
   }
}