Given the following 5x5 matrix:

  Pacific ~   ~   ~   ~   ~ 
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * Atlantic

Return: [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]
即能流到Pacific，又能流到Atlantic的点coordinates
Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

从 Pacific 出发， 看能流到哪些点；
从Atlantic出发，看能流到哪些点 

 -->  mn + mn

Pacific 为 true ---》那么就要去找 Atlantic

Atlantic为 true ---》那么就要去找 Pacific

所以在 function signature 部分可以传入 self[][] 和 other[][]
永远是 fill self， 去 check other
出queue的时候去做检查，只用写一次；
不然进queue的时候检查，在helper函数外和函数里都要写

class Solution {
    private final static int[][] DIRECTIONS = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return res;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        Queue<Integer> queue = new LinkedList<>();
        boolean[][] pacific = new boolean[row][col];
        boolean[][] atlantic = new boolean[row][col];
        addPacificPoints(matrix, queue, pacific); // left & top
        bfs(queue, matrix, pacific, atlantic, res);
        addAtlanticPoints(matrix, queue, atlantic);
        bfs(queue, matrix, atlantic, pacific, res);
        return res;
    }
    private void bfs(Queue<Integer> queue, int[][] matrix, 
                     boolean[][] self, boolean[][] other, List<List<Integer>> res) {
        int row = matrix.length;
        int col = matrix[0].length;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int i = cur / col;
            int j = cur % col;
            if (other[i][j]) {
                List<Integer> tmp = new ArrayList<>();
                tmp.add(i);
                tmp.add(j);
                res.add(tmp);
            }
            for (int[] dir: DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                while (ii >= 0 && ii < row && jj >= 0 && jj < col && matrix[ii][jj] >= matrix[i][j] && !self[ii][jj]) {
                    queue.offer(ii * col + jj);
                    self[ii][jj] = true;
                }
            }
        }
    }
    private void addPacificPoints(int[][] matrix, Queue<Integer> queue, boolean[][] pacific) {
        int row = matrix.length;
        int col = matrix[0].length;
        // left: [i][0] -> i * col + 0 -> 第一列
        for (int i = 0; i < row; i++) {
            queue.offer(i * col);
            pacific[i][0] = true;
        }
        // top: [0][j] -> 0 * col + j (j 从1开始，不能重复) -> 第一行 - [0][0]
        for (int j = 1; j < col; j++) {
            queue.offer(j);
            pacific[0][j] = true;
        }
    }
    private void addAtlanticPoints(int[][] matrix, Queue<Integer> queue, boolean[][] atlantic) {
        int row = matrix.length;
        int col = matrix[0].length;
        // right: [i][col - 1] -> i * (col) + col - 1 -> 最后一列 - [0][col - 1]
        for (int i = 0; i < row; i++) {
            queue.offer(i * col + (col - 1));
            atlantic[i][col - 1] = true;
        }
        // bottom: [row - 1][j] -> (row - 1) * col + j (j 从1开始，不能重复)
        for (int j = 0; j < col - 1; j++) {
            queue.offer((row - 1) * col + j);
            atlantic[row - 1][j] = true;
        }
    }
}
