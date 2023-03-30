package org.penistrong.interview.pinduoduo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class PinDuoDuoSolution1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();   // m为宝石种类数
        in.nextLine();    // 去掉行尾换行符
        // 数字范围很大 最大1e6
        // 用ArrayList顺序存储序列
        // 第一个字符为b, 用0代替; m用1代替
        Integer[][] missions = new Integer[n][3];
        for (int i = 0; i < n; i++) {
            String mission_info = in.nextLine();
            String[] info = mission_info.split(" ");
            missions[i][0] = info[0].equals("b") ? 0 : 1;
            missions[i][1] = Integer.valueOf(info[1]);
            if (missions[i][0] == 1) missions[i][2] = Integer.valueOf(info[2]);
        }
        // 获得宝石是有顺序的
        // 正序遍历获得宝石情况，逆序遍历关底商人情况
        // 由于最多只能携带一颗宝石，所以需要关注最近一关的商人情况，如果前面有
        // 每个被商人关分开的boss关区间，在这个商人关barrier里找到最值钱的商人
        int i = 0;
        int coins = 0;
        HashSet<Integer> last_remain_gems = new HashSet<>();
        while (i < n) {
            // 找到下一个关底商人区间的起始点
            int m_start = i;
            while (m_start < n && missions[m_start][0] == 0) {
                m_start++;
            }
            // 找到关底商人区间的终止点
            // 注意循环结束后的m_end为下一个正常boss关的下标
            int m_end = m_start;
            while (m_end < n && missions[m_end][0] == 1) {
                m_end++;
            }
            // 对每个处于当前boss关区域的宝石, 首先去重，因为只能捡一个，相同宝石掉落就去重
            HashSet<Integer> gem_set = new HashSet<>(last_remain_gems);   // 上一次没消耗的宝石备选也加入
            for (int j = i; j < m_start; j++) {
                gem_set.add(missions[j][1]);
            }
            // 对每颗宝石找到关底商人区间的最大价值，并更新其最大值
            HashMap<Integer, Integer> gem_max_values = new HashMap<>();
            for (Integer gem : gem_set) {
                int gem_cur_max = 0;
                for (int k = m_start; k < m_end; k++) {
                    // 宝石种类匹配的话，更新最大值
                    if (gem.equals(missions[k][1])) {
                        gem_cur_max = Integer.max(gem_cur_max, missions[k][2]);
                    }
                }
                // 加入gem_max_values中
                gem_max_values.put(gem,
                        Integer.max(gem_max_values.getOrDefault(gem, 0), gem_cur_max));
            }
            // 比较最大价值
            int maxVal = 0;
            for (Map.Entry<Integer, Integer> gem_val_pair: gem_max_values.entrySet()) {
                maxVal = Integer.max(maxVal, gem_val_pair.getValue());
            }

            // 如果关底商人不满足条件，那此时多多还是携带了一个宝石，所以需要传给下一趟循环
            // 将当前可能的宝石全部加入
            if (maxVal == 0) {
                last_remain_gems.addAll(gem_set);
            } else {
                coins += maxVal;            // 更新coin数
                last_remain_gems.clear();   // 清空备选
            }
            // 下一轮寻找，更新i
            i = m_end;
        }
        System.out.println(coins);
    }
}
