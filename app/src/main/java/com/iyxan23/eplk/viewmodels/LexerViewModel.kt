package com.iyxan23.eplk.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.lexer.models.Token
import kotlinx.coroutines.launch

class LexerViewModel: ViewModel() {

    private val tokensMutable: MutableLiveData<ArrayList<Token>> = MutableLiveData()
    private val errorMutable: MutableLiveData<EplkError?> = MutableLiveData()

    val tokens: LiveData<ArrayList<Token>> = tokensMutable
    val error: LiveData<EplkError?> = errorMutable

    fun runLexer(code: String) {
        viewModelScope.launch {
            val lexer = Lexer("<EPLK_APP>", code)
            val result = lexer.doLexicalAnalysis()

            if (result.error != null) {
                errorMutable.value = result.error
            } else {
                tokensMutable.value = result.tokens
            }
        }
    }
}