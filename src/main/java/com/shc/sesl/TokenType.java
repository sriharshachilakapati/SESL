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

    CONST, UNIFORM, IN, OUT, PASS, SHADER, VERT, FRAG, EXTENDS, CLASS, STRUCT,
    IF, ELSE, DO, WHILE, FOR, SWITCH, BREAK, CONTINUE, DEFAULT, PP_IFDEF, PP_DEFINE,
    PP_ENDIF, PP_IFNDEF, PP_ELSE, RETURN, PACKAGE, IMPORT, PUBLIC, PRIVATE, THIS, SUPER, DISCARD,
    NATIVE,

//    INT, FLOAT, VOID, BOOLEAN,
//    MAT2, MAT3, MAT4, DMAT2, DMAT3, DMAT4,
//    MAT2x2, MAT2x3, MAT2x4, DMAT2x2, DMAT2x3, DMAT2x4,
//    MAT3x2, MAT3x3, MAT3x4, DMAT3x2, DMAT3x3, DMAT3x4,
//    MAT4x2, MAT4x3, MAT4x4, DMAT4x2, DMAT4x3, DMAT4x4,
//    VEC2, VEC3, VEC4, IVEC2, IVEC3, IVEC4, BVEC2, BVEC3, BVEC4, DVEC2, DVEC3, DVEC4,
//    UINT, UVEC2, UVEC3, UVEC4,
//    SAMPLER1D, SAMPLER2D, SAMPLER3D, SAMPLER_CUBE,
//    SAMPLER1D_SHADOW, SAMPLER2D_SHADOW, SAMPLER_CUBE_SHADOW,
//    SAMPLER1D_ARRAY, SAMPLER2D_ARRAY,
//    SAMPLER1D_ARRAY_SHADOW, SAMPLER2D_ARRAY_SHADOW,
//    ISAMPLER1D, ISAMPLER2D, ISAMPLER3D, ISAMPLER_CUBE,
//    ISAMPLER1D_ARRAY, ISAMPLER2D_ARRAY,
//    USAMPLER1D, USAMPLER2D, USAMPLER3D, USAMPLER_CUBE,
//    USAMPLER1D_ARRAY, USAMPLER2D_ARRAY,
//    SAMPLER2D_RECT, SAMPLER2D_RECT_SHADOW, ISAMPLER2D_RECT, USAMPLER2D_RECT,
//    SAMPLER_BUFFER, ISAMPLER_BUFFER, USAMPLER_BUFFER,
//    SAMPLER2DMS, ISAMPLER2DMS, USAMPLER2DMS,
//    SAMPLER2DMS_ARRAY, ISAMPLER2DMS_ARRAY, USAMPLER2DMS_ARRAY,
//    SAMPLER_CUBE_ARRAY, SAMPLER_CUBE_ARRAY_SHADOW, ISAMPLER_CUBE_ARRAY, USAMPLER_CUBE_ARRAY,

    EOF
}
