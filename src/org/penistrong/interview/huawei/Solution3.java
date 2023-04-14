package org.penistrong.interview.huawei;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution3 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(), M = in.nextInt();
        int[] thres_level = new int[M];
        for (int i = 0; i < M; i++) {
            thres_level[i] = in.nextInt();
        }
        Map<Integer, int[]> canReach = new HashMap<>();
        for (int i = 0; i < M; i++) {
            int k = in.nextInt();
            if (k == 0) continue;
            int[] reachs = new int[k];
            for (int j = 0; j < k; j++)
                reachs[j] = in.nextInt();
            canReach.put(i, reachs);
        }

        // 存储关键阀门编号
        List<Integer> imporant = new ArrayList<>();
        // 逆序向上查找
        for(int level = N - 2; level >= 0; level--) {
            // 当前层为level，找到当前层所有阀门
            List<Integer> cur_level_doors = new ArrayList<>();
            for (int i = 0; i < M; i++) {
                if (thres_level[i] == level)
                    cur_level_doors.add(i);
            }
            // 找到每个阀门的可达阀门
        }
    }
}
