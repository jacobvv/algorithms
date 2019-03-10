package string;

import java.util.*;

/**
 * 最长公共子串(LCSub)问题
 * 与最长公共子序列(LCS)问题非常相似，子序列在原序列中必须连续，则称为子串
 */
public class LongestCommonSubstring {

    /**
     * 对于任意字符串X和Y，其最大公共子串一定是字符串X的某个前缀和字符串Y的某个前缀的共同后缀
     * 例如对于X=ABCDE，Y=NABCMM，最大公共子串Z=ABC一定是X前缀ABC和Y前缀NABC的共同后缀ABC。
     * <p>
     * 对于任意字符串字符Xi和Yj（下标从0开始），以Xi和Yj结尾的子串长度为c[i,j]，那么
     * c[i,j] = 0, (i<0或者j<0)
     * c[i,j] = 0, (i,j>=0并且xi!=yj)
     * c[i,j] = c[i-1,j-1] + 1, (i,j>=0并且xi==yj)
     * 要找出字符串X和字符串Y的最长公共子串，那么计算出所有X和Y的前缀字符串的c[i,j]最优解的值，
     * 即可在c记录中找出最长的公共子串
     * <p>
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param x 字符串A
     * @param y 字符串B
     * @return 字符串A和字符串B的最长公共子串集合，无则返回空集合
     */
    public List<String> findAllByDp(String x, String y) {
        ArrayList<String> result = new ArrayList<>();
        if (x == null || x.isEmpty() || y == null || y.isEmpty()) {
            return result;
        }
        // 根据最优解的递归定义，自底向上计算最优解的值，并找出最长公共子串
        Set<String> resultSet = new HashSet<>();
        int maxLen = 0;
        int lenX = x.length();
        int lenY = y.length();
        int[][] c = new int[lenX][lenY];
        for (int i = 0; i < lenX; i++) {
            for (int j = 0; j < lenY; j++) {
                if (x.charAt(i) == y.charAt(j)) {
                    if (i == 0 || j == 0) {
                        c[i][j] = 1;
                    } else {
                        c[i][j] = c[i - 1][j - 1] + 1;
                    }
                    if (c[i][j] >= maxLen) {
                        if (c[i][j] != maxLen) {
                            resultSet.clear();
                            maxLen = c[i][j];
                        }
                        String lcsub = x.substring(i - maxLen + 1, i + 1);
                        if (lcsub.length() > 0) {
                            resultSet.add(lcsub);
                        }
                    }
                } else {
                    c[i][j] = 0;
                }
            }
        }
        result.addAll(resultSet);
        return result;
    }

    /**
     * findAll的优化版本1
     * 因为c[i,j]的计算只依赖于c[i-1,j-1]，那么c只需要保存2行进行滚动即可。
     * 这样空间复杂度可以进一步减小
     * <p>
     * 时间复杂度O(mn)，空间复杂度O(min(m,n))
     *
     * @param x 字符串A
     * @param y 字符串B
     * @return 字符串A和字符串B的最长公共子串集合，无则返回空集合
     */
    public List<String> findAllByDpUpdate1(String x, String y) {
        ArrayList<String> result = new ArrayList<>();
        if (x == null || x.isEmpty() || y == null || y.isEmpty()) {
            return result;
        }
        // 根据最优解的递归定义，自底向上计算最优解的值，并找出最长公共子串
        Set<String> resultSet = new HashSet<>();
        int maxLen = 0;
        String shorter = x.length() < y.length() ? x : y;
        String longer = x.length() < y.length() ? y : x;
        int lenShorter = shorter.length();
        int lenLonger = longer.length();
        int[][] c = new int[2][lenShorter];
        for (int i = 0; i < lenLonger; i++) {
            for (int j = 0; j < lenShorter; j++) {
                if (longer.charAt(i) == shorter.charAt(j)) {
                    if (i == 0 || j == 0) {
                        c[i % 2][j] = 1;
                    } else {
                        c[i % 2][j] = c[(i - 1) % 2][j - 1] + 1;
                    }
                    if (c[i % 2][j] >= maxLen) {
                        if (c[i % 2][j] != maxLen) {
                            resultSet.clear();
                            maxLen = c[i % 2][j];
                        }
                        String lcsub = longer.substring(i - maxLen + 1, i + 1);
                        if (lcsub.length() > 0) {
                            resultSet.add(lcsub);
                        }
                    }
                } else {
                    c[i % 2][j] = 0;
                }
            }
        }
        result.addAll(resultSet);
        return result;
    }

    /**
     * findAll的优化版本2
     * 因为c[i,j]的计算只依赖于c[i-1,j-1]
     * 那么如果按照(0,0) -> (1,1)斜线方式迭代，只需要保存上一次计算的值即可
     * 这样空间复杂度可以进一步减小
     * <p>
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param x 字符串A
     * @param y 字符串B
     * @return 字符串A和字符串B的最长公共子串集合，无则返回空集合
     */
    public List<String> findAllByDpUpdate2(String x, String y) {
        ArrayList<String> result = new ArrayList<>();
        if (x == null || x.isEmpty() || y == null || y.isEmpty()) {
            return result;
        }
        // 根据最优解的递归定义，自底向上计算最优解的值，并找出最长公共子串
        Set<String> resultSet = new HashSet<>();
        int lenX = x.length();
        int lenY = y.length();
        int maxLen = 0;
        int lengthOfSubstring = 0;
        for (int start = 0; start < lenX; start++) {
            for (int i = start, j = 0; i < lenX && j < lenY; i++, j++) {
                if (x.charAt(i) == y.charAt(j)) {
                    lengthOfSubstring++;
                    if (lengthOfSubstring >= maxLen) {
                        if (lengthOfSubstring != maxLen) {
                            resultSet.clear();
                            maxLen = lengthOfSubstring;
                        }
                        String lcsub = x.substring(i - maxLen + 1, i + 1);
                        if (lcsub.length() > 0) {
                            resultSet.add(lcsub);
                        }
                    }
                } else {
                    lengthOfSubstring = 0;
                }
            }
        }
        lengthOfSubstring = 0;
        for (int start = 0; start < lenY; start++) {
            for (int i = 0, j = start; i < lenX && j < lenY; i++, j++) {
                if (x.charAt(i) == y.charAt(j)) {
                    lengthOfSubstring++;
                    if (lengthOfSubstring >= maxLen) {
                        if (lengthOfSubstring != maxLen) {
                            resultSet.clear();
                            maxLen = lengthOfSubstring;
                        }
                        String lcsub = x.substring(i - maxLen + 1, i + 1);
                        if (lcsub.length() > 0) {
                            resultSet.add(lcsub);
                        }
                    }
                } else {
                    lengthOfSubstring = 0;
                }
            }
        }
        result.addAll(resultSet);
        return result;
    }
}
