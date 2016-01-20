package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.Project;
import org.andreschnabel.schedulevalidator.ScheduleGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScheduleGeneratorTest {

    ScheduleGenerator sg;
    private Project p;

    @Before
    public void setUp() throws Exception {
        this.p = new Project(TestHelpers.TEST_FILE);
        this.sg = new ScheduleGenerator(p);
    }

    @Test
    public void testResourceFeasible() throws Exception {
        int[][] resRem = new int[][]{{2, 2, 2, 2, 2, 2, 2}};
        assertTrue(sg.resourceFeasible(resRem, p.demands, 1, 0));
        resRem = new int[][]{{2, 1, 1, 2, 2, 2, 2}};
        assertFalse(sg.resourceFeasible(resRem, p.demands, 3, 0));
        assertTrue(sg.resourceFeasible(resRem, p.demands, 1, 0));
    }

    @Test
    public void testInitResidualCapacities() throws Exception {
        int[][] resRem = new int[][]{{2, 2, 2, 2, 2, 2, 2}};
        int[][] actResRem = sg.initResidualCapacities(new int[]{0});
        assertEquals(1, actResRem.length);
        assertArrayEquals(resRem[0], actResRem[0]);
    }

    @Test
    public void testScheduleJob() throws Exception {
        int[][] resRem = new int[][]{{2, 2, 2, 2, 2, 2, 2}};
        int[] sts = new int[]{0, 0, 0, 0, 0};
        sg.scheduleJob(resRem, sts, 1, 0);
        int[][] expResRem = new int[][]{{2, 1, 1, 2, 2, 2, 2}};
        assertEquals(1, resRem.length);
        assertArrayEquals(expResRem[0], resRem[0]);
        assertEquals(0, sts[1]);
    }

    @Test
    public void testLastPredFinishingTime() throws Exception {
        int[] sts = new int[]{ 0, 0, 0, 0, 0 };
        assertEquals(2, sg.lastPredFinishingTime(sts, 2));
        assertEquals(0, sg.lastPredFinishingTime(sts, 1));
    }
}