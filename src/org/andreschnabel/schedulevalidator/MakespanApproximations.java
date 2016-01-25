package org.andreschnabel.schedulevalidator;

import java.io.IOException;
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

    public void checkEq() throws Exception {
        if(makespanWithoutOvertime() - makespanWithMaxOvertime() == 0)
            Files.createFile(Paths.get("plsdoskip"));
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 1) throw new Exception("Wrong number of arguments!");
        MakespanApproximations ma = new MakespanApproximations(args[0]);
        ma.checkEq();
    }

}
