package com.shc.sesl.test.preprocessor;

import com.shc.sesl.Preprocessor;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class IfdefTest
{
    @Test
    public void testIfdef1()
    {
        final String source = "#define HELLO\n#ifdef HELLO\nHello World!\n#endif";
        final String expected = "\n\nHello World!\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef2()
    {
        final String source = "#ifdef HELLO\nHello World!\n#endif";
        final String expected = "\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef3()
    {
        final String source = "#ifdef HELLO\nHello World!\n#else\nHowdy!!\n#endif";
        final String expected = "\n\n\nHowdy!!\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef4()
    {
        final String source = "#ifdef HELLO\nHello World!\n#elif 3 == 3\nHowdy!!\n#endif";
        final String expected = "\n\n\nHowdy!!\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef5()
    {
        final String source = "#ifdef HELLO\nHello\n#elif 3 == 2\nAwful!!\n#elif 5 == 5\nHowdy!!\n#else\nDummy!\n#endif";
        final String expected = "\n\n\n\n\nHowdy!!\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef6()
    {
        final String source = "#ifdef HELLO\nHello\n#elif 3 == 2\nAwful!!\n#elif 5 == 5\nHowdy!!\n#elif 2 == 2\nDummy\n#else\nDummy!\n#endif";
        final String expected = "\n\n\n\n\nHowdy!!\n\n\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef7()
    {
        final String source = "#define HELLO\n#ifdef HELLO\nHello!\n#endif";
        final String expected = "\n\nHello!\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef8()
    {
        final String source = "#define HELLO\n#ifdef HELLO\nHello!\n#else\nAwful!!\n#endif";
        final String expected = "\n\nHello!\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef9()
    {
        final String source = "#define HELLO\n#ifdef HELLO\nHello World!\n#elif 3 == 3\nHowdy!!\n#endif";
        final String expected = "\n\nHello World!\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef10()
    {
        final String source = "#define HELLO\n#ifdef HELLO\nHello\n#elif 3 == 2\nAwful!!\n#elif 5 == 5\nHowdy!!\n#else\nDummy!\n#endif";
        final String expected = "\n\nHello\n\n\n\n\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef11()
    {
        final String source = "#define HELLO\n#ifdef HELLO\nHello\n#elif 3 == 2\nAwful!!\n#elif 5 == 5\nHowdy!!\n#elif 2 == 2\nDummy\n#else\nDummy!\n#endif";
        final String expected = "\n\nHello\n\n\n\n\n\n\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef12()
    {
        final String source = "#ifdef FOO\nAweful\n#else\n#define FOO\n#ifdef FOO\nHello World!!\n#endif\n#endif";
        final String expected = "\n\n\n\n\nHello World!!\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfdef13()
    {
        final String source = "#ifdef FOO\nAweful\n#elif 2 == 2\n#define FOO\n#ifdef FOO\nHello World!!\n#endif\n#endif";
        final String expected = "\n\n\n\n\nHello World!!\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }
}
