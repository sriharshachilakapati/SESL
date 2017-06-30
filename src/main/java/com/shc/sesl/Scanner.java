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

    private final String      source;
    private final List<Token> tokens;

    private int start   = 0;
    private int current = 0;
    private int line    = 1;

    static
    {
        keywords.put("true", TRUE);
        keywords.put("false", FALSE);
        keywords.put("const", CONST);
        keywords.put("uniform", UNIFORM);
        keywords.put("in", IN);
        keywords.put("out", OUT);
        keywords.put("pass", PASS);
        keywords.put("shader", SHADER);
        keywords.put("vert", VERT);
        keywords.put("frag", FRAG);
        keywords.put("extends", EXTENDS);
        keywords.put("class", CLASS);
        keywords.put("struct", STRUCT);
        keywords.put("if", IF);
        keywords.put("else", ELSE);
        keywords.put("do", DO);
        keywords.put("while", WHILE);
        keywords.put("for", FOR);
        keywords.put("switch", SWITCH);
        keywords.put("break", BREAK);
        keywords.put("continue", CONTINUE);
        keywords.put("default", DEFAULT);
        keywords.put("#ifdef", PP_IFDEF);
        keywords.put("#define", PP_DEFINE);
        keywords.put("#endif", PP_ENDIF);
        keywords.put("#ifndef", PP_IFNDEF);
        keywords.put("#else", PP_ELSE);
        keywords.put("return", RETURN);
        keywords.put("package", PACKAGE);
        keywords.put("import", IMPORT);
        keywords.put("public", PUBLIC);
        keywords.put("private", PRIVATE);
        keywords.put("this", THIS);
        keywords.put("super", SUPER);
        keywords.put("DISCARD", DISCARD);
        keywords.put("NATIVE", NATIVE);
        keywords.put("int", INT);
        keywords.put("float", FLOAT);
        keywords.put("void", VOID);
        keywords.put("double", DOUBLE);
        keywords.put("boolean", BOOLEAN);
        keywords.put("mat2", MAT2);
        keywords.put("mat3", MAT3);
        keywords.put("mat4", MAT4);
        keywords.put("dmat2", DMAT2);
        keywords.put("dmat3", DMAT3);
        keywords.put("dmat4", DMAT4);

        keywords.put("mat2x2", MAT2x2);
        keywords.put("mat2x3", MAT2x3);
        keywords.put("mat2x4", MAT2x4);
        keywords.put("dmat2x2", DMAT2x2);
        keywords.put("dmat2x3", DMAT2x3);
        keywords.put("dmat2x4", DMAT2x4);

        keywords.put("mat3x2", MAT3x2);
        keywords.put("mat3x3", MAT3x3);
        keywords.put("mat3x4", MAT3x4);
        keywords.put("dmat3x2", DMAT3x2);
        keywords.put("dmat3x3", DMAT3x3);
        keywords.put("dmat3x4", DMAT3x4);

        keywords.put("mat4x2", MAT4x2);
        keywords.put("mat4x3", MAT4x3);
        keywords.put("mat4x4", MAT4x4);
        keywords.put("dmat4x2", DMAT4x2);
        keywords.put("dmat4x3", DMAT4x3);
        keywords.put("dmat4x4", DMAT4x4);

        keywords.put("vec2", VEC2);
        keywords.put("vec3", VEC3);
        keywords.put("vec4", VEC4);
        keywords.put("ivec2", IVEC2);
        keywords.put("ivec3", IVEC3);
        keywords.put("ivec4", IVEC4);
        keywords.put("bvec2", BVEC2);
        keywords.put("bvec3", BVEC3);
        keywords.put("bvec4", BVEC4);
        keywords.put("dvec2", DVEC2);
        keywords.put("dvec3", DVEC3);
        keywords.put("dvec4", DVEC4);

        keywords.put("uint", UINT);
        keywords.put("uvec2", UVEC2);
        keywords.put("uvec3", UVEC3);
        keywords.put("uvec4", UVEC4);

        keywords.put("sampler1D", SAMPLER1D);
        keywords.put("sampler2D", SAMPLER2D);
        keywords.put("sampler3D", SAMPLER3D);
        keywords.put("samplerCube", SAMPLER_CUBE);

        keywords.put("sampler1DShadow", SAMPLER1D_SHADOW);
        keywords.put("sampler2DShadow", SAMPLER2D_SHADOW);
        keywords.put("samplerCubeShadow", SAMPLER_CUBE_SHADOW);

        keywords.put("sampler1DArray", SAMPLER1D_ARRAY);
        keywords.put("sampler2DArray", SAMPLER2D_ARRAY);

        keywords.put("sampler1DArrayShadow", SAMPLER1D_ARRAY_SHADOW);
        keywords.put("sampler2DArrayShadow", SAMPLER2D_ARRAY_SHADOW);

        keywords.put("iSampler1D", ISAMPLER1D);
        keywords.put("iSampler2D", ISAMPLER2D);
        keywords.put("iSampler3D", ISAMPLER3D);
        keywords.put("iSamplerCube", ISAMPLER_CUBE);

        keywords.put("iSampler1DArray", ISAMPLER1D_ARRAY);
        keywords.put("iSampler2DArray", ISAMPLER2D_ARRAY);

        keywords.put("uSampler1D", USAMPLER1D);
        keywords.put("uSampler2D", USAMPLER2D);
        keywords.put("uSampler3D", USAMPLER3D);
        keywords.put("uSamplerCube", USAMPLER_CUBE);

        keywords.put("uSampler1DArray", USAMPLER1D_ARRAY);
        keywords.put("uSampler2DArray", USAMPLER2D_ARRAY);

        keywords.put("sampler2DRect", SAMPLER2D_RECT);
        keywords.put("sampler2DRectShadow", SAMPLER2D_RECT_SHADOW);
        keywords.put("iSampler2DRect", ISAMPLER2D_RECT);
        keywords.put("uSampler2DRect", USAMPLER2D_RECT);

        keywords.put("samplerBuffer", SAMPLER_BUFFER);
        keywords.put("iSamplerBuffer", ISAMPLER_BUFFER);
        keywords.put("uSamplerBuffer", USAMPLER_BUFFER);

        keywords.put("sampler2DMS", SAMPLER2DMS);
        keywords.put("iSampler2DMS", ISAMPLER2DMS);
        keywords.put("uSampler2DMS", USAMPLER2DMS);

        keywords.put("sampler2DMSArray", SAMPLER2DMS_ARRAY);
        keywords.put("iSampler2DMSArray", ISAMPLER2DMS_ARRAY);
        keywords.put("uSampler2DMSArray", USAMPLER2DMS_ARRAY);

        keywords.put("samplerCubeArray", SAMPLER_CUBE_ARRAY);
        keywords.put("samplerCubeArrayShadow", SAMPLER_CUBE_ARRAY_SHADOW);
        keywords.put("iSamplerCubeArray", ISAMPLER_CUBE_ARRAY);
        keywords.put("uSamplerCubeArray", USAMPLER_CUBE_ARRAY);
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

        tokens.add(new Token(EOF, "", null, line));
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
                break;

            case '#':
                scanWord();
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
                    SESL.error(line, "Unexpected character");
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

        addToken(type, source.substring(start, current));
    }

    private void scanWord()
    {
        while (isAlphaNumeric(peek()))
            advance();

        String lexeme = source.substring(start, current);
        TokenType type = keywords.getOrDefault(lexeme, IDENTIFIER);

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
        tokens.add(new Token(tokenType, text, literal, line));
    }

    private boolean isAtEnd()
    {
        return current >= source.length();
    }
}
