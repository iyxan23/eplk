package com.iyxan23.eplk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.iyxan23.eplk.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.main_fragment, container, false)

        val code = root.findViewById<EditText>(R.id.code)

        val runLexerButton = root.findViewById<Button>(R.id.run_lexer_button)
        runLexerButton.setOnClickListener {
            it  .findNavController()
                .navigate(
                    MainFragmentDirections
                        .actionMainFragmentToLexerFragment(code.text.toString())
                )
        }

        return root
    }
}