package org.penistrong.leetcode.search;

public class LeetCode79 {
    private static final int[] direction = {-1, 0, 1, 0, -1};

    public boolean exist(char[][] board, String word) {
        // 搜索word是否在board中连续存在
        // 只要搜到一个就立马返回true, 否则返回false
        // 从每一个位置开始搜，按序找到完全匹配的下一个单词字
        // 设置一个visited标记当前单元格是否被访问过
        if (board == null || board.length == 0 ||
                board[0].length == 0 || word.length() == 0)
            return false;
        int row = board.length, col = board[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0;j < col; j++) {
                boolean[][] visited = new boolean[row][col];
                if (dfs(board, i, j, word, 0, visited))
                    return true;
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, int i, int j, String word, int pos, boolean[][] visited) {
        if (visited[i][j] || board[i][j] != word.charAt(pos))
            return false;
        if (pos == word.length() - 1)
            return true;
        visited[i][j] = true;
        // 搜索4个方向上的
        int row = board.length, col = board[0].length;
        int x, y;
        boolean flag = false;
        for (int k = 0; k < 4; k++) {
            x = i + direction[k];
            y = j + direction[k+1];
            if (x >= 0 && x < row && y >= 0 && y < col) {
                flag = flag || dfs(board, x, y, word, pos + 1, visited);
            }
        }
        visited[i][j] = false;  // 当前搜索位置回溯
        return flag;
    }
}
