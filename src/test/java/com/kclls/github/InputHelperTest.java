package com.kclls.github;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InputHelperTest {
    InputHelper in;

    @Test
    public void getStringInputAcceptsValidString() {
        String text = "Sample text";
        ByteArrayInputStream s = new ByteArrayInputStream(text.getBytes());
        in = new InputHelper(s);
        Assertions.assertEquals("Sample text", in.getStringInput(), "Output should match sample text");
    }

    @Test
    public void getStringInputSplitsCorrectlyOnNewLine() {
        String text = "Line 1\nLine 2";
        ByteArrayInputStream s = new ByteArrayInputStream(text.getBytes());
        in = new InputHelper(s);
        Assertions.assertEquals("Line 1", in.getStringInput(), "First line should be Line 1");
        Assertions.assertEquals("Line 2", in.getStringInput(), "Second line should be Line 2");
    }

    @Test
    public void getIntInputAcceptsValidNumber() {
        String text = "5";
        ByteArrayInputStream s = new ByteArrayInputStream(text.getBytes());
        in = new InputHelper(s);
        Assertions.assertEquals(in.getIntInput(), 5, "Int returned should 5");
    }

    @Test
    public void getIntInputAcceptsNegativeNumbers() {
        // Note: Validation is done outside this class
        String text = "-1";
        ByteArrayInputStream s = new ByteArrayInputStream(text.getBytes());
        in = new InputHelper(s);
        Assertions.assertEquals(in.getIntInput(), -1, "Should return an int of -1");
    }

    @Test
    public void getIntInputSplitsCorrectlyOnNewLine() {
        String text = "5\n8";
        ByteArrayInputStream s = new ByteArrayInputStream(text.getBytes());
        in = new InputHelper(s);
        Assertions.assertEquals(in.getIntInput(), 5, "First value returned should 5");
        Assertions.assertEquals(in.getIntInput(), 8, "Second value returned should 8");
    }

    @Test
    public void getIntInputSkipsInvalidInput() {
        String text = "qwerty\n8";
        ByteArrayInputStream s = new ByteArrayInputStream(text.getBytes());
        in = new InputHelper(s);
        Assertions.assertEquals(in.getIntInput(), 8, "The only output should be 8");
    }

}
