package com.iyxan23.eplk.parser.nodes

import com.iyxan23.eplk.lexer.models.Token

// A single number operation, example: -1
data class UnaryOpNode(
    val tokenOperator: Token,
    val node: Node,
) : Node()