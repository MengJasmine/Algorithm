
```java
class Solution {
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return;
        int row = board.length;
        int col = board[0].length;
        // Convert two-dimensional coordinates to one-dimensional coordinates 
        // And save it into the queue
        Queue<Integer> queue = new LinkedList<>(); 
        // Traverse the border of the board and add 'O' into the queue as the start point
        // Change these points to 'Y'
        
        for (int i = 0; i < row; i++) { // left + right
            // the left border, which is the first col
            if (board[i][0] == 'O') {
                queue.offer(i * col + 0);
                board[i][0] = 'Y';
            }
            // the right border, which is the last col
            if (board[i][col - 1] == 'O') {
                queue.offer(i * col + (col - 1));
                board[i][col - 1] = 'Y';
            }
        }
        // Beacuse the points on the corner have already change to 'Y'
        // We will not add the same point into queue twice.
        for (int j = 0; j < col; j++) { // top + bottom
            // the top border, which is the first row
            if (board[0][j] == 'O') {
                queue.offer(0 * col + j);
                board[0][j] = 'Y';
            }
            // the bottom border, which is the last row
            if (board[row - 1][j] == 'O') {
                queue.offer((row - 1) * col + j);
                board[row - 1][j] = 'Y';
            }
        }
        // By BFS:
        // Start from these border 'O', traverse the matrix by 4 directions
        // 'O' is what we want
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int i = cur / col;
            int j = cur % col;
            for (int[] dir: DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                if (ii >= 0 && ii < row && jj >= 0 && jj < col && board[ii][jj] == 'O') {
                    queue.offer(ii * col + jj);
                    board[ii][jj] = 'Y';
                }
            }
        }
        // Traverse the whole matrix, 
        // Flip all the O, which is surrounded by X and cannot connect to the border
        // Flip all the Y, which is connect the border, back to O
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
            }
        }
        // Because, I just tarverse one point once, there is not overlap.
    }
}
```
