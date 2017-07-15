package com.shc.sesl.test.preprocessor;

import com.shc.sesl.Preprocessor;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class IfTest
{
    @Test
    public void ifTest1()
    {
        final String source = "#if 3 == 3\nYes!!!\n#endif";
        final String expected = "\nYes!!!\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void ifTest2()
    {
        final String source = "#if 3 == 3\nYes!!!\n#else\nNothing macha!!\n#endif";
        final String expected = "\nYes!!!\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void ifTest3()
    {
        final String source = "#if 3 == 3\nYes!!!\n#elif 3 == 3\nAbbacha!!\n#endif";
        final String expected = "\nYes!!!\n\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void ifTest4()
    {
        final String source = "#if 3 != 3\nYes!!!\n#endif";
        final String expected = "\n\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void ifTest5()
    {
        final String source = "#if 3 != 3\nYes!!!\n#else\nNothing macha!!\n#endif";
        final String expected = "\n\n\nNothing macha!!\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }

    @Test
    public void ifTest6()
    {
        final String source = "#if 3 != 3\nYes!!!\n#elif 3 == 3\nAbbacha!!\n#endif";
        final String expected = "\n\n\nAbbacha!!\n";

        assertEquals(expected, Preprocessor.process(source, new HashMap<>()));
    }
}
