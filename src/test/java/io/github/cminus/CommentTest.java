package io.github.cminus;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static io.github.cminus.TestUtils.getFromFile;

public class CommentTest {
    private final String legalFilePath = "src/test/resources/advance/comment.cm";
    private final String legalExpectedOutPath = "src/test/resources/advance/expected/comment.out";
    private final String illegalFilePath1 = "src/test/resources/advance/illegal_comment1.cm";
    private final String illegalExpectedOutputPath1 = "src/test/resources/advance/expected/illegal_comment1.out";
    private final String illegalFilePath2 = "src/test/resources/advance/illegal_comment2.cm";
    private final String illegalExpectedOutputPath2 = "src/test/resources/advance/expected/illegal_comment2.out";

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
    public void legalCommentTest() throws IOException {
        String expected = getFromFile(legalExpectedOutPath);
        CMinusCompiler.runFromFile(legalFilePath);
        assert expected != null;
        Assert.assertEquals(expected.trim(), outContent.toString().trim());
    }

    @Test
    public void illegalCommentTest1() throws IOException {
        String expected = getFromFile(illegalExpectedOutputPath1);
        CMinusCompiler.runFromFile(illegalFilePath1);
        assert expected != null;
        Assert.assertEquals(expected.trim(), errContent.toString().trim().replace("\r", ""));
    }

    @Test
    public void illegalCommentTest2() throws IOException {
        String expected = getFromFile(illegalExpectedOutputPath2);
        CMinusCompiler.runFromFile(illegalFilePath2);
        assert expected != null;
        Assert.assertEquals(expected.trim(), errContent.toString().trim().replace("\r", ""));
    }
}
