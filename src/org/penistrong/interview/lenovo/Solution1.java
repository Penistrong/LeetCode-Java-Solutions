package org.penistrong.interview.lenovo;

import java.util.*;

/**
 * LeetCode 2402 Hard - 会议室③
 * 模拟模拟，还是TMD模拟！
 */
public class Solution1 {

    // 利用JDK17提供的record关键字，当作Tuple使用
    private record HoldingMeetingInfo (Integer roomId, Long end) {}

    public int mostBooked(int n, int[][] meetings) {
        // 输入的用例并不是按照开始时间排序的，所以先排序
        Arrays.sort(meetings, Comparator.comparingInt(row -> row[0]));
        // 正在召开会议的优先队列，存入的是{房间编号，结束时间}这个Record
        PriorityQueue<HoldingMeetingInfo> meetingHoldings = new PriorityQueue<>(
                (r1, r2) ->
                !(r1.end.equals(r2.end)) ? Long.compare(r1.end, r2.end) : Integer.compare(r1.roomId, r2.roomId)
        );  // 注意，首先是按照结束时间升序排序，再局部按照编号升序排序
        // 记录空闲会议室的优先队列，默认按照编号升序排序，初始化时加入所有空闲会议室编号
        PriorityQueue<Integer> idleRooms = new PriorityQueue<>();
        for (int i = 0; i < n; i++) idleRooms.offer(i);
        int[] counts = new int[n];          // 标记每个会议室的使用次数
        for (int[] meeting : meetings) {    // 遍历每个召开的会议
            long start = meeting[0], end = meeting[1];
            // 首先判断是否有会议已经结束(结束时间小于当前遍历到的开始时间)
            while (!meetingHoldings.isEmpty() && meetingHoldings.peek().end <= start) {
                idleRooms.offer(meetingHoldings.poll().roomId); // 会议室空闲出来，重新加入空闲队列
            }
            int roomId;
            if (idleRooms.isEmpty()) {                              // 当不存在任何一个空闲会议室时
                HoldingMeetingInfo info = meetingHoldings.poll();   // 弹出**一个**下标最小的前提下最早结束的会议室
                end += info.end - start;                    // 当前遍历到的会议要安排到这个弹出的会议室里，要更新延期后的结束时间
                roomId = info.roomId;                       // 复用房间号
            } else {
                roomId = idleRooms.poll();              // 有空闲会议室，就弹出一个安排给当前会议使用
            }
            counts[roomId]++;                           // 记录使用次数(不必等到会议结束再记录)
            meetingHoldings.offer(new HoldingMeetingInfo(roomId, end)); // 记录该会议室主持的会议的结束时间
        }
        // 寻找召开次数最多尔后编号最小的房间，记录编号
        int ans = 0;
        for (int k = 0; k < n; k++) if(counts[k] > counts[ans]) ans = k;
        return ans;
    }

    public static void main(String[] args) {
        int n = 4;
        int[][] meetings = new int[][] {{10, 20}, {2, 10}, {3, 5}, {4, 9}, {6, 8}};
        System.out.println(new Solution1().mostBooked(n, meetings));
    }
}
