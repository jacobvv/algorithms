package jacobvv.string;

public class StringPatternMatching {

    public int bfMatch(String src, String target) {
        // 朴素的模式匹配（Brute-Force）算法，使用暴力方式进行循环匹配
        // 时间复杂度O(nm)，空间复杂度O(1)
        if (src == null || target == null) {
            return -1;
        }
        int len = src.length();
        int targetLen = target.length();
        if (len == 0 || targetLen == 0 || targetLen > len) {
            return -1;
        }
        for (int i = 0; i < len - targetLen + 1; i++) {
            boolean match = true;
            for (int j = 0; j < targetLen; j++) {
                if (src.charAt(i + j) != target.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return i;
            }
        }
        return -1;
    }

    private int[] kmpTable(String target) {
        int length = target.length();
        if (length < 2) {
            return new int[]{-1};
        }
        if (length == 2) {
            return new int[]{-1, 0};
        }
        int[] table = new int[length];
        int cnt = 0;
        int pos = 2;
        table[0] = -1;
        table[1] = 0;
        while (pos < length) {
            if (target.charAt(pos - 1) == target.charAt(cnt)) {
                cnt++;
                table[pos] = cnt;
                pos++;
            } else if (cnt > 0) {
                cnt = table[cnt];
            } else {
                table[pos] = 0;
                pos++;
            }
        }
        return table;
    }

    public int kmpMatch(String src, String target) {
        // KMP（Knuth-Morris-Pratt）算法，通过预先构建部分匹配表来帮助字符串匹配，避免过多回溯。
        // 时间复杂度O(n)，空间复杂度O(m)
        if (src == null || target == null) {
            return -1;
        }
        int len = src.length();
        int targetLen = target.length();
        if (len == 0 || targetLen == 0 || targetLen > len) {
            return -1;
        }
        int[] table = kmpTable(target);
        int m = 0;
        int i = 0;
        while (m < len) {
            if (i < 0 || src.charAt(m) == target.charAt(i)) {
                m++;
                i++;
            } else {
                i = table[i];
            }
            if (i == targetLen) {
                return m - targetLen;
            }
        }
        return -1;
    }


}
