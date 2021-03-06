package com.shc.sesl;

/**
 * @author Sri Harsha Chilakapati
 */
public enum TokenType
{
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, LEFT_BRACKET, RIGHT_BRACKET,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, COLON, QUESTION_MARK, MODULUS,
    SLASH, STAR, INCREMENT, DECREMENT, PLUS_EQUALS, MINUS_EQUALS, STAR_EQUALS,
    SLASH_EQUALS,

    NOT, NOT_EQUALS, EQUALS, EQUAL_TO, GREATER, GREATER_EQUALS,
    LESSER, LESSER_EQUALS,

    BITWISE_OR, BITWISE_AND, BITWISE_XOR, LEFT_SHIFT, RIGHT_SHIFT,
    LOGICAL_OR, LOGICAL_AND, LOGICAL_XOR, BITWISE_OR_EQUALS, BITWISE_AND_EQUALS,
    BITWISE_XOR_EQUALS, LEFT_SHIFT_EQUALS, RIGHT_SHIFT_EQUALS, LOGICAL_OR_EQUALS,
    LOGICAL_AND_EQUALS, LOGICAL_XOR_EQUALS,

    IDENTIFIER, INTEGER_VALUE, FLOAT_VALUE, TRUE, FALSE,

    PACKAGE, IMPORT, SHADER, CLASS, STRUCT, EXTENDS, IN, PASS, OUT, IF, ELSE, SWITCH,
    CASE, DEFAULT, BREAK, CONTINUE, DO, WHILE, FOR, THIS, SUPER, NATIVE, OPERATOR, RETURN,
    DISCARD, CONST, UNIFORM, VERT, FRAG, PUBLIC, PRIVATE, STATIC, DEPRECATED,

    ASM, UNION, ENUM, TYPEDEF, TEMPLATE, PACKED, GOTO, INLINE, NOINLINE, VOLATILE, EXTERN,
    EXTERNAL, INTERFACE, LONG, SHORT, DOUBLE, HALF, FIXED, UNSIGNED, INPUT, OUTPUT, SIZEOF,
    CAST, NAMESPACE, USING, INOUT, LAYOUT, PRECISION, ATTRIBUTE, VARYING, LOWP, MEDIUMP,
    HIGHP, CENTROID, INVARIANT, FLAT, SMOOTH, NOPERSPECTIVE, COMMON, PARTITION, ACTIVE,
    SUPERP, FILTER, ROW_MAJOR, PATCH, SAMPLE, SUBROUTINE, UINT, TESSC, TESSE, GEOM, BUFFER,
    SHARED, COHERENT, RESTRICT, READONLY, WRITEONLY, PRECISE, COMPUTE, RESOURCE,

    EOF
}
