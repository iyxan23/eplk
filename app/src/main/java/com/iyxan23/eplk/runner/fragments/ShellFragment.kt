package com.iyxan23.eplk.runner.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.iyxan23.eplk.runner.R
import com.iyxan23.eplk.runner.viewmodels.ShellViewModel

class ShellFragment : Fragment() {

    private lateinit var viewModel: ShellViewModel

    private lateinit var shellOutput: TextView
    private lateinit var executeButton: Button
    private lateinit var codeText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.shell_fragment, container, false)

        shellOutput = root.findViewById(R.id.shell_output)
        executeButton = root.findViewById(R.id.executeButton)
        codeText = root.findViewById(R.id.shellCode)

        return root
    }

    @SuppressLint("SetTextI18n")
    fun out(text: String) {
        shellOutput.text = "${shellOutput.text}$text\n"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShellViewModel::class.java)

        viewModel.result.observe(viewLifecycleOwner) { result ->
            out(result.toString())
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            val code = codeText.text.toString()
            out(error.toString(code))
        }

        executeButton.setOnClickListener {
            val code = codeText.text.toString()
            out("<< $code")
            viewModel.executeCode(code)
        }
    }
}