package org.andreschnabel.schedulevalidator;

public class Main {

    public static void cmdLineRunner(String[] args) throws Exception {
        if(args.length != 4) {
            System.out.println("Wrong number of arguments!");
            System.out.println("Usage: java -jar ScheduleValidator.jar QBWLBeispiel.sm myschedule.txt myprofit.txt MethodName");
            return;
        }

        ProjectWithOvertime p = new ProjectWithOvertime(args[0]);
        int[] sts = Utils.deserializeScheduleFromFile(args[1]);
        float oprofit = Utils.deserializeProfitFromFile(args[2]);
        String methodName = args[3];

        String validStr1 = new ScheduleProperties(p).isScheduleValid(sts) ? "valid" : "invalid";
        System.out.println("Schedule is " + validStr1 + "!");

        String validStr2 = (oprofit == p.profit(sts)) ? "valid" : "invalid";
        System.out.println("Profit is " + validStr2 + "!");

        Utils.appendLineToFile(args[0] + ";" + methodName + ";" + validStr1 + ";" + validStr2 + "\n", "checkresults.txt");
    }

    public static void main(String[] args) throws Exception {
        //cmdLineRunner(args);

        ProjectWithOvertime p = new ProjectWithOvertime("QBWLBeispiel.sm");
        System.out.println("");
    }

}
