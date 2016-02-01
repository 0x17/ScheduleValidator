package org.andreschnabel.schedulevalidator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
        return Float.valueOf(Files.readAllLines(Paths.get(filename)).get(0));
    }

    public static void appendLineToFile(String line, String filename) throws Exception {
        Files.write(Paths.get(filename), line.getBytes(), StandardOpenOption.APPEND);
    }

    public static float pow(float base, float exp) {
        return (float)Math.pow(base, exp);
    }
}
