package com.iyxan23.eplk.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.parser.Parser
import kotlinx.coroutines.launch

class ParserViewModel : ViewModel() {
    private val errorMutable: MutableLiveData<EplkError> = MutableLiveData()
    private val nodesMutable: MutableLiveData<Node> = MutableLiveData()

    val error: LiveData<EplkError> = errorMutable
    val nodes: LiveData<Node> = nodesMutable

    fun runParser(tokens: ArrayList<Token>) {
        viewModelScope.launch {
            val parser = Parser(tokens)
            val result = parser.parse()

            if (result.hasError) {
                errorMutable.value = result.error
            } else {
                nodesMutable.value = result.node
            }
        }
    }
}