package com.iyxan23.eplk

import com.iyxan23.eplk.nodes.*
import com.iyxan23.eplk.nodes.control.ForNode
import com.iyxan23.eplk.nodes.control.IfNode
import com.iyxan23.eplk.nodes.control.WhileNode
import com.iyxan23.eplk.nodes.operation.BinOpNode
import com.iyxan23.eplk.nodes.operation.UnaryOpNode
import com.iyxan23.eplk.nodes.types.*
import com.iyxan23.eplk.nodes.variable.VarAccessNode
import com.iyxan23.eplk.nodes.variable.VarDeclarationNode

object Utils {
    fun prettyPrintNode(node: Node, indentation: Int = 0, indentationAmount: Int = 2): String {
        val result = StringBuilder()

        val strIndentation = " ".repeat(indentation)

        when (node) {
            is IntegerNode -> {
                result.append(strIndentation + "Integer: ${node.number.value}")
            }

            is FloatNode -> {
                result.append(strIndentation + "Float: ${node.float.value}")
            }

            is BooleanNode -> {
                result.append(strIndentation + "Boolean: ${node.value}")
            }

            is StringNode -> {
                result.append(strIndentation + "String: ${node.value}")
            }

            is ListNode -> {
                result.appendLine(strIndentation + "List: ")

                node.items.forEach { item ->
                    result.appendLine(prettyPrintNode(item, indentation + indentationAmount, indentationAmount))
                }
            }

            is UnaryOpNode -> {
                result.appendLine(strIndentation + "Unary operation: operator: ${node.tokenOperator.token.name}")
                result.appendLine(prettyPrintNode(node.node, indentation + indentationAmount, indentationAmount))
            }

            is BinOpNode -> {
                result.appendLine(strIndentation + "Binary operation: operator: ${node.operatorToken.token.name}")
                result.appendLine(prettyPrintNode(node.leftNode, indentation + indentationAmount, indentationAmount))
                result.appendLine(prettyPrintNode(node.rightNode, indentation + indentationAmount, indentationAmount))
            }

            is VarDeclarationNode -> {
                result.appendLine(strIndentation + "Variable declaration:")
                result.appendLine(strIndentation + "Variable name: ${node.variableName}")
                result.appendLine(strIndentation + "Variable value:")
                result.appendLine(prettyPrintNode(node.variableValue, indentation + indentationAmount, indentationAmount))
            }

            is VarAccessNode -> {
                result.appendLine(strIndentation + "Variable access: Variable name: ${node.variableName}")
            }

            is IfNode -> {
                node.statements.forEachIndexed { index, pair ->
                    val condition = pair.first
                    val expression = pair.second

                    result.appendLine(strIndentation + (if (index == 0) "If condition:" else "Elif condition:"))
                    result.appendLine(prettyPrintNode(condition, indentation + indentationAmount, indentationAmount))
                    result.appendLine(strIndentation + "Expression:")
                    result.appendLine(prettyPrintNode(expression, indentation + indentationAmount, indentationAmount))
                }

                result.appendLine(strIndentation + "Else expression: ")
                result.appendLine(prettyPrintNode(node.elseExpression, indentation + indentationAmount, indentationAmount))
            }

            is ForNode -> {
                result.appendLine(strIndentation + "For statement:")
                result.appendLine(strIndentation + "First expression:")
                result.appendLine(prettyPrintNode(node.firstExpression, indentation + indentationAmount, indentationAmount))
                result.appendLine(strIndentation + "Second expression:")
                result.appendLine(prettyPrintNode(node.secondExpression, indentation + indentationAmount, indentationAmount))
                result.appendLine(strIndentation + "Third expression:")
                result.appendLine(prettyPrintNode(node.thirdExpression, indentation + indentationAmount, indentationAmount))
                result.appendLine(strIndentation + "Expression:")
                result.appendLine(prettyPrintNode(node.expression, indentation + indentationAmount, indentationAmount))
            }

            is WhileNode -> {
                result.appendLine(strIndentation + "While statement:")
                result.appendLine(strIndentation + "Condition:")
                result.appendLine(prettyPrintNode(node.condition, indentation + indentationAmount, indentationAmount))
                result.appendLine(strIndentation + "Expression:")
                result.appendLine(prettyPrintNode(node.expressions, indentation + indentationAmount, indentationAmount))
            }

            is FunctionDefinitionNode -> {
                result.appendLine(strIndentation + "Function definition:")
                result.appendLine(strIndentation + "Name: ${node.functionName}")
                result.appendLine(strIndentation + "Parameters: ${node.parameters.joinToString(", ")}")
                result.appendLine(strIndentation + "Statement(s):")
                result.appendLine(prettyPrintNode(node.statements, indentation + indentationAmount, indentationAmount))
            }

            is FunctionCallNode -> {
                result.appendLine(strIndentation + "Call function:")
                result.appendLine(strIndentation + "Node to call:")
                result.appendLine(prettyPrintNode(node.nodeToCall, indentation + indentationAmount, indentationAmount))
                result.appendLine(strIndentation + "Arguments:")

                node.arguments.forEach { argument ->
                    result.appendLine(prettyPrintNode(argument, indentation + indentationAmount, indentationAmount))
                }
            }

            is IncrementOrDecrementNode -> {
                val increment = node.incDecToken.token == Tokens.DOUBLE_PLUS

                result.appendLine(strIndentation + "IncrementOrDecrementNode: " + (if (increment) "Increment" else "decrement"))
                result.appendLine(strIndentation + "Node to " + (if (increment) "Increment" else "decrement") + ": ")
                result.appendLine(prettyPrintNode(node.nodeToIncDec, indentation + indentationAmount, indentationAmount))
            }

            is IndexNode -> {
                result.appendLine(strIndentation + "Index Node: Node to Index: ")
                result.appendLine(prettyPrintNode(node.nodeToIndex, indentation + indentationAmount, indentationAmount))
                result.appendLine(strIndentation + "Index value: ")
                result.appendLine(prettyPrintNode(node.indexValue, indentation + indentationAmount, indentationAmount))
            }
        }

        return result.toString()
    }
}