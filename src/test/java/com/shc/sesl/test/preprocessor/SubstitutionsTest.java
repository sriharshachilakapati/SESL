package com.shc.sesl.test.preprocessor;

import com.shc.sesl.Preprocessor;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class SubstitutionsTest
{
    @Test
    public void testSubstitution1()
    {
        final String source = "#define HELLO Hello World!\nHELLO";
        final String expected = "\nHello World!";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testSubstitution2()
    {
        final String source = "#define HELLO Hello\n#define WORLD World!\nHELLO WORLD";
        final String expected = "\n\nHello World!";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testSubstitution3()
    {
        final String source = "#define HELLO Hello\nHELLO\n#define HELLO World!\nHELLO";
        final String expected = "\nHello\n\nWorld!";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testSubstitution4()
    {
        final String source = "#define VAR Hello World!\nVAR\n#undef VAR\nVAR";
        final String expected = "\nHello World!\n\nVAR";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }
}
