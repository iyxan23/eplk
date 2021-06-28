package com.iyxan23.eplk

import com.iyxan23.eplk.interpreter.StandardDefinitions
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.objects.EplkVoid
import com.iyxan23.eplk.parser.Parser

// Shows on how much time does it took to execute a command
const val withTime = false

// This one is for showing individual times for the lexer, parser, and interpreter
const val individualTimes = false

// This replaces \\n with a new line, used for testing newline statements
const val replaceSlashNWithNewline = true

fun main() {
    println("Welcome to the EPLK Shell!")

    // val scope = Scope("<SHELL>")
    val scope = StandardDefinitions.generateScope("<SHELL>")

    while (true) {
        print("EPLK SHELL > ")
        var code = readLine() ?: break

        if (replaceSlashNWithNewline) code = code.replace("\\n", "\n")

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
            println(interpreterResult.value)
        }

        println()
    }
}