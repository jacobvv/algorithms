package string;

public class StringPatternMatching {

    public int bfMatch(String src, String target) {
        // 朴素的模式匹配（Brute-Force）算法，使用暴力方式进行循环匹配
        // 时间复杂度O(nk)，空间复杂度O(1)
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

    public int kmpMatch(String src, String target) {
        // 朴素的模式匹配（Brute-Force）算法，使用暴力方式进行循环匹配
        // 时间复杂度O(nk)，空间复杂度O(1)
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


}
