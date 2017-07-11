package com.shc.sesl;

/**
 * A small utility class to check whether a character is a digit or alphabet or maybe
 * one of either. I could've used the standard methods of the {@link Character} class
 * but the issue is that they also support numbers in Devanagari scripts, which isn't
 * what we want at all.
 *
 * @author Sri Harsha Chilakapati
 */
class CharUtil
{
    static boolean isDigit(char ch)
    {
        return ch >= '0' && ch <= '9';
    }

    static boolean isAlpha(char ch)
    {
        return ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) || ch == '_';
    }

    static boolean isAlphaNumeric(char ch)
    {
        return isDigit(ch) || isAlpha(ch);
    }
}
