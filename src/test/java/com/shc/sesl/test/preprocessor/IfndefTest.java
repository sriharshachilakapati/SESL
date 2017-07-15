package com.shc.sesl.test.preprocessor;

import com.shc.sesl.Preprocessor;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class IfndefTest
{
    @Test
    public void testIfndef1()
    {
        final String source = "#define HELLO\n#ifndef HELLO\nHello World!\n#endif";
        final String expected = "\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef2()
    {
        final String source = "#ifndef HELLO\nHello World!\n#endif";
        final String expected = "\nHello World!\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef3()
    {
        final String source = "#ifndef HELLO\nHello World!\n#else\nHowdy!!\n#endif";
        final String expected = "\nHello World!\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef4()
    {
        final String source = "#ifndef HELLO\nHello World!\n#elif 3 == 3\nHowdy!!\n#endif";
        final String expected = "\nHello World!\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef5()
    {
        final String source = "#ifndef HELLO\nHello\n#elif 3 == 2\nAwful!!\n#elif 5 == 5\nHowdy!!\n#else\nDummy!\n#endif";
        final String expected = "\nHello\n\n\n\n\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef6()
    {
        final String source = "#ifndef HELLO\nHello\n#elif 3 == 2\nAwful!!\n#elif 5 == 5\nHowdy!!\n#elif 2 == 2\nDummy\n#else\nDummy!\n#endif";
        final String expected = "\nHello\n\n\n\n\n\n\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef7()
    {
        final String source = "#define HELLO\n#ifndef HELLO\nHello!\n#endif";
        final String expected = "\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef8()
    {
        final String source = "#define HELLO\n#ifndef HELLO\nAwful!\n#else\nHello!!\n#endif";
        final String expected = "\n\n\n\nHello!!\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef9()
    {
        final String source = "#define HELLO\n#ifndef HELLO\nHello World!\n#elif 3 == 3\nHowdy!!\n#endif";
        final String expected = "\n\n\n\nHowdy!!\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef10()
    {
        final String source = "#define HELLO\n#ifndef HELLO\nHello\n#elif 3 == 2\nAwful!!\n#elif 5 == 5\nHowdy!!\n#else\nDummy!\n#endif";
        final String expected = "\n\n\n\n\n\nHowdy!!\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef11()
    {
        final String source = "#define HELLO\n#ifndef HELLO\nHello\n#elif 3 == 2\nAwful!!\n#elif 5 == 5\nHowdy!!\n#elif 2 == 2\nDummy\n#else\nDummy!\n#endif";
        final String expected = "\n\n\n\n\n\nHowdy!!\n\n\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef12()
    {
        final String source = "#ifndef FOO\n#define FOO\n#ifdef FOO\nHello World!!\n#endif\n#else\nAwful\n#endif";
        final String expected = "\n\n\nHello World!!\n\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void testIfndef13()
    {
        final String source = "#ifndef FOO\n#define FOO\n#ifdef FOO\nHello World!!\n#endif\nAwful\n#elif 2 == 2\nAwful\n#endif";
        final String expected = "\n\n\nHello World!!\n\nAwful\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }
}
