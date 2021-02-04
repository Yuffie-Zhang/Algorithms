//https://aonecode.com/amazon-online-assessment-shopping-patterns

package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

public class ShoppingPatterns {
    public int findTrioScore(int node, int edge, int[] productsFrom, int[] productsTo) {
        Map<Integer, Set<Integer>> graph = new HashMap<>(); // store node and all its larger linked nodes
        Map<Integer, Set<Integer>> oppositegraph = new HashMap<>();
        for (int i = 0; i < productsFrom.length; i++) {
            if (productsFrom[i] > productsTo[i]) {
                swap(productsFrom[i], productsTo[i]);
            }
            addEdge(graph, productsFrom[i], productsTo[i]);
            addEdge(oppositegraph, productsTo[i], productsFrom[i]);
        }

        int[] res = { node };
        for (int i = 1; i <= node; i++) {
            List<Integer> nodes = new ArrayList<>();
            nodes.add(i);
            dfs(graph.get(i), i, nodes, graph, oppositegraph, res);
        }
        return res[0];
    }

    private void dfs(Set<Integer> baseNeighbors, int curNode, List<Integer> nodes, Map<Integer, Set<Integer>> graph,
            Map<Integer, Set<Integer>> oppositegraph, int[] res) {
        if (nodes.size() == 3) {
            if (baseNeighbors.contains(nodes.get(2))) {
                int score = calculateTrioScore(nodes, graph, oppositegraph);
                res[0] = Math.min(res[0], score);
            }
            return;
        }
        Set<Integer> neighbors = graph.get(curNode);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                nodes.add(neighbor);
                dfs(baseNeighbors, neighbor, nodes, graph, oppositegraph, res);
                nodes.remove(nodes.size() - 1);
            }
        }

    }

    private int calculateTrioScore(List<Integer> nodes, Map<Integer, Set<Integer>> graph,
            Map<Integer, Set<Integer>> oppositegraph) {
        Set<Integer> products = new HashSet<>();
        for (int node : nodes) {
            products.addAll(graph.get(node));
            products.addAll(oppositegraph.get(node));
        }
        return products.size() - 3; // minus nodes themselves
    }

    private void swap(int large, int small) {
        int tmp = large;
        large = small;
        small = tmp;
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