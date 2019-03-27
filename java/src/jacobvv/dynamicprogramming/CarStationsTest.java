package jacobvv.dynamicprogramming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CarStationsTest {

    private static class TestCase {
        private int[] e;
        private int[] x;
        private int[][] a;
        private int[][] t;
        private int n;
        private int[] result;

        public TestCase(int[] e, int[] x, int[][] a, int[][] t, int[] result) {
            this.e = e;
            this.x = x;
            this.a = a;
            this.t = t;
            this.result = result;
            this.n = a[0].length;
        }
    }

    private List<TestCase> cases = new ArrayList<>();

    @BeforeEach
    void setup() {
        cases.add(new TestCase(
                new int[]{2, 4},
                new int[]{3, 2},
                new int[][]{
                        {7, 9, 3, 4, 8, 4},
                        {8, 5, 6, 4, 5, 7}
                },
                new int[][]{
                        {2, 3, 1, 3, 4},
                        {2, 1, 2, 2, 1}
                },
                new int[]{0, 1, 0, 1, 1, 0, 38}
        ));
    }

    @Test
    void test() {
        CarStations target = new CarStations();
        for (TestCase tc : cases) {
            assertArrayEquals(tc.result, target.fastestAssembly(
                    tc.e, tc.x, tc.a, tc.t, tc.n));
        }
    }

}
