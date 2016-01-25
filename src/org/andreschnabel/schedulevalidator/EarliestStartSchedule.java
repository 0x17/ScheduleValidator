package org.andreschnabel.schedulevalidator;

public class EarliestStartSchedule extends ScheduleGenerator {

    public EarliestStartSchedule(Project p) {
        super(p);
    }

    public int[] computeESS() {
        int[] ess = new int[p.numJobs];
        for(int job : p.topOrder)
            ess[job] = lastPredFinishingTime(ess, job);
        return ess;
    }

}
