package org.andreschnabel.schedulevalidator;

import java.nio.file.Files;
import java.nio.file.Paths;

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

    public void checkEq(String outPath) throws Exception {
        if(makespanWithoutOvertime() - makespanWithMaxOvertime() == 0)
            Files.createFile(Paths.get(outPath + "plsdoskip"));
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 2) throw new Exception("Wrong number of arguments!");
        String instanceFn, outPath;
        outPath = args[0];
        if(!Files.exists(Paths.get(outPath)))
            Files.createDirectory(Paths.get(outPath));
        instanceFn = args[1];
        new MakespanApproximations(instanceFn).checkEq(outPath);
    }

}
