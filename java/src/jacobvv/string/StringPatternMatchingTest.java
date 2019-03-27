package jacobvv.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringPatternMatchingTest {

    private String[][] inputs = new String[][]{
            {"hello java", "java"},
            {"best practice of algorithms.", "best practice of algorithms."},
            {"world.", "abc"},
            {"", "1"},
            {null, ""},
            {"a", ""},
            {"ABABABAABAABABAABAABABA", "ABAABABA"}
    };
    private int[] outputs = new int[]{6, 0, -1, -1, -1, -1, 7};

    @Test
    void bfMatchTest() {
        StringPatternMatching target = new StringPatternMatching();
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(outputs[i], target.bfMatch(inputs[i][0], inputs[i][1]));
        }
    }

    @Test
    void kmpMatchTest() {
        StringPatternMatching target = new StringPatternMatching();
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(outputs[i], target.kmpMatch(inputs[i][0], inputs[i][1]));
        }
    }

}