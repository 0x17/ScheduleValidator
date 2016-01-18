package org.andreschnabel.schedulevalidator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class Utils {
    public static ArrayList<Integer> intParts(String line) {
        String[] parts = line.split(" ");
        ArrayList<Integer> res = new ArrayList<>();
        for (String part : parts)
            if(part.trim().length() > 0)
                res.add(Integer.parseInt(part));
        return res;
    }

    public static int extractInt(String line) {
        return Integer.parseInt(line.split(":")[1].trim());
    }

    public static int[] deserializeScheduleFromFile(String filename) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        int[] sts = new int[lines.size()];
        for(String line : lines) {
            String[] parts = line.split("->");
            sts[Integer.valueOf(parts[0])] = Integer.valueOf(parts[1]);
        }
        return sts;
    }

    public static float deserializeProfitFromFile(String filename) throws Exception {
        return Float.valueOf(Files.readAllLines(Paths.get(filename)).get(0));
    }

    public static void appendLineToFile(String line, String filename) throws Exception {
        List<String> lines = new LinkedList<>();
        lines.add(line);
        Files.write(Paths.get(filename), lines, StandardOpenOption.APPEND);
    }

    public static float pow(float base, float exp) {
        return (float)Math.pow(base, exp);
    }
}
