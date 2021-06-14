package com.iyxan23.eplk.parser.nodes

import com.iyxan23.eplk.Token

data class BinOpNode(
    val left_node: Token,
    val op_node: Token,
    val right_node: Token,
)
