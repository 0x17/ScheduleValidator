package org.andreschnabel.schedulevalidator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Project {

    public int numJobs, numRes, numPeriods;

    public int[] durations;
    public int[] capacities;
    public int[][] demands;
    public boolean[][] adjMx;

    public int[] topOrder;

    public Project(String filename) throws Exception {
        if(filename.endsWith(".sm")) {
            parseProGen(filename);
        } else if(filename.endsWith(".rcp")) {
            parsePattersonFormat(filename);
        }
    }

    private void parsePattersonFormat(String filename) throws Exception {
        boolean cardinalitiesKnown = false, continueSuccs = false;
        int succRemaining = 0, j = 0;

        List<String> lines = Files.lines(Paths.get(filename)).collect(Collectors.toList());

        for(String line : lines) {
            final Integer[] values = Utils.intPartsArr(line);

            if(succRemaining > 0) {
                int k;
                for(k=0; k<succRemaining && k<values.length; k++) {
                    adjMx[j][values[k]-1] = true;
                }
                succRemaining -= k;
                if(succRemaining <= 0) {
                    j++;
                }
            }
            // #jobs #res
            else if(values.length == 2) {
                numJobs = values[0];
                numRes = values[1];
                adjMx = new boolean[numJobs][numJobs];
                durations = new int[numJobs];
                demands = new int[numJobs][numRes];
                capacities = new int[numRes];
                cardinalitiesKnown = true;
            } else if(cardinalitiesKnown) {
                // K1 K2 ...
                if(values.length == numRes) {
                    capacities = Utils.integerToIntArray(values);
                    // dj kj1 kj2 .. #succs succ1 succ2 ...
                } else if(values.length >= 1 + numRes + 1) {
                    durations[j] = values[0];
                    for(int r=0; r<numRes; r++)
                        demands[j][r] = values[1+r];
                    int succCount = values[1+numRes];
                    int k;
                    for(k=0; k<succCount && k+2+numRes<values.length; k++) {
                        int vix = 2+numRes+k;
                        adjMx[j][values[vix]-1] = true;
                    }
                    if(k+2+numRes >= values.length && k < succCount) {
                        succRemaining = succCount - k;
                    } else {
                        j++;
                    }
                }
            }
        }

        numPeriods = Arrays.stream(durations).sum()+1;
    }

    private void parseProGen(String filename) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filename));

        int ctr = 0;

        for(String line : lines) {
            if(line.startsWith("jobs (incl. supersource/sink )")) {
                numJobs = Utils.extractInt(line);
                adjMx = new boolean[numJobs][numJobs];
                durations = new int[numJobs];
            }
            else if(line.startsWith("horizon")) {
                numPeriods = Utils.extractInt(line);
            }
            else if(line.trim().startsWith("- renewable")) {
                numRes = Utils.extractInt(line.replace("R", " "));
                capacities = new int[numRes];
                demands = new int[numJobs][numRes];
            }
            else if(line.startsWith("PRECEDENCE RELATIONS:")) parsePrecedenceRelation(lines, ctr);
            else if(line.startsWith("REQUESTS/DURATIONS:")) parseDemandsAndDurations(lines, ctr);
            else if(line.startsWith("RESOURCEAVAILABILITIES:")) parseCapacities(lines, ctr);

            ctr++;
        }

        topOrder = TopSort.computeTopologicalOrder(this);
    }

    private void parseCapacities(List<String> lines, int ctr) {
        ArrayList<Integer> caps = Utils.intParts(lines.get(ctr + 2));
        for(int i=0; i<caps.size(); i++)
            capacities[i] = caps.get(i);
    }

    private void parseDemandsAndDurations(List<String> lines, int ctr) {
        for(int i=0; i<numJobs; i++) {
            int ix = ctr + 3 + i;
            ArrayList<Integer> parts = Utils.intParts(lines.get(ix));
            durations[parts.get(0)-1] = parts.get(2);
            for(int j=3; j<parts.size(); j++) {
                demands[parts.get(0)-1][j-3] = parts.get(j);
            }
        }
    }

    private void parsePrecedenceRelation(List<String> lines, int ctr) {
        for(int i=0; i<numJobs; i++) {
            int ix = ctr + 2 + i;
            ArrayList<Integer> parts = Utils.intParts(lines.get(ix));
            for(int j=3; j<parts.size(); j++) {
                adjMx[parts.get(0)-1][parts.get(j)-1] = true;
            }
        }
    }

}
