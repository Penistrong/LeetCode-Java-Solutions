package org.penistrong.offeroriented.part2.array;

/**
 * 剑指Offer2-013 二维子矩阵的和
 * 二维前缀和，演变为积分图，LeetCode 304与该题一致
 */
public class OfferOriented_2_013 {
    /**
     * Your NumMatrix object will be instantiated and called as such:
     * NumMatrix obj = new NumMatrix(matrix);
     * int param_1 = obj.sumRegion(row1,col1,row2,col2);
     */

    public static class NumMatrix {
        private final long[][] prefix_matrix;

        // 二维前缀和(积分图), 直接用二维数组表示右下角为(i,j)坐标时的(0,0)~(i,j)矩形元素和
        // 计算积分图时，根据左边和上边的前缀和，并减去重叠区域(i-1,j-1)所代表的左上角前缀和即可
        public NumMatrix(int[][] matrix) {
            int row = matrix.length;
            int col = matrix[0].length;
            prefix_matrix = new long[row + 1][col + 1];
            for (int i = 1; i <= row; i++) {
                for (int j = 1; j <= col; j++) {
                    prefix_matrix[i][j] = matrix[i-1][j-1]              // 新增元素
                            + prefix_matrix[i-1][j]     // 上一行
                            + prefix_matrix[i][j-1]     // 左一列
                            - prefix_matrix[i-1][j-1];  // 左上角重叠区域
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            // 根据前缀和矩阵计算即可, 注意各个下标的对称关系
            long region = prefix_matrix[row2 + 1][col2 + 1]
                    - prefix_matrix[row1][col2 + 1] // 同列的上一行
                    - prefix_matrix[row2 + 1][col1] // 同行的左一列
                    + prefix_matrix[row1][col1];    // 前两个减去区域的重叠区域(以nums[row1-1][col1-1]为右下角的矩阵)
            return (int)region;
        }
    }
}
