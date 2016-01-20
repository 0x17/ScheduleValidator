package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.ProjectWithOvertime;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectWithOvertimeTest {

    ProjectWithOvertime p;

    @Before
    public void setUp() throws Exception {
        p = new ProjectWithOvertime("MiniBeispiel.sm");
    }

    @Test
    public void testProfit() throws Exception {

    }
}