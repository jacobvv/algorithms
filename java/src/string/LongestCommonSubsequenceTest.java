package string;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LongestCommonSubsequenceTest {

    private String[][] inputs = new String[][]{
            {"hello java", "java"},
            {"best practice of algorithms.", "best practice of algorithms."},
            {"world.", "abc"},
            {"hello java", "world see aha."},
            {"", "1"},
            {null, ""},
    };
    private String[][] outputs = new String[][]{
            {"java"},
            {"best practice of algorithms."},
            {},
            {"e aa", "l aa", "o aa"},
            {},
            {}
    };

    @Test
    void findByDynamicProgrammingTest() {
        LongestCommonSubsequence target = new LongestCommonSubsequence();
        for (int i = 0; i < inputs.length; i++) {
            boolean isOK = false;
            String result = target.findByDp(inputs[i][0], inputs[i][1]);
            if (outputs[i].length == 0) {
                assertEquals("", result);
            } else {
                for (int j = 0; j < outputs[i].length; j++) {
                    if (outputs[i][j].equals(result)) {
                        isOK = true;
                        break;
                    }
                }
                assertTrue(isOK, "Expected: " +
                        Arrays.toString(outputs[i]) + ", But actual: " + result);
            }
        }
    }

    @Test
    void findAllByRecursionTest() {
        LongestCommonSubsequence target = new LongestCommonSubsequence();
        for (int i = 0; i < inputs.length; i++) {
            List<String> result = target.findAllByDpRecursion(inputs[i][0], inputs[i][1]);
            List<String> expected = Arrays.asList(outputs[i]);
            expected.sort(null);
            result.sort(null);
            assertIterableEquals(expected, result, "Expected: " +
                    expected + ", But actual: " + result);
        }
    }

    @Test
    void findAllByStackTest() {
        LongestCommonSubsequence target = new LongestCommonSubsequence();
        for (int i = 0; i < inputs.length; i++) {
            List<String> result = target.findAllByDpStack(inputs[i][0], inputs[i][1]);
            List<String> expected = Arrays.asList(outputs[i]);
            expected.sort(null);
            result.sort(null);
            assertIterableEquals(expected, result,
                    "Inputs: " + Arrays.asList(inputs[i]) +
                            "Expected: " + expected +
                            ", But actual: " + result);
        }
    }


}