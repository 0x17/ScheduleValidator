package org.andreschnabel.schedulevalidator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Utils {
    public static ArrayList<Integer> intParts(String line) {
        return Utils.intParts(line, " ");
    }

    public static ArrayList<Integer> intParts(String line, String sep) {
        String[] parts = line.split(sep);
        ArrayList<Integer> res = new ArrayList<>();
        for (String part : parts)
            if(part.trim().length() > 0)
                res.add(Integer.parseInt(part));
        return res;
    }

    public static Integer[] intPartsArr(String line) {
        ArrayList<Integer> al = intParts(line);
        return al.toArray(new Integer[0]);
    }

    public static int extractInt(String line) {
        return Integer.parseInt(line.split(":")[1].trim());
    }

    public static int[] deserializeScheduleFromLines(List<String> lines) {
        int[] sts = new int[(int)lines.stream().filter((line) -> line.contains("->")).count()];
        for(String line : lines) {
            if(!line.contains("->")) continue;
            String[] parts = line.trim().split("->");
            sts[Integer.valueOf(parts[0])-1] = Integer.valueOf(parts[1]);
        }
        return sts;
    }

    public static int[] deserializeScheduleFromFile(String filename) throws Exception {
        return deserializeScheduleFromLines(Files.readAllLines(Paths.get(filename)));
    }

    public static float deserializeProfitFromFile(String filename) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        return lines.get(0).contains("infes") ? Float.NaN : Float.valueOf(lines.get(0));
    }

    public static void appendLineToFile(String line, String filename) throws Exception {
        Files.write(Paths.get(filename), line.getBytes(), StandardOpenOption.APPEND);
    }

    public static float pow(float base, float exp) {
        return (float)Math.pow(base, exp);
    }

    public static int[] integerToIntArray(Integer[] values) {
        return Arrays.stream(values).mapToInt(i->i).toArray();
    }
}
