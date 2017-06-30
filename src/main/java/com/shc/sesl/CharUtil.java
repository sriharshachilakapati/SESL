package com.shc.sesl;

/**
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
