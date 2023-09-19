package org.penistrong.oj.baidu.first;

import java.util.Scanner;

/**
 * 小红的方案谈判
 * 小红带的团队共同商量出一个解决方案，每一版方案出来后，小红都需要跟领导商量方案是否可行
 * 但是领导每次都只会否定方案，要求打回重做
 * 小红团队的成员们就会感到很愤怒，每次修改都会使负责某一版方案的成员其愤怒值增加1点，每位成员有其不同的愤怒阈值
 * 如果某位成员因为第i版方案被驳回导致其愤怒值达到阈值，那么他就会逼宫领导，强制接收上一版的第i-1版方案
 * 请问小红最多能够修改多少次方案
 *
 * 输入描述:
 * 第一行为两个整数n和m，表示小红团队的成员数量和方案版数
 * 第二行为n个整数，表示每位成员的愤怒阈值
 * 接下来的m行，每行2个整数l和r，表示负责第i版方案的成员区间为[l, r]
 *
 * 输出描述:
 * 输出最大可行的方案版本号
 */
public class Solution3 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();   //成员数量

    }
}
