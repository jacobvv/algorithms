package string;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LongestCommonSubstringTest {

    private String[][] inputs = new String[][]{
            {"a", "ab"},
            {"hello java", "java"},
            {"best practice of algorithms.", "best practice of algorithms."},
            {"best practice of java.", "best practice of algorithms."},
            {"world.", "abc"},
            {"hello java", "world see aha."},
            {"", "1"},
            {null, ""},
    };
    private String[][] outputs = new String[][]{
            {"a"},
            {"java"},
            {"best practice of algorithms."},
            {"best practice of "},
            {},
            {" ", "a", "l", "e", "h", "o"},
            {},
            {}
    };

    @Test
    void findAllTest() {
        LongestCommonSubstring target = new LongestCommonSubstring();
        for (int i = 0; i < inputs.length; i++) {
            List<String> result = target.findAllByDp(inputs[i][0], inputs[i][1]);
            List<String> expected = Arrays.asList(outputs[i]);
            expected.sort(null);
            result.sort(null);
            assertIterableEquals(expected, result,
                    "Inputs: " + Arrays.asList(inputs[i]) +
                            ", Expected: " + expected +
                            ", But actual: " + result);
        }
    }

    @Test
    void findAllUpdate1Test() {
        LongestCommonSubstring target = new LongestCommonSubstring();
        for (int i = 0; i < inputs.length; i++) {
            List<String> result = target.findAllByDpUpdate1(inputs[i][0], inputs[i][1]);
            List<String> expected = Arrays.asList(outputs[i]);
            expected.sort(null);
            result.sort(null);
            assertIterableEquals(expected, result,
                    "Inputs: " + Arrays.asList(inputs[i]) +
                            ", Expected: " + expected +
                            ", But actual: " + result);
        }
    }

    @Test
    void findAllUpdate2Test() {
        LongestCommonSubstring target = new LongestCommonSubstring();
        for (int i = 0; i < inputs.length; i++) {
            List<String> result = target.findAllByDpUpdate2(inputs[i][0], inputs[i][1]);
            List<String> expected = Arrays.asList(outputs[i]);
            expected.sort(null);
            result.sort(null);
            assertIterableEquals(expected, result,
                    "Inputs: " + Arrays.asList(inputs[i]) +
                            ", Expected: " + expected +
                            ", But actual: " + result);
        }
    }

    @Test
    void findAllByKmpTest() {
        LongestCommonSubstring target = new LongestCommonSubstring();
        for (int i = 0; i < inputs.length; i++) {
            List<String> result = target.findAllByKmp(inputs[i][0], inputs[i][1]);
            List<String> expected = Arrays.asList(outputs[i]);
            expected.sort(null);
            result.sort(null);
            assertIterableEquals(expected, result,
                    "Inputs: " + Arrays.asList(inputs[i]) +
                            ", Expected: " + expected +
                            ", But actual: " + result);
        }
    }

}
