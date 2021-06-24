package com.iyxan23.eplk

import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.objects.EplkVoid
import com.iyxan23.eplk.parser.Parser

// Shows on how much time does it took to execute a command
const val withTime = true

// This one is for showing individual times for the lexer, parser, and interpreter
const val individualTimes = true

fun main() {
    println("Welcome to the EPLK Shell!")

    val scope = Scope("<SHELL>")

    while (true) {
        print("EPLK SHELL > ")
        val code = readLine() ?: break

        val startTime = System.currentTimeMillis()

        val lexerResult = Lexer("<SHELL>", code).doLexicalAnalysis()

        if (individualTimes) println("Lexer took ${System.currentTimeMillis() - startTime}ms")

        if (lexerResult.error != null) {
            println(lexerResult.error.generateString())
            continue
        }

        val parserStartTime = System.currentTimeMillis()

        val parseResult = Parser(lexerResult.tokens!!).parse()

        if (individualTimes) println("Parser took ${System.currentTimeMillis() - parserStartTime}ms")

        if (parseResult.hasError) {
            println(parseResult.error!!.generateString())
            continue
        }

        val interpreterStartTime = System.currentTimeMillis()

        val interpreterResult = parseResult.node!!.visit(scope)

        if (individualTimes) println("Interpreter took ${System.currentTimeMillis() - interpreterStartTime}ms")

        if (withTime) println("Took ${System.currentTimeMillis() - startTime}ms")

        if (interpreterResult.hasError) {
            println(interpreterResult.error!!.generateString())
            continue
        }

        // Don't print void objects
        if (interpreterResult.value !is EplkVoid) {
            println()
            println(interpreterResult.value)
        }

        println()
    }
}