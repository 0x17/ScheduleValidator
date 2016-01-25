package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.EarliestStartSchedule;
import org.andreschnabel.schedulevalidator.ProjectWithOvertime;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EarliestStartScheduleTest {

    private ProjectWithOvertime p;
    private EarliestStartSchedule ess;

    @Before
    public void setUp() throws Exception {
        this.p = new ProjectWithOvertime(TestHelpers.TEST_FILE);
        this.ess = new EarliestStartSchedule(p);
    }

    @Test
    public void testComputeESS() throws Exception {
        int[] sts = ess.computeESS();
        int[] expSts = { 0, 0, 2, 0, 4 };
        assertArrayEquals(expSts, sts);
    }
}