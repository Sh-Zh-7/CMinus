package io.github.cminus;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static io.github.cminus.TestUtils.getFromFile;

public class IntTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final String legalFilePath = "src/test/resources/advance/int.cm";
    private final String legalExpectedOutPath = "src/test/resources/advance/expected/int.out";
    private final String illegalFilePath = "src/test/resources/advance/illegal_int.cm";
    private final String illegalExpectedOutputPath = "src/test/resources/advance/expected/illegal_int.out";

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void legalIntTest() throws IOException {
        String expected = getFromFile(legalExpectedOutPath);
        CMinusCompiler.runFromFile(legalFilePath);
        assert expected != null;
        Assert.assertEquals(expected.trim(), outContent.toString().trim());
    }

    @Test
    public void illegalIntTest() throws IOException {
        String expected = getFromFile(illegalExpectedOutputPath);
        CMinusCompiler.runFromFile(illegalFilePath);
        assert expected != null;
        Assert.assertEquals(expected.trim(), errContent.toString().trim().replace("\r", ""));
    }
}
