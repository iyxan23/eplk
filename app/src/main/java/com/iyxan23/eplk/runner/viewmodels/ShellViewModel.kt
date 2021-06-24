package com.iyxan23.eplk.runner.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkObject
import com.iyxan23.eplk.parser.Parser
import kotlinx.coroutines.launch

class ShellViewModel : ViewModel() {

    private var scope = Scope("<SHELL>")

    private val tokensMutable: MutableLiveData<ArrayList<Token>> = MutableLiveData()
    private val nodesMutable: MutableLiveData<Node> = MutableLiveData()
    private val resultMutable: MutableLiveData<EplkObject> = MutableLiveData()
    private val errorMutable: MutableLiveData<EplkError> = MutableLiveData()

    val tokens: LiveData<ArrayList<Token>> = tokensMutable
    val error: LiveData<EplkError> = errorMutable
    val nodes: LiveData<Node> = nodesMutable
    val result: LiveData<EplkObject> = resultMutable

    fun executeCode(code: String) {
        viewModelScope.launch {
            // Run the lexer
            val lexer = Lexer("<EPLK RUNNER>", code)
            val lexerResult = lexer.doLexicalAnalysis()

            if (lexerResult.error != null) {
                errorMutable.value = lexerResult.error
                return@launch

            } else {
                tokensMutable.value = lexerResult.tokens
            }

            // Then parser
            val parser = Parser(tokensMutable.value!!)
            val parserResult = parser.parse()

            if (parserResult.hasError) {
                errorMutable.value = lexerResult.error
                return@launch

            } else {
                nodesMutable.value = parserResult.node
            }

            // And finally, interpret it
            val interpreterResult = nodesMutable.value!!.visit(scope)

            if (interpreterResult.hasError) {
                errorMutable.value = interpreterResult.error
                return@launch

            } else {
                resultMutable.value = interpreterResult.value
            }
        }
    }

    fun refreshScope() {
        scope = Scope("<SHELL>")
    }
}