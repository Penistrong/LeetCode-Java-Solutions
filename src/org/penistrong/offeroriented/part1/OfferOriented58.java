package org.penistrong.offeroriented.part1;

import java.util.Scanner;

public class OfferOriented58 {
    public String reverseWords(String s) {
        // 分割后可能会出现 "" 这样的串
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            if (words[i].equals("")) continue;
            sb.append(words[i]);
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(new OfferOriented58().reverseWords(in.nextLine()));
    }
}
