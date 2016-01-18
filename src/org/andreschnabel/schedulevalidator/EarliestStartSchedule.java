package org.andreschnabel.schedulevalidator;

public class EarliestStartSchedule extends ScheduleGenerator {

    public EarliestStartSchedule(Project p) {
        super(p);
    }

    public int[] computeESS(int[] order) {
        int[] ess = new int[p.numJobs];
        for(int job : order)
            ess[job] = lastPredFinishingTime(ess, job);
        return ess;
    }

}
