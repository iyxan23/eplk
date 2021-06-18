package com.iyxan23.eplk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.iyxan23.eplk.R
import com.iyxan23.eplk.adapters.TokensRecyclerViewAdapter
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.viewmodels.LexerViewModel

class LexerFragment : Fragment() {

    companion object {
        fun newInstance() = LexerFragment()
    }

    private lateinit var viewModel: LexerViewModel
    private var tokensAdapter: TokensRecyclerViewAdapter
                = TokensRecyclerViewAdapter(emptyList<Token>() as ArrayList<Token>, "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.lexer_fragment, container, false)

        val tokensRecyclerView = root.findViewById<RecyclerView>(R.id.tokens_rv)
        tokensRecyclerView.adapter = tokensAdapter

        val runParserButton = root.findViewById<Button>(R.id.run_parser_button)
        val progressBarLexer = root.findViewById<Button>(R.id.progress_lexer)
        runParserButton.setOnClickListener {
            TODO("Implement ParseFragment")
        }

        viewModel.tokens.observe(viewLifecycleOwner, { tokens ->
            progressBarLexer.visibility = View.VISIBLE
            tokensAdapter.update(tokens)
        })

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LexerViewModel::class.java)
        viewModel.runLexer(arguments?.getString("code")!!)
    }
}