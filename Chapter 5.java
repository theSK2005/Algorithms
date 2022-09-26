import java.util.*;
import com.google.common.graph.*;
 
public class Graph {
    public static int[] LLPTraversal (MutableGraph<Integer> graph, int j) {
        int[] G = new int[graph.nodes().size()];
        Integer[] pre = (Integer[]) graph.predecessors(j).toArray();
        if (j == 0) {
            G[j] = 1;
        }
        else {
            G[j] = 0;
        }
        for (int i : pre) {
            if (G[i] == 1 && G[j] == 0 && graph.hasEdgeConnecting(i, j)) {
                G[j] = 1;
            }
        }
        return G;
    }
    
    public static int[] LLPTraversalSequential (MutableGraph<Integer> graph, int s) {
    	int[] G = new int[graph.nodes().size()];
    	Set<Integer> S = new HashSet<>();
    	S.add(s);
    	G[s] = 0;
    	int j = 0;
    	while (!S.isEmpty()) {
    		for (int i = 0; i < G.length; i++) {
    			if (S.contains(i)) {
    				j = i;
    				S.remove(i);
    			}
    		}
    		Integer[] suc = (Integer[]) graph.successors(j).toArray();
			for (int k : suc) {
				if (G[k] == 0) {
					G[k] = 1;
					S.add(k);
				}
			}
    	}
    	return G;
    }
    
    public static int[] BFSTraversalSequential (MutableGraph<Integer> graph, int s) {
    	int[] G = new int[graph.nodes().size()];
    	int[] parent = new int[graph.nodes().size()];
    	for (int i = 0; i < G.length; i++) {
    		G[i] = Integer.MAX_VALUE;
    		parent[i] = -1;
    	}
    	Queue<Integer> S = new LinkedList<Integer>();
    	S.add(s);
    	G[s] = 0;
    	int j;
    	while (!S.isEmpty()) {
    		j = S.remove();
    		Integer[] suc = (Integer[]) graph.successors(j).toArray();
    		for (int k : suc) {
    			if (G[k] > G[j] + 1) {
    				G[k] = G[j] + 1;
    				parent[k] = j;
    				S.add(k);
    			}
    		}
    	}
    	return G;
    }
    
    public static int[] BFSTraversalParallel (MutableGraph<Integer> graph, int s) {
    	int[] G = new int[graph.nodes().size()];
    	int[] parent = new int[graph.nodes().size()];
    	for (int i = 0; i < G.length; i++) {
    		G[i] = Integer.MAX_VALUE;
    		parent[i] = -1;
    	}
    	Set<Integer> S = new HashSet<Integer>();
    	Set<Integer> T = new HashSet<Integer>();
    	S.add(s);
    	G[s] = 0;
    	while (!S.isEmpty()) {
    		T.clear();
    		for (Integer j : S) {
    			Integer[] suc = (Integer[]) graph.successors(j).toArray();
    			for (Integer k : suc) {
    				//need to parallelize
    				if (G[k] > G[j] + 1) {
    					G[k] = G[j] + 1;
    					parent[k] = j;
    					T.add(k);
    				}
    			}
    		}
    		S = T;
    	}
    	return G;
    }
    
    public static void SlowComponents (MutableGraph<Integer> graph, int j, int[] G) {
    	G[j] = j;
    	Integer[] adj = (Integer[]) graph.adjacentNodes(j).toArray();
    	int max = 0;
    	for (Integer i : adj) {
    		if (G[i] > max) {
    			max = G[i];
    		}
    	}
    	if (G[j] < max) {
    		G[j] = max;
    	}
    }
    
    public static void LLPFastComponents (MutableGraph<Integer> graph, int j, int[]parent) {
    	parent[j] = j;
    	Integer[] adj = (Integer[]) graph.adjacentNodes(j).toArray();
    	for (Integer i : adj) {
    		if (parent[i] < parent[parent[i]]) {
    			parent[i] = parent[parent[i]];
    		}
    	}
    	for (Integer i : adj) {
    		if (parent[i] < parent[j]) {
    			parent[i] = parent[j];
    		}
    	}
    	
    }
    
    public static void FastComponents (MutableGraph<Integer> graph) {
    	int[] parent = new int[graph.nodes().size()];
    	int[] vmax = new int[graph.nodes().size()];
    	for (int i = 0; i < parent.length; i++) {
    		parent[i] = i;
    	}
    	while (FastComponentsWhile1(graph, parent)) {
    		Integer[] listNodes = (Integer[]) graph.nodes().toArray();
    		for (Integer v : listNodes) {
    			Integer[] adj = (Integer[]) graph.adjacentNodes(v).toArray();
    			int w = v;
    			for (Integer curAdj : adj) {
    				if (parent[curAdj] > parent[w]) {
    					w = curAdj;
    				}
    			}
    			vmax[v] = parent[w];
    		}
    		for (Integer v : listNodes) {
    			if (v == parent[v]) {
    				ArrayList<Integer> uList = new ArrayList<Integer>();
    				for (int u = 0; u < parent.length; u++) {
    					if (v == parent[u]) {
    						uList.add(u);
    					}
    				}
    				int curMax = uList.get(0);
    				for (int u = 0; u < uList.size(); u++) {
    					if (uList.get(u) > curMax) {
    						curMax = uList.get(u);
    					}
    				}
    				parent[v] = vmax[curMax];
    			}
    			while (FastComponentsWhile2(parent)) {
    				for (Integer w : listNodes) {
    					parent[w] = parent[parent[w]];
    				}
    			}
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
	public static boolean FastComponentsWhile1 (MutableGraph<Integer> graph, int[] parent) {
    	EndpointPair<Integer>[] listEdges = (EndpointPair<Integer>[]) graph.edges().toArray();
    	for (EndpointPair<Integer> edgePair : listEdges) {
    		if (parent[edgePair.nodeU()] < parent[edgePair.nodeV()]) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean FastComponentsWhile2 (int[] parent) {
    	for (int i = 0; i < parent.length; i++) {
    		if (parent[i] != parent[parent[i]]) {
    			return true;
    		}
    	}
    	return false;
    }
}