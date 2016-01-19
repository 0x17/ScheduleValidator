package org.andreschnabel.schedulevalidator;

public class ScheduleGenerator {

    Project p;

    public ScheduleGenerator(Project p) {
        this.p = p;
    }

    protected boolean resourceFeasible(int[][] resRem, int j, int stj) {
        for(int tau = stj + 1; tau <= stj + p.durations[j]; tau++) {
            for(int r = 0; r < p.numRes; r++) {
                if(resRem[r][tau] < 0)
                    return false;
            }
        }
        return true;
    }

    protected int[][] initResidualCapacities(int[] zr) {
        int[][] resRem = new int[p.numRes][p.numPeriods];
        for(int r = 0; r < p.numRes; r++) {
            for(int t = 0; t < p.numPeriods; t++) {
                resRem[r][t] = p.capacities[r] + zr[r];
            }
        }
        return resRem;
    }

    protected void scheduleJob(int[][] resRem, int[] sts, int j, int stj) {
        sts[j] = stj;
        for(int tau = stj +1; tau <= stj + p.durations[j]; tau++) {
            for(int r = 0; r < p.numRes; r++) {
                resRem[r][tau] -= p.demands[j][r];
            }
        }
    }

    protected int lastPredFinishingTime(int[] sts, int job) {
        int t = 0;
        for(int i = 0; i < p.numJobs; i++) {
            if(p.adjMx[i][job] && sts[i] + p.durations[i] > t) {
                t = sts[i] + p.durations[i];
            }
        }
        return t;
    }

}