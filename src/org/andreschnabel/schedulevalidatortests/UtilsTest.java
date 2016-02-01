package org.andreschnabel.schedulevalidatortests;

import org.andreschnabel.schedulevalidator.Utils;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class UtilsTest {

    @org.junit.Test
    public void testIntParts() throws Exception {
        assertArrayEquals(new Integer[] { 3, 1, 4, 1, 5926, 53, 5 },
                Utils.intParts("  3    1    4    1  5926    53 5  \n").toArray(new Integer[7]));
    }

    @org.junit.Test
    public void testExtractInt() throws Exception {
        assertEquals(23, Utils.extractInt(" someKey    :   23  \n"));
    }

    @org.junit.Test
    public void testDeserializeScheduleFromFile() throws Exception {
        TestHelpers.runTestOnTempFile("testDeserializeScheduleFromFile", "1->0\n2->1\n3->2\n4->4\n5->8", (fn) ->
                assertArrayEquals(new int[] { 0, 1, 2, 4, 8 }, Utils.deserializeScheduleFromFile(fn)));
    }

    @org.junit.Test
    public void testDeserializeProfitFromFile() throws Exception {
        TestHelpers.runTestOnTempFile("testDeserializeProfitFromFile", "23.5\n", (fn) -> assertEquals(23.5f, Utils.deserializeProfitFromFile(fn), 0.001f));
    }

    @org.junit.Test
    public void testAppendLineToFile() throws Exception {
        TestHelpers.runTestOnTempFile("testAppendLineToFile", "First line\n", (fn) -> {
            Utils.appendLineToFile("Second line\n", fn);
            byte[] actualContent = Files.readAllBytes(Paths.get(fn));
            assertArrayEquals("First line\nSecond line\n".getBytes(), actualContent);
        });
    }

    @org.junit.Test
    public void testPow() throws Exception {
        assertEquals(4.0f, Utils.pow(2.0f, 2.0f), 0.001f);
    }

    @Test
    public void testDeserializeScheduleFromLines() {
        int[] sts = Utils.deserializeScheduleFromLines(Arrays.asList("    1->0 ", " 2->4    ", "    "));
        int[] expSts = { 0, 4 };
        assertArrayEquals(expSts, sts);
    }
}