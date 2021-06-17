package com.iyxan23.eplk.parser.nodes

import com.iyxan23.eplk.lexer.models.Token

// Simply a number, example: 1
data class IntegerNode(
    val number: Token
) : Node()