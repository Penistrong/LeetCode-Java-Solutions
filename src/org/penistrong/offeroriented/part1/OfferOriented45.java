package org.penistrong.offeroriented.part1;

import java.util.*;

public class OfferOriented45 {

    public String minNumber(int[] nums) {
        // 字符串拼接最小，由于总长度不变，肯定是最高位最小的数字排在前导是最优选择
        // 对于前导0,可以不去除
        List<String> numStrs = new ArrayList<>(Arrays.stream(nums).boxed().map(String::valueOf).toList());
        // 排序时，只要对两两拼接后的字符串进行比较
        // x+y < y+x, 则认为x "小于" y
        // y+x > x+y, 则认为x "大于" y
        numStrs.sort((x, y) -> (x + y).compareTo(y + x));
        // 对该排序可以执行数学证明，证明规则的自反性、对称性、传递性
        StringBuilder sb = new StringBuilder();
        for (String num : numStrs) {
            sb.append(num);
        }
        return sb.toString();
    }
}
