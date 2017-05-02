package org.andreschnabel.schedulevalidator;

public class ScheduleGenerator {

    final Project p;

    public ScheduleGenerator(Project p) {
        this.p = p;
    }

    public boolean resourceFeasible(int[][] resRem, int[][] demands, int j, int stj) {
        for(int tau = stj + 1; tau <= stj + p.durations[j]; tau++) {
            for(int r = 0; r < p.numRes; r++) {
                if(resRem[r][tau] - demands[j][r] < 0)
                    return false;
            }
        }
        return true;
    }

    public int[][] initResidualCapacities(int[] zr) {
        int[][] resRem = new int[p.numRes][p.numPeriods];
        for(int r = 0; r < p.numRes; r++) {
            for(int t = 0; t < p.numPeriods; t++) {
                resRem[r][t] = p.capacities[r] + zr[r];
            }
        }
        return resRem;
    }

    public void scheduleJob(int[][] resRem, int[] sts, int j, int stj) {
        sts[j] = stj;
        for(int tau = stj +1; tau <= stj + p.durations[j]; tau++) {
            for(int r = 0; r < p.numRes; r++) {
                resRem[r][tau] -= p.demands[j][r];
            }
        }
    }

    public int lastPredFinishingTime(int[] sts, int job) {
        int t = 0;
        for(int i = 0; i < p.numJobs; i++) {
            if(p.adjMx[i][job] && sts[i] + p.durations[i] > t) {
                t = sts[i] + p.durations[i];
            }
        }
        return t;
    }

}
