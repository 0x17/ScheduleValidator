package org.andreschnabel.schedulevalidator;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void cmdLineRunner(String[] args) throws Exception {
        if(args.length != 4) {
            System.out.println("Wrong number of arguments!");
            System.out.println("Usage: java -jar ScheduleValidator.jar QBWLBeispiel.sm");
            return;
        }

        ProjectWithOvertime p = new ProjectWithOvertime(args[0]);
        int[] sts = Utils.deserializeScheduleFromFile("myschedule.txt");
        float oprofit = Utils.deserializeProfitFromFile("myprofit.txt");

        float epsilon = 0.1f;

        boolean schedValid = new ScheduleProperties(p).isScheduleValid(sts);
        boolean profitValid = Math.abs(oprofit - p.profit(sts)) < epsilon;

        String validStr1 = schedValid ? "valid" : "invalid";
        System.out.println("Schedule is " + validStr1 + "!");

        String validStr2 = profitValid ? "valid" : "invalid";
        System.out.println("Profit is " + validStr2 + "!");

        if(!schedValid || !profitValid)
            Files.createFile(Paths.get("plsdoskip"));
    }

    public static void main(String[] args) throws Exception {
        cmdLineRunner(args);
    }

}
