package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.ProjectWithOvertime;
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
    public void testProfit() throws Exception {
        for(int t=1; t<p.numPeriods; t++) {
            //assertTrue(p.revenues[t-1] >= p.revenues[t]);
        }

    }
}