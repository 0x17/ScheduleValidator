package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.Project;
import org.andreschnabel.schedulevalidator.ScheduleGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScheduleGeneratorTest {

    ScheduleGenerator sg;

    @Before
    public void setUp() throws Exception {
        Project p = new Project("MiniBeispiel.DAT");
        sg = new ScheduleGenerator(p);
    }

    @Test
    public void testResourceFeasible() throws Exception {

    }

    @Test
    public void testInitResidualCapacities() throws Exception {

    }

    @Test
    public void testScheduleJob() throws Exception {

    }

    @Test
    public void testLastPredFinishingTime() throws Exception {

    }
}