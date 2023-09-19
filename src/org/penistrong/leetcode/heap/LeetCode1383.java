package org.penistrong.leetcode.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class LeetCode1383 {

    class Staff {
        public int speed;
        public int efficiency;

        public Staff (int s, int e) {
            this.speed = s;
            this.efficiency = e;
        }
    }

    private final int MOD = (int) 1e9 + 7;

    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        List<Staff> staffs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            staffs.add(new Staff(speed[i], efficiency[i]));
        }
        // 由于是速度和乘以效率最小值，两个变量都能优化，因此选择固定效率最小值e_min这个变量，然后选出所有比这个e_min大的工程师
        Collections.sort(staffs, (s1, s2) -> s2.efficiency - s1.efficiency);    // 按照效率从高到低降序排序，方便固定最小值
        // 对于剩下的所有工程师，想要找到速度和最大的k-1位(第1位为固定了效率最大值的"锚定"工程师)
        // 这个问题可以用小根堆优化，碰到比堆顶大的元素，可以pop掉堆顶，维持堆内元素始终为前k-1个最大元素
        PriorityQueue<Staff> team = new PriorityQueue<>((s1, s2) -> s1.speed - s2.speed);
        long ans = 0, sum = 0;
        for (int i = 0; i < n; i++) {
            // 选择staffs[i]作为锚定工程师，后续的所有工程师其效率都比该锚定工程师低
            // 所以之前遍历到的工程师组成的团队就满足要求
            Staff staff = staffs.get(i);
            int e_min = staff.efficiency;
            long s_sum = sum + staff.speed;
            // 判断当前的团队表现值是否更高(因为是要找到更大的表现值，所以在遍历过程中可以不断更新)
            ans = Math.max(ans, e_min * s_sum);
            team.offer(staff);
            sum += staff.speed;
            if (team.size() == k) {
                sum -= team.poll().speed;
            }
        }
        return (int) (ans % MOD);
    }
}
