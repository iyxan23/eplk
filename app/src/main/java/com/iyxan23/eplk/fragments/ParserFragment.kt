package com.iyxan23.eplk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iyxan23.eplk.R
import com.iyxan23.eplk.viewmodels.LexerViewModel
import com.iyxan23.eplk.viewmodels.ParserViewModel

class ParserFragment : Fragment() {

    lateinit var viewModel: ParserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.parser_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ParserViewModel::class.java)
    }
}