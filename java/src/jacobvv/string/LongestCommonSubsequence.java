package jacobvv.string;

import java.util.*;

/**
 * 最长公共子序列(LCS)问题
 * 给定一个序列X=<x1,x2,x3,x4,x5...,xm>，从序列中任意去掉0个，或者多个元素，
 * 剩下的序列称之为X的子序列，例如Z=<x1,x4,x5,...,xm>。
 * 最长公共子序列问题，是给定两个序列X，Y，找出另一个序列Z，Z既是序列X的子序列，也是Y的子序列，
 * 并且Z是能找出的最长的序列了，则称Z为序列X，Y的最长公共子序列。
 */
public class LongestCommonSubsequence {

    /**
     * 动态规划
     * 1. 最优解的结构
     * 对于序列Xm=<x1,x2,...,xm>和Yn=<y1,y2,...,yn>，
     * 他们的最大公共序列(LCS)Zi=<z1,z2,...,zi>
     * 此时我们考虑他们的最后一个元素，无非有以下几种情况：
     * a. 如果xm==yn==zi，此时Zi去除最后一个元素Z(i-1)是X(m-1)和Y(n-1)的最大公共子序列。
     * b. 如果xm!=yn，zi!=yn，那么Zi一定是Xm和Y(n-1)的一个LCS
     * 如果xm!=yn，zi!=xm，那么Zi一定是X(m-1)和Yn的一个LCS
     * <p>
     * 2. 递归定义最优解
     * 更一般的情况，我们考虑序列Xi和Yj，c[i,j]是Xi和Yj的一个LCS的长度
     * c[i,j] = 0, (i==0或者j==0)
     * c[i,j] = c[i-1,j-1] + 1, (i,j>0并且xi==yj)
     * c[i,j] = max(c[i-1,j], c[i,j-1]), (i,j>0并且xi!=yj)
     * <p>
     * 时间复杂度O(mn)，空间复杂度O(mn)
     * 如果只需要LCS的长度，则空间复杂度可以优化到O(min(n,m))
     *
     * @param x 字符串A
     * @param y 字符串B
     * @return 字符串A和字符串B的最长公共子序列，无则返回空字符串
     */
    public String findByDp(String x, String y) {
        if (x == null || x.isEmpty() || y == null || y.isEmpty()) {
            return "";
        }
        // 根据最优解的构成和递归定义，自底向上计算最优解的值
        int[][] c = calc(x, y);
        // 根据记录得出LCS的解
        int i = x.length();
        int j = y.length();
        StringBuilder lcs = new StringBuilder();
        while (i > 0 && j > 0) {
            int len = c[i][j];
            if (c[i - 1][j] == len) {
                i--;
            } else if (c[i][j - 1] == len) {
                j--;
            } else {
                lcs.insert(0, x.charAt(i - 1));
                i--;
                j--;
            }
        }
        return lcs.toString();
    }

    /**
     * 动态规划
     * 根据计算的最优解值的记录信息，构建所有的LCS
     * 这里采用递归法构建所有的LCS
     *
     * @param x 字符串A
     * @param y 字符串B
     * @return 字符串A和字符串B的全部最长公共子序列，无则返回空集
     */
    public List<String> findAllByDpRecursion(String x, String y) {
        if (x == null || x.isEmpty() || y == null || y.isEmpty()) {
            return new ArrayList<>();
        }
        // 根据最优解的构成和递归定义，自底向上计算最优解的值
        int[][] c = calc(x, y);
        // 需要根据记录构造出所有的LCS解，参考树的路径问题，采用递归方式构造
        Set<String> result = new HashSet<>();
        buildAllByRecursion(c, x.length(), y.length(), x, y, "", result);
        return new ArrayList<>(result);
    }

