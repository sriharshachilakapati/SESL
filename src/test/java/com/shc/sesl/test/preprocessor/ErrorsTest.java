package com.shc.sesl.test.preprocessor;

import com.shc.sesl.Preprocessor;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author Sri Harsha Chilakapati
 */
public class ErrorsTest
{
    @Test(expected = Preprocessor.PreprocessorException.class)
    public void testUnknownDirectiveError()
    {
        final String source = "#somethingelse";
        Preprocessor.process(source, new HashMap<>());
    }

    @Test(expected = Preprocessor.PreprocessorException.class)
    public void testMissingDirectiveError()
    {
        final String source = "#if 3 == 2";
        Preprocessor.process(source, new HashMap<>());
    }

    @Test(expected = Preprocessor.PreprocessorException.class)
    public void testMissingIfArgsError()
    {
        final String source = "#if";
        Preprocessor.process(source, new HashMap<>());
    }

    @Test(expected = Preprocessor.PreprocessorException.class)
    public void testMissingIfdefArgsError()
    {
        final String source = "#ifdef";
        Preprocessor.process(source, new HashMap<>());
    }

    @Test(expected = Preprocessor.PreprocessorException.class)
    public void testMissingIfndefArgsError()
    {
        final String source = "#ifndef";
        Preprocessor.process(source, new HashMap<>());
    }

    @Test(expected = Preprocessor.PreprocessorException.class)
    public void testDirectiveNotOnNewlineError()
    {
        final String source = "Hello #if";
        Preprocessor.process(source, new HashMap<>());
    }

    @Test(expected = Preprocessor.PreprocessorException.class)
    public void testMultipleDirectivesOnSameLineError()
    {
        final String source = "#if 3 == 3 Hello #endif";
        Preprocessor.process(source, new HashMap<>());
    }
}
