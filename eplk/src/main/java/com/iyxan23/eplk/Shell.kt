package com.iyxan23.eplk

import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.parser.Parser

fun main() {
    println("Welcome to EPLK Shell!")

    val scope = Scope("<SHELL>")

    while (true) {
        print("EPLK SHELL > ")
        val code = readLine() ?: continue

        val lexerResult = Lexer("<SHELL>", code).doLexicalAnalysis()

        if (lexerResult.error != null) {
            println(lexerResult.error.toString(code))
            continue
        }

        val parseResult = Parser(lexerResult.tokens!!).parse()

        if (parseResult.hasError) {
            println(parseResult.error!!.toString(code))
            continue
        }

        val interpreterResult = parseResult.node!!.visit(scope)

        if (interpreterResult.hasError) {
            println(interpreterResult.error!!.toString(code))
            continue
        }

        println(interpreterResult.value)
    }
}