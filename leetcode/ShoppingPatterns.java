//https://aonecode.com/amazon-online-assessment-shopping-patterns

package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

public class ShoppingPatterns {
    public int findTrioScore(int node, int edge, int[] productsFrom, int[] productsTo) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 1; i <= node; i++) {
            graph.put(i, new HashSet<>());
        }
        for (int i = 0; i < productsFrom.length; i++) {
            addEdge(graph, productsFrom[i], productsTo[i]);
            addEdge(graph, productsTo[i], productsFrom[i]);
        }

        int[] res = { node };
        HashSet<Integer> visitedEdge = new HashSet<>();
        for (int i = 1; i <= node; i++) {
            if (visitedEdge.size() == edge) {
                break;
            }
            List<Integer> path = new ArrayList<>();
            path.add(i);
            dfs(i, path, graph, visitedEdge, res, node);
        }
        return res[0];
    }

    private void dfs(int curNode, List<Integer> nodes, Map<Integer, Set<Integer>> graph, HashSet<Integer> visitedEdge,
            int[] res, int node) {
        if (nodes.size() > 3) {
            int lastIndex = nodes.size() - 1;
            if (nodes.get(lastIndex) == nodes.get(lastIndex - 3)) {
                int score = calculateTrioScore(nodes.get(lastIndex), nodes.get(lastIndex - 1), nodes.get(lastIndex - 2),
                        graph);
                res[0] = Math.min(res[0], score);
                return;
            }
        }
        for (int neighbor : graph.get(curNode)) {
            int edgeVal = calculateEdgeValue(curNode, neighbor, node);
            if (visitedEdge.contains(edgeVal)) {
                continue;
            }
            visitedEdge.add(edgeVal);
            nodes.add(neighbor);
            dfs(neighbor, nodes, graph, visitedEdge, res, node);
            nodes.remove(nodes.size() - 1);
        }
    }

    private int calculateEdgeValue(int x, int y, int total) {
        int small = x < y ? x : y;
        int large = x < y ? y : x;
        return small * total + large;
    }

    private int calculateTrioScore(int x, int y, int z, Map<Integer, Set<Integer>> graph) {
        Set<Integer> products = new HashSet<>();
        products.addAll(graph.get(x));
        products.addAll(graph.get(y));
        products.addAll(graph.get(z));
        return products.size() - 3; // minus nodes themselves
    }

    private void addEdge(Map<Integer, Set<Integer>> graph, int from, int to) {
        if (!graph.containsKey(from)) {
            graph.put(from, new HashSet<>());
        }
        graph.get(from).add(to);
    }

    @Test
    public void test() {
        int products_nodes = 6;
        int products_edges = 6;
        int[] products_from = { 1, 2, 2, 3, 4, 5 };
        int[] products_to = { 2, 4, 5, 5, 5, 6 };
        assertEquals(3, findTrioScore(products_nodes, products_edges, products_from, products_to));
    }
}