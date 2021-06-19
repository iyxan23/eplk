package com.iyxan23.eplk

enum class Tokens {
    PLUS,
    MINUS,
    MUL,
    DIV,
    POW,

    EQUAL,

    PAREN_OPEN,
    PAREN_CLOSE,
    STRING_LITERAL,
    INT_LITERAL,
    FLOAT_LITERAL,

    IDENTIFIER,
    KEYWORD,

    EOF, // End Of File
}