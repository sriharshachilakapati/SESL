package com.shc.sesl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shc.sesl.CharUtil.*;
import static com.shc.sesl.TokenType.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class Scanner
{
    private static final Map<String, TokenType> keywords = new HashMap<>();
    private static final Map<String, TokenType> reserved = new HashMap<>();

    private final String      source;
    private final List<Token> tokens;

    private int start   = 0;
    private int current = 0;
    private int line    = 1;
    private int lineStart = 0;

    static
    {
        keywords.put("package", PACKAGE);
        keywords.put("import", IMPORT);
        keywords.put("shader", SHADER);
        keywords.put("class", CLASS);
        keywords.put("struct", STRUCT);
        keywords.put("extends", EXTENDS);
        keywords.put("in", IN);
        keywords.put("pass", PASS);
        keywords.put("out", OUT);
        keywords.put("if", IF);
        keywords.put("else", ELSE);
        keywords.put("switch", SWITCH);
        keywords.put("case", CASE);
        keywords.put("default", DEFAULT);
        keywords.put("break", BREAK);
        keywords.put("continue", CONTINUE);
        keywords.put("do", DO);
        keywords.put("while", WHILE);
        keywords.put("for", FOR);
        keywords.put("this", THIS);
        keywords.put("super", SUPER);
        keywords.put("native", NATIVE);
        keywords.put("operator", OPERATOR);
        keywords.put("return", RETURN);
        keywords.put("discard", DISCARD);
        keywords.put("const", CONST);
        keywords.put("uniform", UNIFORM);
        keywords.put("vert", VERT);
        keywords.put("frag", FRAG);
        keywords.put("public", PUBLIC);
        keywords.put("private", PRIVATE);
        keywords.put("static", STATIC);
        keywords.put("deprecated", DEPRECATED);

        keywords.put("true", TRUE);
        keywords.put("false", FALSE);

        reserved.put("asm", ASM);
        reserved.put("union", UNION);
        reserved.put("enum", ENUM);
        reserved.put("typedef", TYPEDEF);
        reserved.put("template", TEMPLATE);
        reserved.put("packed", PACKED);
        reserved.put("goto", GOTO);
        reserved.put("inline", INLINE);
        reserved.put("noinline", NOINLINE);
        reserved.put("volatile", VOLATILE);
        reserved.put("extern", EXTERN);
        reserved.put("external", EXTERNAL);
        reserved.put("interface", INTERFACE);
        reserved.put("long", LONG);
        reserved.put("short", SHORT);
        reserved.put("double", DOUBLE);
        reserved.put("half", HALF);
        reserved.put("fixed", FIXED);
        reserved.put("unsigned", UNSIGNED);
        reserved.put("input", INPUT);
        reserved.put("output", OUTPUT);
        reserved.put("sizeof", SIZEOF);
        reserved.put("cast", CAST);
        reserved.put("namespace", NAMESPACE);
        reserved.put("using", USING);
        reserved.put("inout", INOUT);
        reserved.put("layout", LAYOUT);
        reserved.put("precision", PRECISION);
        reserved.put("attribute", ATTRIBUTE);
        reserved.put("varying", VARYING);
        reserved.put("lowp", LOWP);
        reserved.put("mediump", MEDIUMP);
        reserved.put("highp", HIGHP);
        reserved.put("centroid", CENTROID);
        reserved.put("invariant", INVARIANT);
        reserved.put("flat", FLAT);
        reserved.put("smooth", SMOOTH);
        reserved.put("noperspective", NOPERSPECTIVE);
        reserved.put("common", COMMON);
        reserved.put("partition", PARTITION);
        reserved.put("active", ACTIVE);
        reserved.put("superp", SUPERP);
        reserved.put("filter", FILTER);
        reserved.put("row_major", ROW_MAJOR);
        reserved.put("patch", PATCH);
        reserved.put("sample", SAMPLE);
        reserved.put("subroutine", SUBROUTINE);
        reserved.put("uint", UINT);
        reserved.put("tessc", TESSC);
        reserved.put("tesse", TESSE);
        reserved.put("geom", GEOM);
        reserved.put("buffer", BUFFER);
        reserved.put("shared", SHARED);
        reserved.put("coherent", COHERENT);
        reserved.put("restrict", RESTRICT);
        reserved.put("readonly", READONLY);
        reserved.put("writeonly", WRITEONLY);
        reserved.put("precise", PRECISE);
        reserved.put("compute", COMPUTE);
        reserved.put("resource", RESOURCE);
    }

    public Scanner(String source)
    {
        this.source = source;
        this.tokens = new ArrayList<>();
    }

    public List<Token> scanTokens()
    {
        while (!isAtEnd())
        {
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line, 0));
        return tokens;
    }

    private void scanToken()
    {
        char c = advance();

        switch (c)
        {
            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                line++;
                lineStart = current;
                break;

            case '(': addToken(LEFT_PAREN);    break;
            case ')': addToken(RIGHT_PAREN);   break;
            case '[': addToken(LEFT_BRACKET);  break;
            case ']': addToken(RIGHT_BRACKET); break;
            case '{': addToken(LEFT_BRACE);    break;
            case '}': addToken(RIGHT_BRACE);   break;
            case ';': addToken(SEMICOLON);     break;
            case ':': addToken(COLON);         break;
            case '?': addToken(QUESTION_MARK); break;
            case ',': addToken(COMMA);         break;
            case '.': addToken(DOT);           break;
            case '%': addToken(MODULUS);       break;

            case '|':
            {
                if (match('|'))
                    addToken(match('=') ? LOGICAL_OR_EQUALS : LOGICAL_OR);
                else
                    addToken(match('=') ? BITWISE_OR_EQUALS : BITWISE_OR);
                break;
            }

            case '&':
            {
                if (match('&'))
                    addToken(match('=') ? LOGICAL_AND_EQUALS : LOGICAL_AND);
                else
                    addToken(match('=') ? BITWISE_AND_EQUALS : BITWISE_AND);
                break;
            }

            case '^':
            {
                if (match('^'))
                    addToken(match('=') ? LOGICAL_XOR_EQUALS : LOGICAL_XOR);
                else
                    addToken(match('=') ? BITWISE_XOR_EQUALS : BITWISE_XOR);
                break;
            }

            case '+':
            {
                if (match('+'))
                    addToken(INCREMENT);
                else
                    addToken(match('=') ? PLUS_EQUALS : PLUS);
                break;
            }

            case '-':
            {
                if (match('-'))
                    addToken(DECREMENT);
                else
                    addToken(match('=') ? MINUS_EQUALS : MINUS);
                break;
            }

            case '*': addToken(match('=') ? STAR_EQUALS : STAR); break;
            case '!': addToken(match('=') ? NOT_EQUALS : NOT);   break;
            case '=': addToken(match('=') ? EQUALS : EQUAL_TO);  break;

            case '<':
            {
                if (match('<'))
                    addToken(match('=') ? LEFT_SHIFT_EQUALS : LEFT_SHIFT);
                else
                    addToken(match('=') ? LESSER_EQUALS : LESSER);
                break;
            }

            case '>':
            {
                if (match('>'))
                    addToken(match('=') ? RIGHT_SHIFT_EQUALS : RIGHT_SHIFT);
                else
                    addToken(match('=') ? GREATER_EQUALS : GREATER);
                break;
            }

            case '/':
            {
                if (match('/'))
                {
                    while (peek() != '\n' && !isAtEnd())
                        advance();
                }
                else if (match('*'))
                {
                    while (!isAtEnd())
                    {
                        advance();

                        if (peek() == '\n')
                            line++;

                        if (peek() == '*')
                        {
                            advance();
                            if (match('/'))
                                break;
                        }
                    }
                }
                else
                    addToken(match('=') ? SLASH_EQUALS : SLASH);

                break;
            }

            default:
                if (isDigit(c))
                    scanNumber();
                else if (isAlpha(c))
                    scanWord();
                else
                    SESL.error(line, start, "Unexpected character");
                break;
        }
    }

    private void scanNumber()
    {
        TokenType type = INTEGER_VALUE;

        while (isDigit(peek()))
            advance();

        if (peek() == '.')
        {
            type = FLOAT_VALUE;
            advance();

            while (isDigit(peek()))
                advance();
        }

        Object literal;

        if (type == INTEGER_VALUE)
            literal = Integer.parseInt(source.substring(start, current));
        else
            literal = Float.parseFloat(source.substring(start, current));

        addToken(type, literal);
    }

    private void scanWord()
    {
        while (isAlphaNumeric(peek()))
            advance();

        String lexeme = source.substring(start, current);
        TokenType type = keywords.getOrDefault(lexeme, reserved.getOrDefault(lexeme, IDENTIFIER));

        addToken(type, lexeme);
    }

    private boolean match(char expected)
    {
        if (isAtEnd())
            return false;

        if (source.charAt(current) != expected)
            return false;

        current++;
        return true;
    }

    private char peek()
    {
        return isAtEnd() ? '\0' : source.charAt(current);
    }

    private char advance()
    {
        current++;
        return source.charAt(current - 1);
    }

    private void addToken(TokenType tokenType)
    {
        addToken(tokenType, null);
    }

    private void addToken(TokenType tokenType, Object literal)
    {
        String text = source.substring(start, current);
        tokens.add(new Token(tokenType, text, literal, line, start - lineStart));
    }

    private boolean isAtEnd()
    {
        return current >= source.length();
    }
}
