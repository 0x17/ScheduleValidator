package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.ProjectWithOvertime;
import org.andreschnabel.schedulevalidator.ScheduleProperties;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SchedulePropertiesTest {

    private ScheduleProperties props;
    private ProjectWithOvertime p;

    @Before
    public void setUp() throws Exception {
        this.p = new ProjectWithOvertime(TestHelpers.TEST_FILE);
        this.props = new ScheduleProperties(p);
    }

    @Test
    public void testResourceFeasible() throws Exception {
        p.zmax = new int[] {0};
        assertFalse(props.resourceFeasible(new int[] { 0, 0, 2, 0, 4 }));
        assertTrue(props.resourceFeasible(new int[] { 0, 0, 2, 4, 6 }));
    }

    @Test
    public void testCumulatedDemand() throws Exception {
        int[] sts = { 0, 0, 2, 0, 4 };
        int[] profile = { 0, 3, 3, 1, 1, 0 };
        for(int t=0; t<profile.length; t++)
            assertEquals(profile[t], props.cumulatedDemand(sts, 0, t));
    }

    @Test
    public void testOrderFeasible() throws Exception {
        assertTrue(props.orderFeasible(new int[] {0, 0, 2, 0, 4}));
        assertTrue(props.orderFeasible(new int[] {0, 0, 4, 2, 6}));
        assertTrue(props.orderFeasible(new int[] {0, 0, 2, 4, 6}));
        assertFalse(props.orderFeasible(new int[] {0, 0, 2, 4, 4}));
        assertFalse(props.orderFeasible(new int[] {0, 0, 1, 4, 6}));
    }

    @Test
    public void testTotalCosts() throws Exception {
        assertEquals(0.0f, props.totalCosts(new int[] {0, 0, 2, 4, 6}), 0.0f);
        assertEquals(1.0f, props.totalCosts(new int[] {0, 0, 2, 0, 4}), 0.0f);
    }

    @Test
    public void testIsScheduleValid() throws Exception {
        p.zmax = new int[] {0};
        assertTrue(props.isScheduleValid(new int[] { 0, 0, 2, 4, 6 }));
        assertFalse(props.isScheduleValid(new int[] { 0, 0, 1, 4, 6 }));
        assertFalse(props.isScheduleValid(new int[] { 0, 0, 2, 0, 4 }));
        assertFalse(props.isScheduleValid(new int[] { 0, 2, 0, 4, 6 }));
    }
}