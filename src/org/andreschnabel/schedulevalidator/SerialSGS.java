package org.andreschnabel.schedulevalidator;

public class SerialSGS extends ScheduleGenerator {

    public SerialSGS(Project p) {
        super(p);
    }

    public int[] scheduleJobs(int[] order, int[] zr) {
        int[][] resRem = initResidualCapacities(zr);
        int[] sts = new int[p.numJobs];

        for(int job : order) {
            int t = lastPredFinishingTime(sts, job);
            while(!resourceFeasible(resRem, p.demands, job, t)) t++;
            scheduleJob(resRem, sts, job, t);
        }

        return sts;
    }
}
