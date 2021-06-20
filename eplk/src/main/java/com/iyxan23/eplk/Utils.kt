package com.iyxan23.eplk

import com.iyxan23.eplk.nodes.*
import com.iyxan23.eplk.nodes.variable.VarAccessNode
import com.iyxan23.eplk.nodes.variable.VarDeclarationNode

object Utils {
    fun prettyPrintNode(node: Node, indentation: Int = 0, indentationAmount: Int = 2): String {
        val result = StringBuilder()

        result.append(" ".repeat(indentation))

        when (node) {
            is IntegerNode -> {
                result.append("Integer: ")
                result.append(node.number.value)
            }

            is FloatNode -> {
                result.append("Float: ")
                result.append(node.float.value)
            }

            is UnaryOpNode -> {
                result.append("Unary Operation: operator: ")
                result.append(node.tokenOperator.token.name)
                result.appendLine()
                result.append(prettyPrintNode(node.node, indentation + indentationAmount, indentationAmount))
            }

            is BinOpNode -> {
                result.append("Binary Operation: operator: ")
                result.append(node.operatorToken.token.name)
                result.appendLine()
                result.append(prettyPrintNode(node.leftNode, indentation + indentationAmount, indentationAmount))
                result.appendLine()
                result.append(prettyPrintNode(node.rightNode, indentation + indentationAmount, indentationAmount))
            }

            is VarDeclarationNode -> {
                result.append("Variable Declaration: Variable name: ${node.variableName}")
                result.appendLine()
                result.append("Variable Value: ")
                result.appendLine()
                result.append(prettyPrintNode(node.variableValue, indentation + indentationAmount, indentationAmount))
            }

            is VarAccessNode -> {
                result.append("Variable Access: Variable Name: ${node.variableName}")
            }
        }

        return result.toString()
    }
}