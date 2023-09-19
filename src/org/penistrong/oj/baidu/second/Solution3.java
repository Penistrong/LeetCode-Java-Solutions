package org.penistrong.oj.baidu.second;

import java.util.*;

public class Solution3 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        // 每个表的名字需要映射到实际存储表的数据结构上
        // 对每个表的列名都需要一个映射，映射到实际列所在的下标位置上
        Map<String, Integer> tableNames = new HashMap<>();
        List<List<String[]>> tables = new ArrayList<>();
        Map<String, Map<String, Integer>> colMappings = new HashMap<>();    // 存储每个表名，其对应的表里，列名和索引的映射
        for (int t = 0; t < n; t++) {
            String tableName = in.nextLine();
            String[] nums = in.nextLine().split(" ");
            int a = Integer.parseInt(nums[0]), b = Integer.parseInt(nums[1]);
            List<String[]> table = new ArrayList<>();
            String[] colName = in.nextLine().split(" ");
            Map<String, Integer> colMapping = new HashMap<>();
            for (int i = 0; i < colName.length; i++) {
                colMapping.put(colName[i], i);
            }
            for (int record = 1; record <= a; record++) {   // 读入数据行
                table.add(in.nextLine().split(" "));
            }
            tableNames.put(tableName, t);    // 存储表名与表数据在tables里的下标映射
            colMappings.put(tableName, colMapping);
            tables.add(table);
        }
        int Q = Integer.parseInt(in.nextLine());    // q次查询
        for (int q = 0; q < Q; q++) {
            String sql = in.nextLine();
            if (sql.startsWith("select")) {         // 查询语句
                String[] patterns = sql.split(" ");           // pattern[0]="select", pattern[1]="str1,str2,str3"
                String t_name = patterns[patterns.length - 1].substring(0, patterns[patterns.length - 1].length() - 1);      // 表名
                // String[] cols = patterns[1].split(",");
                List<String> cols = new ArrayList<>();
                for (int i = 1; !patterns[i].equals("from"); i++) {
                    if (patterns[i].isEmpty())  // 跳过空白分割
                        continue;
                    cols.addAll(Arrays.asList(patterns[i].split(",")));
                }
                // 需要列名与其索引的映射，放入一个List里，对于每一行，根据映射顺序输出字段
                List<Integer> sequence = new ArrayList<>();
                StringBuilder col_sb = new StringBuilder();
                for (String col : cols) {
                    col_sb.append(col).append(" ");
                    sequence.add(colMappings.get(t_name).get(col));
                }
                // 首先输出表名
                System.out.println(col_sb.toString().trim());
                // 查询对应的表，输出每行需要输出的玩意
                for (String[] datas : tables.get(tableNames.get(t_name))) {
                    StringBuilder sb = new StringBuilder();
                    for (Integer idx : sequence) {
                        sb.append(datas[idx]).append(" ");
                    }
                    System.out.println(sb.toString().trim());
                }
            } else { // Update语句
                String t_name = sql.split("set")[0].split("update")[1].trim();
                String setPattern = sql.split("set")[1].split("where")[0].trim();
                String wherePattern = sql.split("where")[1].trim();
                wherePattern = wherePattern.substring(0, wherePattern.length() - 1);      // 去除最后的分号
                String id = wherePattern.split("=")[0].trim();
                String id_val = wherePattern.split("=")[1].trim();
                List<String[]> table = tables.get(tableNames.get(t_name));
                // 找到主键对应的数据行
                for (int i = 0; i < table.size(); i++) {
                    String[] data = table.get(i);
                    if (!data[0].equals(id_val))
                        continue;
                    // 找到主键行后，设置对应的值
                    String set_col = setPattern.split("=")[0].trim();
                    String set_val = setPattern.split("=")[1].trim();
                    data[colMappings.get(t_name).get(set_col)] = set_val;
                }
            }
        }

    }
}
