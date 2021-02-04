题目大意：
    matrix 里面只有 0 或 1，return 新的matrix，里面更新每个1到0的最短距离
做题思路：
    把所有0对应的坐标加到queue里，来更新每个1到0的最短路径
    level order 的遍历，所以是把0加入queue，先把一步能走的的位置都填入1，走一步都做完的，再走下一层，是两步的
查重方式：
   这题只有 0 和 1，遇到“1”我们就要计算它到0的距离
   但是这个1可以是没被遍历过的；也可以是遍历过，但最短路径就是1
   所以我们利用res[][]来辅助查重：
   如果对应res是0就代表没有遍历过的，可以做，不然已经是被填好的
class Solution {
    private final static int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    // start from 0, and run bfs to find the 1, is just the shortest distance
    public int[][] updateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return null;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] res = new int[row][col];
        // add all '0' into the queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(i * col + j);
                }
            }
        }
        // Use the BFS, the distance starts from 1, when move to next point add 1 to the len 
        int minLen = 1;
        // Use the res[][] == 0 to check it has not be visited yet.
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                int i = cur / col;
                int j = cur % col;
                for (int[] dir: DIRECTIONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (ii >= 0 && ii < row && jj >= 0 && jj < col && matrix[ii][jj] == 1 && res[ii][jj] == 0) {
                        queue.offer(ii * col + jj);
                        res[ii][jj] = minLen;
                    }
                }
            }
            minLen++; // that means we finish this level and move to next 
        }
        return res;
    }
}
