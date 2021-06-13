package io.github.cminus;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static io.github.cminus.TestUtils.getFromFile;

public class ExampleTest {
    private final String example1Path = "src/test/resources/examples/example1.cm";
    private final String example1ExpectedOutPath = "src/test/resources/examples/expected/example1.out";
    private final String example2Path = "src/test/resources/examples/example2.cm";
    private final String example2ExpectedOutPath = "src/test/resources/examples/expected/example2.out";
    private final String example3Path = "src/test/resources/examples/example3.cm";
    private final String example3ExpectedOutPath = "src/test/resources/examples/expected/example3.out";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

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
    public void example1Test() throws IOException {
        String expected = getFromFile(example1ExpectedOutPath);
        CMinusCompiler.runFromFile(example1Path);
        assert expected != null;
        Assert.assertEquals(expected.trim(), errContent.toString().trim());
    }

    @Test
    public void example2Test() throws IOException {
        String expected = getFromFile(example2ExpectedOutPath);
        CMinusCompiler.runFromFile(example2Path);
        assert expected != null;
        Assert.assertEquals(expected.trim(), errContent.toString().replace("\r", "").trim());
    }

    @Test
    public void example3Test() throws IOException {
        String expected = getFromFile(example3ExpectedOutPath);
        CMinusCompiler.runFromFile(example3Path);
        assert expected != null;
        Assert.assertEquals(expected.trim(), outContent.toString().trim());
    }

}
