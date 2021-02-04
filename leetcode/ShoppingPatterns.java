//https://aonecode.com/amazon-online-assessment-shopping-patterns

package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

public class ShoppingPatterns {
    public int findTrioScore(int node, int edge, int[] productsFrom, int[] productsTo) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Map<Integer, Set<Integer>> visitedEdge = new HashMap<>();
        initializeGraph(graph, node);
        initializeGraph(visitedEdge, node);

        for (int i = 0; i < productsFrom.length; i++) {
            addEdge(graph, productsFrom[i], productsTo[i]);
        }

        int[] res = { node };

        for (int i = 1; i <= node; i++) {
            if (visitedEdge.get(i).size() == graph.get(i).size()) {
                continue;
            }
            List<Integer> path = new ArrayList<>();
            path.add(i);
            dfs(i, path, graph, visitedEdge, res, node);
        }
        return res[0];
    }

    private void dfs(int curNode, List<Integer> nodes, Map<Integer, Set<Integer>> graph,
            Map<Integer, Set<Integer>> visitedEdge, int[] res, int node) {
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
            if (visitedEdge.get(curNode).contains(neighbor)) {
                continue;
            }
            addEdge(visitedEdge, curNode, neighbor);
            nodes.add(neighbor);
            dfs(neighbor, nodes, graph, visitedEdge, res, node);
            nodes.remove(nodes.size() - 1);
        }
    }

    private int calculateTrioScore(int x, int y, int z, Map<Integer, Set<Integer>> graph) {
        Set<Integer> products = new HashSet<>();
        products.addAll(graph.get(x));
        products.addAll(graph.get(y));
        products.addAll(graph.get(z));
        return products.size() - 3; // minus nodes themselves
    }

    private void addEdge(Map<Integer, Set<Integer>> graph, int from, int to) {
        graph.get(from).add(to);
        graph.get(to).add(from);
    }

    private void initializeGraph(Map<Integer, Set<Integer>> graph, int node) {
        for (int i = 1; i <= node; i++) {
            graph.put(i, new HashSet<>());
        }
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