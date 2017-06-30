package com.shc.sesl;

/**
 * @author Sri Harsha Chilakapati
 */
public class SESL
{
    private static boolean hadError = false;

    public static void main(String[] args)
    {
        String test = "+ -1234 123.45 - /= != ! = == /* comment\nmultiline */ ++ -- // line comment\n +";
        new Scanner(test).scanTokens().forEach(System.out::println);
    }

    static void error(int line, String message)
    {
        report(line, "", message);
    }

    private static void report(int line, String where, String message)
    {
        System.err.println(String.format("[line %d] Error %s: %s", line, where, message));
        hadError = true;
    }
}
