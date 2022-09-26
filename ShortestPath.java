import java.util.*;
import com.google.common.graph.*;
import java.lang.*;

public class ShortestPath {
	public static int[] DijkstraAlg (MutableValueGraph<Integer, Integer> graph) {
        int[] D = new int[graph.nodes().size()];
        boolean[] fixed = new boolean[graph.nodes().size()];
        for (int i = 0; i < D.length; i++) {
        	D[i] = Integer.MAX_VALUE;
        	fixed[i] = false;
        }
       	PriorityQueue<KeyValPair> H = new PriorityQueue<KeyValPair>();
       	D[0] = 0;
       	H.add(new KeyValPair(0, D[0]));
       	while (!H.isEmpty()) {
       		int j = H.peek().getJ();
       		int d = H.peek().getD();
       		H.poll();
       		fixed[j] = true;
       		for (int k = 0; k < fixed.length; k++) {
        		if (!fixed[k] && graph.hasEdgeConnecting(j, k)) {
        			if (D[k] > D[j] + graph.edgeValueOrDefault(j, k, 0)) {
        				D[k] = D[j] + graph.edgeValueOrDefault(j, k, 0);
        				Iterator<KeyValPair> it = H.iterator();
        				while (it.hasNext()) {
        					if (it.next().getJ() == k) {
        						it.remove();
        					}
        					break;
        				}
        				H.add(new KeyValPair(k, D[k]));
       				}
       			}
       		}
       	}
       	return D;
	}
}

class KeyValPair implements Comparable<KeyValPair> {
	private int j, d;
	public KeyValPair (int j, int d) {
		this.j = j;
		this.d = d;
	}
	@Override
	public int compareTo (KeyValPair keyVal) {
		if (d < keyVal.d) { 
			return -1;
		}
		else if (d > keyVal.d) { 
			return 1;
		}
		return 0;
	}
	
	public int getJ () {
		return j;
	}
	
	public int getD () {
		return d;
	}
}