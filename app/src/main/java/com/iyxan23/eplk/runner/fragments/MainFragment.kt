package com.iyxan23.eplk.runner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.iyxan23.eplk.runner.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.main_fragment, container, false)

        root.findViewById<Button>(R.id.buttonShell)
            .setOnClickListener {
                it  .findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToShellFragment())
            }

        root.findViewById<Button>(R.id.buttonStepByStep)
            .setOnClickListener {
                it  .findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToCodeFragment())
            }

        return root
    }
}