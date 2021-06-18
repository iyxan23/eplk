package com.iyxan23.eplk

import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.UnaryOpNode
import com.iyxan23.eplk.objects.EplkInteger
import com.iyxan23.eplk.parser.Parser
import org.junit.Test

class NodesTest {

    val filename = "<TEST>"

    @Test
    fun testUnaryOpNode() {
        val lexerResult = Lexer(filename, "-1").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as UnaryOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        assert(parseResult.tokenOperator == Token(Tokens.MINUS, null, Position(0, 1, 0, filename)))
        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError)
        assert(resultVisit.value is EplkInteger)
    }
}