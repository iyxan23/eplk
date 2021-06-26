package com.iyxan23.eplk

enum class Tokens {
    // Arithmetic tokens
    PLUS,
    MINUS,
    MUL,
    DIV,
    POW,

    PLUS_EQUAL,
    MINUS_EQUAL,
    MUL_EQUAL,
    DIV_EQUAL,

    DOUBLE_PLUS,
    DOUBLE_MINUS,

    // Comparison tokens
    DOUBLE_EQUALS,
    GREATER_THAN,
    LESSER_THAN,
    GREATER_OR_EQUAL_THAN,
    LESSER_OR_EQUAL_THAN,
    NOT,
    NOT_EQUAL,
    AND,
    OR,

    TRUE,
    FALSE,

    IF,
    ELIF,
    ELSE,
    BRACES_OPEN,  // {
    BRACES_CLOSE, // }

    FOR,
    SEMICOLON,

    FUN,
    COMMA,
    ARROW,

    WHILE,

    EQUAL,

    PAREN_OPEN,  // (
    PAREN_CLOSE, // )

    STRING_LITERAL,
    INT_LITERAL,
    FLOAT_LITERAL,

    BRACKET_OPEN,  // [
    BRACKET_CLOSE, // ]

    IDENTIFIER,
    KEYWORD,

    EOF, // End Of File
}