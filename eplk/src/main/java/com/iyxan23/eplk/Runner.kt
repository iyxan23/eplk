package com.iyxan23.eplk

import com.iyxan23.eplk.interpreter.StandardDefinitions
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.objects.EplkVoid
import com.iyxan23.eplk.parser.Parser
import java.io.File

val scope = StandardDefinitions.generateScope("<SHELL>")

fun main() {
    print("Filepath: ")
    val filepath: String = readLine() ?: return
    val code = File(filepath).readText()
    val lexerResult = Lexer("<SHELL>", code).doLexicalAnalysis()

    if (lexerResult.error != null) {
        println(lexerResult.error.generateString())
        return
    }

    val parseResult = Parser(lexerResult.tokens!!).parse()

    if (parseResult.hasError) {
        println(parseResult.error!!.generateString())
        return
    }

    val interpreterResult = parseResult.node!!.visit(scope)

    if (interpreterResult.hasError) {
        println(interpreterResult.error!!.generateString())
        return
    }

    // Don't print void objects
    if (interpreterResult.value !is EplkVoid) {
        println()
        println(interpreterResult.value)
    }
}