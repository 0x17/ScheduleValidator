package org.andreschnabel.schedulevalidatortests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public final class TestHelpers {

    public static String TEST_FILE = "MiniBeispiel.sm";

    @FunctionalInterface
    public interface CheckedConsumer<T> {
        void accept(T t) throws Exception;
    }

    public static void runTestOnTempFile(String tmpFilename, String contents, CheckedConsumer<String> testFunc) throws Exception {
        Path testfp = Paths.get(tmpFilename);
        assertFalse(Files.exists(testfp));
        Files.write(testfp, contents.getBytes(), StandardOpenOption.CREATE_NEW);
        try {
            testFunc.accept(tmpFilename);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        Files.delete(testfp);
    }

}
