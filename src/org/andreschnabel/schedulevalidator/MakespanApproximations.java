package org.andreschnabel.schedulevalidator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class MakespanApproximations {

    private ProjectWithOvertime p;
    private SerialSGS sgs;

    public MakespanApproximations(String fn) throws Exception {
        p = new ProjectWithOvertime(fn);
        sgs = new SerialSGS(p);
    }

    private int makespanWithoutOvertime() {
        return p.makespan(sgs.scheduleJobs(p.topOrder, p.zzero));
    }

    private int makespanWithMaxOvertime() {
        return p.makespan(sgs.scheduleJobs(p.topOrder, p.zmax));
    }

    public boolean isEq() throws Exception {
        return makespanWithoutOvertime() - makespanWithMaxOvertime() == 0;
    }

    public void checkEq(String outPath) throws Exception {
        if(isEq())
            Files.createFile(Paths.get(outPath + "plsdoskip"));
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 2) throw new Exception("Wrong number of arguments!");

        if(args[0].equals("count")) {
            String path = args[1];
            int numPurged = countPurged(path);
            System.out.println("Number purged = " + numPurged);
            return;
        }

        String instanceFn, outPath;
        outPath = args[0];
        if(!Files.exists(Paths.get(outPath)))
            Files.createDirectory(Paths.get(outPath));
        instanceFn = args[1];
        new MakespanApproximations(instanceFn).checkEq(outPath);
    }

    private static int countPurged(String path) throws Exception {
        int c = 0;

        for(Path f : Files.list(Paths.get(path)).collect(Collectors.toList())) {
            String fn = f.getFileName().toString();
            if(Files.isRegularFile(f) && fn.endsWith(".sm")) {
                boolean eq = new MakespanApproximations(path + "/" + fn).isEq();
                if (eq) c++;
            }
        }

        return c;
    }

}
