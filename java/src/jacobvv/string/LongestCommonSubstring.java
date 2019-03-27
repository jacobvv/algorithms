package jacobvv.string;

import java.util.*;

/**
 * 最长公共子串(LCSub)问题
 * 与最长公共子序列(LCS)问题非常相似，子序列在原序列中必须连续，则称为子串
 */
public class LongestCommonSubstring {

    /**
     * 动态规划
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

    /**
     * 利用后缀数组和KMP算法解出最长公共子串
     * 对于任意字符串X和Y，其最大公共子串一定是字符串X的某个前缀和字符串Y的某个前缀的共同后缀
     * 相反同样成立，最大公共子串一定是字符串X的某个后缀和字符串Y的某个后缀的共同前缀
     * 这样字符串（假设长度为n），可以分解为m个不同的后缀，例如ABCDE可以分解为：
     * E
     * DE
     * CDE
     * BCDE
     * ABCDE
     * 这五个后缀。
     * 然后用这些后缀对另一个字符串进行匹配，能够匹配最长长度的部分即为最长公共子串。
     * 如果匹配算法使用朴素匹配法，则匹配的算法复杂度为O(n^3)，总共的算法复杂度为O(n^4)
     * 如果匹配算法使用KMP算法，则匹配算法复杂度为O(n)，总共的算法复杂度为O(n^2)
     * 这里使用KMP算法
     * <p>
     * 时间复杂度O(n^2)，空间复杂度(n)
     *
     * @param x 字符串A
     * @param y 字符串B
     * @return 字符串A和字符串B的最长公共子串集合，无则返回空集合
     */
    public List<String> findAllByKmp(String x, String y) {
        ArrayList<String> result = new ArrayList<>();
        if (x == null || x.isEmpty() || y == null || y.isEmpty()) {
            return result;
        }
        Set<String> resultSet = new HashSet<>();
        String longer = x.length() > y.length() ? x : y;
        String shorter = x.length() > y.length() ? y : x;
        int shorterLen = shorter.length();
        int max = 0;
        // 取出较短字符串的所有后缀进行KMP字符串匹配
        for (int i = 0; i < shorterLen; i++) {
            int lcsubLen = kmpMatch(longer, shorter, i);
            // 保存最大匹配长度即为最长公共子串
            if (lcsubLen >= max) {
                if (lcsubLen != max) {
                    resultSet.clear();
                    max = lcsubLen;
                }
                String lcsub = shorter.substring(i, i + max);
                if (lcsub.length() > 0) {
                    resultSet.add(lcsub);
                }
            }
        }
        result.addAll(resultSet);
        return result;
    }

    private static int[] kmpTable(String target, int start) {
        int length = target.length();
        if (length - start < 2) {
            return new int[]{-1};
        }
        if (length - start == 2) {
            return new int[]{-1, 0};
        }
        int[] table = new int[length - start];
        int cnt = start;
        int pos = start + 2;
        table[0] = -1;
        table[1] = 0;
        while (pos < length) {
            if (target.charAt(pos - 1) == target.charAt(cnt)) {
                cnt++;
                table[pos - start] = cnt - start;
                pos++;
            } else if (cnt > start) {
                cnt = table[cnt - start] + start;
            } else {
                table[pos - start] = 0;
                pos++;
            }
        }
        return table;
    }

    /**
     * KMP字符串匹配，只需返回匹配的最大长度
     *
     * @param src         源字符串
     * @param target      目标字符串
     * @param targetStart 目标字符串开始下标
     * @return 匹配的最大长度
     */
    private int kmpMatch(String src, String target, int targetStart) {
        if (src == null || target == null) {
            return -1;
        }
        int len = src.length();
        int targetLen = target.length() - targetStart;
        if (len == 0 || targetLen <= 0) {
            return 0;
        }
        int max = 0;
        int[] table = kmpTable(target, targetStart);
        int m = 0;
        int i = targetStart;
        while (m < len) {
            if (i < targetStart || src.charAt(m) == target.charAt(i)) {
                m++;
                i++;
            } else {
                i = table[i - targetStart] + targetStart;
            }
            if (i - targetStart == targetLen) {
                return targetLen;
            }
            if (i - targetStart > max) {
                max = i - targetStart;
            }
        }
        return max;
    }

    /**
     * 广义后缀树
     * TODO： 广义后缀树数据结构的实现
     *
     * @param x 字符串A
     * @param y 字符串B
     * @return 字符串A和字符串B的最长公共子串集合，无则返回空集合
     */
    public List<String> findAllByGst(String x, String y) {
        ArrayList<String> result = new ArrayList<>();
        if (x == null || x.isEmpty() || y == null || y.isEmpty()) {
            return result;
        }
        // TODO: 利用广义后缀树进行多字符串匹配
        Set<String> resultSet = new HashSet<>();
        return result;
    }
}