    /**
     * 动态规划
     * 根据计算的最优解值的记录信息，构建所有的LCS
     * 这里采用深度优先搜索构建所有的LCS
     *
     * @param x 字符串A
     * @param y 字符串B
     * @return 字符串A和字符串B的全部最长公共子序列，无则返回空集
     */
    public List<String> findAllByDpStack(String x, String y) {
        if (x == null || x.isEmpty() || y == null || y.isEmpty()) {
            return new ArrayList<>();
        }
        // 根据最优解的构成和递归定义，自底向上计算最优解的值
        int[][] c = calc(x, y);
        // 需要根据记录构造出所有的LCS解，参考树的路径问题，采用递归方式构造
        return buildAllByStack(c, x, y);
    }

    /**
     * 根据最优解的构成和递归定义，自底向上计算最优解的值
     *
     * @param x 字符串A
     * @param y 字符串B
     * @return 递归定义中对于所有匹配的字符计算最优解的值
     */
    private int[][] calc(String x, String y) {
        int lenX = x.length();
        int lenY = y.length();
        int[][] c = new int[lenX + 1][lenY + 1];
        // 为了方便边界情况的处理，这里c中i=0或者j=0的情况其值均预设为0
        // 当i>0，j>0时，开始对应字符串的字符。
        // 因为字符串下标从0开始，而c相应下标从1开始，所以需要转换。
        // i的上界为len，而非len-1，字符串取字符应该使用下标i-1，而非i。
        for (int i = 0; i <= lenX; i++) {
            for (int j = 0; j <= lenY; j++) {
                if (i == 0 || j == 0) {
                    c[i][j] = 0;
                } else if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else {
                    c[i][j] = c[i - 1][j] > c[i][j - 1] ? c[i - 1][j] : c[i][j - 1];
                }
            }
        }
        return c;
    }

    /**
     * 根据最优解值的记录，用递归方式构建出所有的LCS
     *
     * @param c      最优解值的记录
     * @param i      x字符串上当前判断的字符位置
     * @param j      y字符串上当前判断的字符位置
     * @param x      给定字符串A
     * @param y      给定字符串B
     * @param lcs    正在构建的其中一个LCS
     * @param result 所有的LCS集合
     */
    private void buildAllByRecursion(int[][] c, int i, int j, String x, String y,
                                     String lcs, Set<String> result) {
        if (c[i][j] == 0) {
            if (lcs.length() > 0) {
                result.add(lcs);
            }
            return;
        }
        int len = c[i][j];
        if (c[i - 1][j] == len) {
            buildAllByRecursion(c, i - 1, j, x, y, lcs, result);
        }
        if (c[i][j - 1] == len) {
            buildAllByRecursion(c, i, j - 1, x, y, lcs, result);
        }
        if (x.charAt(i - 1) == y.charAt(j - 1)) {
            lcs = x.charAt(i - 1) + lcs;
            buildAllByRecursion(c, i - 1, j - 1, x, y, lcs, result);
        }
    }

    /**
     * 根据最优解值的记录，用深度优先搜索构建出所有的LCS
     *
     * @param c 最优解值的记录
     * @param x 给定字符串A
     * @param y 给定字符串B
     */
    private List<String> buildAllByStack(int[][] c, String x, String y) {
        Set<String> result = new HashSet<>();
        Stack<Coordinate> stack = new Stack<>();
        stack.push(new Coordinate(x.length(), y.length()));
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            Coordinate co = stack.pop();
            if (c[co.x][co.y] == 0) {
                if (sb.length() > 0) {
                    result.add(sb.toString());
                }
                if (!stack.isEmpty()) {
                    Coordinate top = stack.peek();
                    sb.delete(0, c[top.x][top.y]);
                }
            } else if (x.charAt(co.x - 1) == y.charAt(co.y - 1)) {
                sb.insert(0, x.charAt(co.x - 1));
                stack.push(new Coordinate(co.x - 1, co.y - 1));
            } else {
                if (c[co.x - 1][co.y] == c[co.x][co.y]) {
                    stack.push(new Coordinate(co.x - 1, co.y));
                }
                if (c[co.x][co.y - 1] == c[co.x][co.y]) {
                    stack.push(new Coordinate(co.x, co.y - 1));
                }
            }
        }
        return new ArrayList<>(result);
    }

    private static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
