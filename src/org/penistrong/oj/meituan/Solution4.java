package org.penistrong.oj.meituan;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 美团9.2第四批笔试(技术) 第4题
 * <p>
 * 小美有一个长度为n的数组，她希望删除k个元素，使得剩余元素两两之间互为倍数关系，你能告诉小美有多少种删除方案吗?
 * 由于答案过大，请对1e9+7取模
 * <p>
 * 输入描述:
 * 第一行输入两个整数n,k(1 <= k <= n <= 1e3)表示数组长度n和需要删除的元素个数k
 * 第二行输入n个整数表示数组a(1 <= a[i] <= 1e9)
 * <p>
 * 输出描述:
 * 输出一个整数表示答案
 */
public class Solution4 {

    public static void main(String[] args) {
        new Solution4().solve();
    }

    public void solve() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(),k = in.nextInt();
        int[] nums = new int[n];
        for(int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
        }
        // 将数组从小到大排序，方便后续依次判断倍数关系
        Arrays.sort(nums);
        int mod = (int) 1e9 + 7;
        int res = 0;
        // 二维DP: 第一维表示最先删除的元素位置，第二维表示删除k个元素后 剩余元素两两互为倍数的方案数
        int[][] dp = new int[n][n - k];
        for(int i = n - 1; i >= 0; i--){
            dp[i][0] = 1;
            for(int j = i + 1; j < n; j++){
                if(nums[j] % nums[i] == 0) {
                    for(int u = 1; u < n - k; u++){
                        dp[i][u] = (dp[i][u] + dp[j][u - 1]) % mod;
                    }
                }
            }
            res = (res + dp[i][n - k - 1]) % mod;
        }
        System.out.println(res);
    }
}
