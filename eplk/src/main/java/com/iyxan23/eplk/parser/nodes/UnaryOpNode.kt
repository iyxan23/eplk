package com.iyxan23.eplk.parser.nodes

import com.iyxan23.eplk.Token

data class UnaryOpNode(
    val op_token: Token,
    val node: NumberNode,
)