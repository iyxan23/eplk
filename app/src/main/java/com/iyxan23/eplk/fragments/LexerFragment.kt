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
import com.iyxan23.eplk.viewmodels.LexerViewModel


class LexerFragment : Fragment() {

    companion object {
        fun newInstance() = LexerFragment()
    }

    private lateinit var viewModel: LexerViewModel
    private var tokensAdapter: TokensRecyclerViewAdapter
                = TokensRecyclerViewAdapter(ArrayList(), "")

    private lateinit var progressBarLexer: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.lexer_fragment, container, false)

        val tokensRecyclerView = root.findViewById<RecyclerView>(R.id.tokens_rv)
        tokensRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        tokensRecyclerView.adapter = tokensAdapter

        val runParserButton = root.findViewById<Button>(R.id.run_parser_button)
        progressBarLexer = root.findViewById<ProgressBar>(R.id.progress_lexer)
        runParserButton.setOnClickListener {
            TODO("Implement ParseFragment")
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
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            // An error occurred in the lexer
            progressBarLexer.visibility = View.GONE

            val builder = AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(error.toString(code))
                .setCancelable(false)
                .setPositiveButton("Ok") { dialog, which ->
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