package com.iyxan23.eplk.fragments

import android.app.AlertDialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iyxan23.eplk.R
import com.iyxan23.eplk.adapters.TokensRecyclerViewAdapter
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.viewmodels.LexerViewModel

class LexerFragment : Fragment() {

    private lateinit var viewModel: LexerViewModel
    private var tokensAdapter: TokensRecyclerViewAdapter
                = TokensRecyclerViewAdapter(ArrayList(), "")

    private var tokens: Array<Token>? = null

    private lateinit var progressBarLexer: ProgressBar
    private lateinit var runParserButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.lexer_fragment, container, false)

        val tokensRecyclerView = root.findViewById<RecyclerView>(R.id.tokens_rv)
        tokensRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        tokensRecyclerView.adapter = tokensAdapter

        runParserButton = root.findViewById(R.id.run_parser_button)
        progressBarLexer = root.findViewById(R.id.progress_lexer)

        // Don't enable the run parser button before the lexer has finished doing lexical analysis
        runParserButton.isEnabled = false
        runParserButton.setOnClickListener {
            // tokens can't be null because the button is enabled after the lexer has finished
            findNavController()
                .navigate(LexerFragmentDirections.actionLexerFragmentToParserFragment())
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LexerViewModel::class.java)

        val code = arguments?.getString("code")!!

        viewModel.runLexer(code)
        tokensAdapter.code = code

        viewModel.tokens.observe(viewLifecycleOwner, { tokens ->
            progressBarLexer.visibility = View.GONE
            tokensAdapter.update(tokens)

            this.tokens = tokens.toTypedArray()

            // Enable the run parser button
            runParserButton.isEnabled = true
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            // An error occurred in the lexer
            progressBarLexer.visibility = View.GONE

            val builder = AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(error.toString(code))
                .setCancelable(false)
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                    findNavController().navigateUp()
                }

            val dialog = builder.create()

            // Set the font to be monospaced because the error message depends on it
            val messageView = dialog.findViewById<TextView>(android.R.id.message)
            messageView.typeface = Typeface.MONOSPACE
            messageView.gravity = Gravity.CENTER

            dialog.show()
        })
    }
}