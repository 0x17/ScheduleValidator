package org.andreschnabel.schedulevalidator;

import java.util.function.BiFunction;

public final class ScheduleProperties {

    private final ProjectWithOvertime p;

    public ScheduleProperties(ProjectWithOvertime p) {
        this.p = p;
    }

    public boolean resourceFeasible(int[] sts) {
        for(int t = 0; t < p.numPeriods; t++) {
            for(int r = 0; r < p.numRes; r++) {
                if(cumulatedDemand(sts, r, t) > p.capacities[r] + p.zmax[r])
                    return false;
            }
        }
        return true;
    }

    public int cumulatedDemand(int[] sts, int r, int t) {
        int cdemand = 0;
        for(int j=0; j<p.numJobs; j++) {
            if(sts[j] + 1 <= t && t <= sts[j] + p.durations[j]) {
                cdemand += p.demands[j][r];
            }
        }
        return cdemand;
    }

    private <T> T orderFeasibilityCommon(int[] sts, BiFunction<Integer, Integer, T> func, T def) {
        for(int j=0; j<p.numJobs; j++) {
            for(int i=0; i<p.numJobs; i++) {
                if(p.adjMx[i][j] && sts[i] + p.durations[i] > sts[j])
                    return func.apply(i, j);
            }
        }
        return def;
    }

    public boolean orderFeasible(int[] sts) {
        return orderFeasibilityCommon(sts, (i, j) -> false, true);
    }

    public String orderInfeasibilityCause(int[] sts) {
        return orderFeasibilityCommon(sts, (i, j) -> (i+1) + "->" + (j+1) + " but ST("+(i+1)+")+d("+(i+1)+")="+sts[i]+"+"+p.durations[i]+"="+(sts[i]+p.durations[i])+">"+sts[j]+"=ST("+(j+1)+")!", "");
    }

    public float totalCosts(int[] sts) {
        float costs = 0.0f;
        for(int r = 0; r < p.numRes; r++) {
            for(int t = 0; t < p.numPeriods; t++) {
                int cdem = cumulatedDemand(sts, r, t);
                costs += p.kappa[r] * Math.max(0, cdem - p.capacities[r]);
            }
        }
        return costs;
    }

    public boolean isScheduleValid(int[] sts) {
        return orderFeasible(sts) && resourceFeasible(sts);
    }
}
