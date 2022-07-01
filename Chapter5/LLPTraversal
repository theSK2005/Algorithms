import com.google.common.graph.Graph;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.GraphBuilder;
class Graph {
    public static int[] LLPTraversal (MutableGraph<Integer> graph, int j) {
        int[] G = new int[graph.nodes().size()];
        int[] pre = graph.predecessors(j).toArray();
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
    }
}
