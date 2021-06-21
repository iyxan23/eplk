package com.iyxan23.eplk

import com.iyxan23.eplk.nodes.*
import com.iyxan23.eplk.nodes.variable.VarAccessNode
import com.iyxan23.eplk.nodes.variable.VarDeclarationNode

object Utils {
    fun prettyPrintNode(node: Node, indentation: Int = 0, indentationAmount: Int = 2): String {
        val result = StringBuilder()

        val strIndentation = " ".repeat(indentation)

        when (node) {
            is IntegerNode -> {
                result.append(strIndentation + "Integer: ")
                result.append(node.number.value)
            }

            is FloatNode -> {
                result.append(strIndentation + "Float: ")
                result.append(node.float.value)
            }

            is UnaryOpNode -> {
                result.append(strIndentation + "Unary Operation: operator: ")
                result.append(node.tokenOperator.token.name)
                result.appendLine()
                result.append(prettyPrintNode(node.node, indentation + indentationAmount, indentationAmount))
            }

            is BinOpNode -> {
                result.append(strIndentation + "Binary Operation: operator: ")
                result.append(node.operatorToken.token.name)
                result.appendLine()
                result.append(prettyPrintNode(node.leftNode, indentation + indentationAmount, indentationAmount))
                result.appendLine()
                result.append(prettyPrintNode(node.rightNode, indentation + indentationAmount, indentationAmount))
            }

            is VarDeclarationNode -> {
                result.append(strIndentation + "Variable Declaration:")
                result.appendLine()
                result.append(strIndentation + "Variable name: ${node.variableName}")
                result.appendLine()
                result.append(strIndentation + "Variable Value: ")
                result.appendLine()
                result.append(prettyPrintNode(node.variableValue, indentation + indentationAmount, indentationAmount))
            }

            is VarAccessNode -> {
                result.append(strIndentation + "Variable Access: Variable Name: ${node.variableName}")
            }

            is IfNode -> {
                node.statements.forEachIndexed { index, pair ->
                    val condition = pair.first
                    val expression = pair.second

                    result.append(strIndentation + (if (index == 0) "If Condition: \n" else "Elif Condition: \n"))
                    result.append(prettyPrintNode(condition, indentation + indentationAmount, indentationAmount))
                    result.appendLine()
                    result.append(strIndentation + "Expression: \n")
                    result.append(prettyPrintNode(expression, indentation + indentationAmount, indentationAmount))
                    result.appendLine()
                }

                result.append(strIndentation + "Else Expression: \n")
                result.append(prettyPrintNode(node.elseExpression, indentation + indentationAmount, indentationAmount))
            }
        }

        return result.toString()
    }
}