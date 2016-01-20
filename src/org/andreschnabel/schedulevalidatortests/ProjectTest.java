package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.Project;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ProjectTest {

    Project p;

    @Before
    public void setUp() throws Exception {
        p = new Project("MiniBeispiel.sm");
    }

    @Test
    public void testConstructor() throws Exception {
        assertEquals(5, p.numJobs);
        assertEquals(7, p.numPeriods);
        assertEquals(1, p.numRes);

        int[] expDurations = { 0, 2, 2, 2, 0 };
        assertArrayEquals(expDurations, p.durations);

        int[] expDemands = { 0, 1, 1, 2, 0 };
        for(int i=0; i<expDemands.length; i++)
            assertEquals(expDemands[i], p.demands[i][0]);

        boolean[][] expAdjMx = {
                {false, true, false, true, false},
                {false, false, true, false, false},
                {false, false, false, false, true},
                {false, false, false, false, true},
                {false, false, false, false, false}
        };
        for(int i=0; i<expAdjMx.length; i++) {
            assertArrayEquals(expAdjMx[i], p.adjMx[i]);
        }

        assertEquals(1, p.capacities.length);
        assertEquals(2, p.capacities[0]);
    }


}