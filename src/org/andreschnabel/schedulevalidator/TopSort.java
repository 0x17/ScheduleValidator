package org.andreschnabel.schedulevalidator;

public final class TopSort {

    public static int[] computeTopologicalOrder(Project p) {
        int[] order = new int[p.numJobs];

        order[0] = 0;
        for(int ix=1; ix<p.numJobs; ix++) {
            for(int j=0; j<p.numJobs; j++) {
                if(!activityInOrderBefore(p, j, order, ix) && !hasMissingPredBefore(p, j, order, ix)) {
                    order[ix] = j;
                    break;
                }
            }
        }

        return order;
    }

    private static boolean activityInOrderBefore(Project p, int j, int[] order, int ix) {
        for(int ix2 = 0; ix2 < ix; ix2++)
            if(order[ix2] == j)
                return true;
        return false;
    }

    private static boolean hasMissingPredBefore(Project p, int j, int[] order, int ix) {
        for(int i=0; i<p.numJobs; i++)
            if(p.adjMx[i][j])
                for(int ix2 = 0; ix2 < ix; ix2++)
                    if(order[ix2] == i)
                        return true;
        return false;
    }

}
