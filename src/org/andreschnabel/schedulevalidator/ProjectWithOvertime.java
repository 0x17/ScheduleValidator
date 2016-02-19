package org.andreschnabel.schedulevalidator;

import java.util.Arrays;

public class ProjectWithOvertime extends Project {

    private final ScheduleProperties props;
    public float[] kappa, revenues;
    public int[] zmax;
    public int[] zzero;

    public ProjectWithOvertime(String filename) throws Exception {
        super(filename);

        props = new ScheduleProperties(this);

        kappa = new float[numRes];
        zmax = new int[numRes];
        for(int r = 0; r < numRes; r++) {
            kappa[r] = 0.5f;
            zmax[r] = capacities[r] / 2;
        }

        computeRevenueFunction();
    }

    private void computeRevenueFunction() {
        zzero = new int[numRes];
        Arrays.fill(zzero, 0);

        int tkappa = computeTKappa();

        int[] ess = new EarliestStartSchedule(this).computeESS();

        int minMs = Math.max(makespan(ess), tkappa);
        int maxMs = makespan(new SerialSGS(this).scheduleJobs(topOrder, zzero));
        float maxCosts = props.totalCosts(ess);

        revenues = new float[numPeriods];
        for(int t=0; t<numPeriods; t++) {
            revenues[t] =
                    (minMs == maxMs || t < minMs) ? maxCosts :
                    (t > maxMs) ? 0.0f :
                    maxCosts - maxCosts / Utils.pow(maxMs - minMs, 2.0f) * Utils.pow(t - minMs, 2.0f);
        }
    }

    private int computeTKappa() {
        int tkappa = 0;
        for(int r=0; r<numRes; r++) {
            int areaAcc = 0;
            for(int j=0; j<numJobs; j++)
                areaAcc += durations[j] * demands[j][r];

            int quotient = (int)Math.ceil((float)areaAcc / (float)(capacities[r] + zmax[r]));
            if(quotient > tkappa)
                tkappa = quotient;
        }
        return tkappa;
    }

    public int makespan(int[] sts) {
        return sts[sts.length-1];
    }

    public float profit(int[] sts) {
        return revenues[makespan(sts)] - props.totalCosts(sts);
    }
}
