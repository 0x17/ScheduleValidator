package org.andreschnabel.schedulevalidator;

public final class ScheduleProperties {

    public static boolean resourceFeasible(ProjectWithOvertime p, int[] sts) {
        for(int t = 0; t < p.numPeriods; t++) {
            for(int r = 0; r < p.numRes; r++) {
                if(cumulatedDemand(p, sts, r, t) > p.capacities[r] + p.zmax[r])
                    return false;
            }
        }
        return true;
    }

    public static int cumulatedDemand(ProjectWithOvertime p, int[] sts, int r, int t) {
        int cdemand = 0;
        for(int j=0; j<p.numJobs; j++) {
            if(sts[j] + 1 <= t && t <= sts[j] + p.durations[j]) {
                cdemand += p.demands[j][r];
            }
        }
        return cdemand;
    }

    public static boolean orderFeasible(ProjectWithOvertime p, int[] sts) {
        for(int j=0; j<p.numJobs; j++) {
            for(int i=0; i<p.numJobs; i++) {
                if(p.adjMx[i][j] && sts[i] + p.durations[i] > sts[j])
                    return false;
            }
        }
        return true;
    }

    public static float totalCosts(ProjectWithOvertime p, int[] sts) {
        float costs = 0.0f;
        for(int r = 0; r < p.numRes; r++) {
            for(int t = 0; t < p.numPeriods; t++) {
                int cdem = cumulatedDemand(p, sts, r, t);
                costs += p.kappa[r] * Math.max(0, cdem - p.capacities[r]);
            }
        }
        return costs;
    }

    public static boolean isScheduleValid(ProjectWithOvertime p, int[] sts) {
        return orderFeasible(p, sts) && resourceFeasible(p, sts);
    }
}
