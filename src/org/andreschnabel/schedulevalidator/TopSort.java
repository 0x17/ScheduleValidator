package org.andreschnabel.schedulevalidator;

public final class TopSort {

    public static int[] computeTopologicalOrder(Project p) {
        int[] order = new int[p.numJobs];

        order[0] = 0;
        for(int ix=1; ix<p.numJobs; ix++) {
            for(int j=0; j<p.numJobs; j++) {
                if(!activityInOrderBefore(j, order, ix) && !hasMissingPredBefore(p.adjMx, j, order, ix)) {
                    order[ix] = j;
                    break;
                }
            }
        }

        return order;
    }

    public static boolean activityInOrderBefore(int j, int[] order, int ix) {
        //return IntStream.range(0, ix).anyMatch((ix2) -> order[ix2] == j);
        for(int ix2 = 0; ix2 < ix; ix2++)
            if(order[ix2] == j)
                return true;

        return false;
    }

    public static boolean hasMissingPredBefore(boolean[][] adjMx, int j, int[] order, int ix) {
        for(int i=0; i<order.length; i++)
            if(adjMx[i][j]) {
                boolean found = false;
                for (int ix2 = 0; ix2 < ix; ix2++)
                    if (order[ix2] == i) {
                        found = true;
                        break;
                    }
                if(!found) return true;
            }

        return false;
    }

}
