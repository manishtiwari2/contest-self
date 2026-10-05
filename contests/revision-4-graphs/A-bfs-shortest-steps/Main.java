import java.io.*;
import java.util.*;

public class Main {

    // n nodes (0 to n-1), edges[i] = {a, b} is a two-way edge.
    static List<List<Integer>> buildGraph(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());              // an empty friend list for every node
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);                 // remove this line for one-way edges
        }
        return graph;
    }

    // Return dist[v] = the fewest edges from start to v, or -1 if v can't be reached.
    static int[] bfs(List<List<Integer>> graph, int start) {

        // Write your code here

        return new int[graph.size()];
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(st.nextToken()) - 1;

        int[][] edges = new int[m][2];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            edges[i][0] = Integer.parseInt(st.nextToken()) - 1;
            edges[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        List<List<Integer>> graph = buildGraph(n, edges);
        int[] dist = bfs(graph, start);

        StringBuilder sb = new StringBuilder();

        for (int d : dist) {
            sb.append(d).append(" ");
        }
        System.out.println(sb);
    }
}
