package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.Project;
import org.andreschnabel.schedulevalidator.SerialSGS;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class SerialSGSTest {

    private SerialSGS sgs;

    @Before
    public void setUp() throws Exception {
        Project p = new Project(TestHelpers.TEST_FILE);
        this.sgs = new SerialSGS(p);
    }

    @Test
    public void testScheduleJobs() throws Exception {
        int[] actualSts = sgs.scheduleJobs(new int[]{0, 1, 2, 3, 4}, new int[]{0});
        int[] expSts = { 0, 0, 2, 4, 6 };
        assertArrayEquals(expSts, actualSts);

        actualSts = sgs.scheduleJobs(new int[]{0, 1, 2, 3, 4}, new int[]{1});
        expSts = new int[] {0, 0, 2, 0, 4};
        assertArrayEquals(expSts, actualSts);

        actualSts = sgs.scheduleJobs(new int[]{0, 3, 1, 2, 4}, new int[]{0});
        expSts = new int[] {0, 2, 4, 0, 6};
        assertArrayEquals(expSts, actualSts);

        actualSts = sgs.scheduleJobs(new int[]{0, 3, 1, 2, 4}, new int[]{1});
        expSts = new int[] {0, 0, 2, 0, 4};
        assertArrayEquals(expSts, actualSts);
    }
}