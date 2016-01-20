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
        Project p = new Project("MiniBeispiel.sm");
        this.sgs = new SerialSGS(p);
    }

    @Test
    public void testScheduleJobs() throws Exception {
        sgs.scheduleJobs(new int[]{0, 1, 2, 3, 4}, new int[]{0});
    }
}