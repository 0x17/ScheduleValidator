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

        String outPath = args[0];

        Path sfPath = Paths.get(outPath + SKIP_FILE);
        Files.createFile(sfPath);

        ProjectWithOvertime p = new ProjectWithOvertime(args[1]);
        int[] sts = Utils.deserializeScheduleFromFile(outPath + "myschedule.txt");

        boolean schedValid = new ScheduleProperties(p).isScheduleValid(sts);

        float oprofit = Utils.deserializeProfitFromFile(outPath + "myprofit.txt");

        float EPSILON = 0.1f;
        boolean profitValid = Math.abs(oprofit - p.profit(sts)) < EPSILON;

        String validStr1 = schedValid ? "valid" : "invalid";
        System.out.println("Schedule is " + validStr1 + "!");

        String validStr2 = profitValid ? "valid" : "invalid";
        System.out.println("Profit is " + validStr2 + "!");

        if(schedValid && profitValid)
            Files.deleteIfExists(sfPath);
    }

    public static void main(String[] args) throws Exception {
        cmdLineRunner(args);
    }

}
