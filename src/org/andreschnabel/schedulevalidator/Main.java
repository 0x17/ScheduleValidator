package org.andreschnabel.schedulevalidator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static final String SKIP_FILE = "plsdoskip";

    public static void cmdLineRunner(String[] args) throws Exception {
        if(args.length != 2) {
            System.out.println("Wrong number of arguments!");
            System.out.println("Usage: java -jar ScheduleValidator.jar j30_1secs/ QBWLBeispiel.sm");
            return;
        }

        String outPath = args[0].equals(".") ? "" : args[0];

        Path sfPath = Paths.get(outPath + SKIP_FILE);
        if(!Files.exists(sfPath))
            Files.createFile(sfPath);

        ProjectWithOvertime p = new ProjectWithOvertime(args[1]);
        int[] sts = Utils.deserializeScheduleFromFile(outPath + "myschedule.txt");

        boolean schedValid = new ScheduleProperties(p).isScheduleValid(sts);

        if(!schedValid) {
            System.out.print("Schedule infeasible: ");
            ScheduleProperties props = new ScheduleProperties(p);
            if(!props.orderFeasible(sts)) {
                System.out.println("Schedule is not order feasible: " + props.orderInfeasibilityCause(sts));
            }
            if(!props.resourceFeasible(sts)) {
                System.out.println("Schedule is not resource feasible!");
            }
        }

        float oprofit = Utils.deserializeProfitFromFile(outPath + "myprofit.txt");

        float EPSILON = 0.1f;
        boolean profitValid = Math.abs(oprofit - p.profit(sts)) < EPSILON;

        String validStr1 = schedValid ? "valid" : "invalid";
        System.out.println("Schedule is " + validStr1 + "!");

        String validStr2 = profitValid ? "valid" : "invalid";
        System.out.println("Profit is " + validStr2 + "!");
        System.out.println("Expected profit = " + p.profit(sts) + "; Actual profit = " + oprofit);

        if(schedValid && profitValid)
            Files.deleteIfExists(sfPath);
    }

    public static void main(String[] args) throws Exception {
        cmdLineRunner(args);
    }

}
