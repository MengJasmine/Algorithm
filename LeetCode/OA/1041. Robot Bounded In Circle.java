On an infinite plane, a robot initially stands at (0, 0) and faces north. The robot can receive one of three instructions:

"G": go straight 1 unit;
"L": turn 90 degrees to the left;
"R": turn 90 degrees to the right.
The robot performs the instructions given in order, and repeats them forever.

Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.

所以有环的两种情形：
1. 机器人一直在原点，都没有动
2. 初始的朝向和最后走完所有instructions的朝向不同
   这样，这个instruction循环多少次之后一定会出环
   
顺时针四个放下：上、右、下、左
private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

dir 表示自己的朝向：
if (i == 'L') { // 向左转，就是当前位置再加3（这个地方要再研究一下）
    dir = (dir + 3) % 4;
} else if (i == 'R') {
    dir = (dir + 1) % 4;
} else {
    x += DIRECTIONS[dir][0];
    y += DIRECTIONS[dir][1];
}

class Solution {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};// 上，右，下，左
    public boolean isRobotBounded(String instructions) {
        if (instructions == null || instructions.length() == 0) return false;
        int x = 0, y = 0;
        int dir = 0;
        for (char i : instructions.toCharArray()) {
            if (i == 'L') {
                dir = (dir + 3) % 4;
            } else if (i == 'R') {
                dir = (dir + 1) % 4;
            } else {
                x += DIRECTIONS[dir][0];
                y += DIRECTIONS[dir][1];
            }
        }
        return ((x == 0 && y == 0) || (dir != 0));
    }
}
