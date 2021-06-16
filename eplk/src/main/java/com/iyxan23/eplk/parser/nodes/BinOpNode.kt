package com.iyxan23.eplk.parser.nodes

import com.iyxan23.eplk.Token

// An operation, example: 1 + 1
data class BinOpNode(
    val leftNode: Node,
    val operatorToken: Token,
    val rightNode: Node,
) : Node()