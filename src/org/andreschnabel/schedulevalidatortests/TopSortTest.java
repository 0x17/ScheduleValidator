package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.Project;
import org.andreschnabel.schedulevalidator.TopSort;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class TopSortTest {

    @Test
    public void testComputeTopologicalOrder() throws Exception {
        int[][] feasibleOrders = {
            {0, 1, 2, 3, 4},
            {0, 3, 1, 2, 4},
            {0, 1, 3, 2, 4}
        };
        Project p = new Project("MiniBeispiel.sm");
        int[] order = TopSort.computeTopologicalOrder(p);
        assertTrue(Arrays.stream(feasibleOrders).anyMatch((fo) -> Arrays.equals(fo, order)));
    }

    @Test
    public void testActivityInOrderBefore() throws Exception {
        int[] order = { 0, 1, 2, 3, 4, 5 };
        int j = 3;
        int[] resultValues = { 0, 0, 0, 0, 1, 1 };
        assertTrue(IntStream.range(0, order.length).allMatch((ix) ->
                (resultValues[ix] == 1) == TopSort.activityInOrderBefore(j, order, ix)));
    }

    @Test
    public void testHasMissingPredBefore() throws Exception {
        boolean[][] adjMx = new boolean[][] {
                {false, true, false},
                {false, false, true},
                {false, false, false}
        };
        int[] order = { 0, 1, 2 };
        assertFalse(TopSort.hasMissingPredBefore(adjMx, 1, order, 1));
    }
}