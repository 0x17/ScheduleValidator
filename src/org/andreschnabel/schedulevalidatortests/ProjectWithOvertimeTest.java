package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.ProjectWithOvertime;
import org.andreschnabel.schedulevalidator.SerialSGS;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectWithOvertimeTest {

    ProjectWithOvertime p;

    @Before
    public void setUp() throws Exception {
        p = new ProjectWithOvertime(TestHelpers.TEST_FILE);
    }

    @Test
    public void testConstructor() throws Exception {
        for(int r = 0; r < p.numRes; r++) {
            assertTrue(p.zzero[r] >= 0);
            assertTrue(p.zmax[r] >= 0);
        }

        // revenue function must be monotonically decreasing
        for(int i=1; i<p.revenues.length; i++)
            assertTrue(p.revenues[i-1] >= p.revenues[i]);

        for(float r : p.revenues)
            assertTrue(r >= 0.0f);

        int[] stsNoOvertime = new SerialSGS(p).scheduleJobs(p.topOrder, p.zzero);
        assertEquals(0.0, p.revenues[p.makespan(stsNoOvertime)], 0.0001);
    }

    @Test
    public void testProfit() throws Exception {
        SerialSGS sgs = new SerialSGS(p);
        int[] stsNoOvertime = sgs.scheduleJobs(p.topOrder, p.zzero);
        assertEquals(0.0, p.profit(stsNoOvertime), 0.0001);
    }

    @Test
    public void testMakespan() throws Exception {
        int[] sts = new int[]{0, 2, 4, 0, 6};
        assertEquals(6, p.makespan(sts));
    }
}