package com.iyxan23.eplk.nodes.control

import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.nodes.StatementsNode

class ElifNode(
    condition: Node,
    statements: StatementsNode,

    override val startPosition: Position,
    override val endPosition: Position,

) : BaseIfNode(condition, statements, startPosition, endPosition)