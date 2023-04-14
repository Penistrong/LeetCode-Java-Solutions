package org.penistrong.interview.huawei;

import java.util.*;

public class Solution2 {

    static boolean[] visited;
    static int[] node_max_food;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();   // N个方块
        int[] food = new int[N];    // 保存对应索引下标编号的方块食物数量
        Map<Integer, Integer> node_parent_pairs = new HashMap<>();
        int id, parent_id;
        for (int i = 0; i < N; i++) {
            id = in.nextInt();
            parent_id = in.nextInt();
            node_parent_pairs.put(id, parent_id);
            food[id] = in.nextInt();
        }
        visited = new boolean[N];
        // 开始DFS搜索每个方块，反向查找父节点，直到搜索到parent=-1的节点，直到-1退出
        int max_food = Integer.MIN_VALUE;
        node_max_food = new int[N];
        Arrays.fill(node_max_food, Integer.MIN_VALUE);
        for (Map.Entry<Integer, Integer> node_parent_pair: node_parent_pairs.entrySet()) {
            PriorityQueue<Integer> val_queue = new PriorityQueue<>((a, b) -> b - a);
            dfs(node_parent_pair.getKey(),
                node_parent_pair.getValue(),
                0,
                food,
                node_parent_pairs,
                val_queue);
            // System.out.println(" cur_path_max = " + (val_queue.isEmpty()? 0 : val_queue.peek()));
            if (!val_queue.isEmpty())
                node_max_food[node_parent_pair.getKey()] = val_queue.peek();

            max_food = Integer.max(max_food, node_max_food[node_parent_pair.getKey()]);
        }
        System.out.println(max_food);
    }

    // 用降序优先队列保存一次搜索路径上的最大食物值
    // 要剪枝，如果发现某个节点之前访问过，那么只要读取当时得到的最大食物值加上本次最大食物值即可
    private static void dfs(int id, int parent_id, int cur_val, int[] food, Map<Integer, Integer> node_parent_pairs, PriorityQueue<Integer> val_queue) {
        if (visited[id]) { // 如果方格访问过，读取那一次DFS中遍历过的最大食物值
            cur_val += node_max_food[id];
            val_queue.offer(cur_val);
            return;
        }
        cur_val = cur_val + food[id];
        val_queue.offer(cur_val);
        visited[id] = true;
        // System.out.print(" [" + id + ", " + parent_id + "] <-");
        if (parent_id == -1) {  // 没有格子能到达了
            return;
        }
        // 找到父方格
        int grandpa_id = node_parent_pairs.get(parent_id);
        dfs(parent_id, grandpa_id, cur_val, food, node_parent_pairs, val_queue);
    }
}
