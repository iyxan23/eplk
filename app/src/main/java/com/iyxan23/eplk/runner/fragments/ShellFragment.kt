package com.iyxan23.eplk.runner.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.iyxan23.eplk.runner.R
import com.iyxan23.eplk.runner.viewmodels.ShellViewModel

class ShellFragment : Fragment() {

    private lateinit var viewModel: ShellViewModel

    private lateinit var shellOutput: TextView
    private lateinit var executeButton: Button
    private lateinit var codeText: EditText
    private lateinit var runningProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.shell_fragment, container, false)

        shellOutput = root.findViewById(R.id.shell_output)
        executeButton = root.findViewById(R.id.executeButton)
        codeText = root.findViewById(R.id.shellCode)
        runningProgressBar = root.findViewById(R.id.shell_progress_bar)

        return root
    }

    private val output = SpannableStringBuilder()

    private fun out(text: String, color: Int) {
        output.append("$text\n", ForegroundColorSpan(color), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        shellOutput.text = output
    }

    private var isExecuting = false

    private var code = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShellViewModel::class.java)

        viewModel.result.observe(viewLifecycleOwner) { result ->
            runningProgressBar.visibility = View.GONE

            out(
                result.toString(),
                ResourcesCompat.getColor(
                    resources,
                    R.color.material_on_background_emphasis_high_type,
                    requireActivity().theme
                )
            )
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            runningProgressBar.visibility = View.GONE

            out(
                error.toString(code),
                ResourcesCompat.getColor(
                    resources,
                    R.color.design_default_color_error,
                    requireActivity().theme
                )
            )
        }

        executeButton.setOnClickListener {
            code = codeText.text.toString()

            out(
                "EPLK SHELL > $code",
                ResourcesCompat.getColor(
                    resources,
                    R.color.material_on_background_emphasis_medium,
                    requireActivity().theme
                )
            )

            isExecuting = true
            it.isEnabled = false

            runningProgressBar.visibility = View.VISIBLE

            viewModel.executeCode(code)
        }
    }
}