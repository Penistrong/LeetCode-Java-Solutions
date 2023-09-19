package org.penistrong.leetcode.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * LeetCode 502 IPO
 * 大根堆 + 贪心
 */
public class LeetCode502 {
    class Project {
        public int profit;
        public int capital;

        public Project (int profit, int capital) {
            this.profit = profit;
            this.capital = capital;
        }
    }

    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int size = profits.length;
        List<Project> projects = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            projects.add(new Project(profits[i], capital[i]));
        }
        // 按照最少资本要求升序排序
        Collections.sort(projects, (p1, p2) -> p1.capital - p2.capital);
        PriorityQueue<Project> runnableProjects = new PriorityQueue<>((p1, p2) -> p2.profit - p1.profit);
        int myCapital = w, pIdx = 0;
        for (int i = 0; i < k; i++) {   // 最多进行k次项目
            // 将那些最少资本要求小于目前持有资本的项目加入可执行项目列表里，直到某个项目的资本要求大于当前持有资本
            while (pIdx < size && projects.get(pIdx).capital <= myCapital) {
                runnableProjects.offer(projects.get(pIdx++));
            }
            if (!runnableProjects.isEmpty()) {
                myCapital += runnableProjects.poll().profit;
            } else {
                break;
            }
        }
        return myCapital;
    }
}
