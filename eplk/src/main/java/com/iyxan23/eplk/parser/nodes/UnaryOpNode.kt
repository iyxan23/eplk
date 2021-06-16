package com.iyxan23.eplk.parser.nodes

import com.iyxan23.eplk.Token

// Probably a negative number, example: -1
data class UnaryOpNode(
    val tokenOperator: Token,
    val node: Node,
) : Node()