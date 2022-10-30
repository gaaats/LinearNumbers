package com.gameartnumm.linearnumbers

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.gameartnumm.linearnumbers.databinding.FragmentVelcomeBinding
import com.google.android.material.snackbar.Snackbar

class VelcomeFragment : Fragment() {
    private var _binding: FragmentVelcomeBinding? = null
    private val binding: FragmentVelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding = NULL")


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ccc", "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVelcomeBinding.inflate(inflater, container, false)

        try {
            val animationDrawable = binding.consRootVelcomeFrag.background as AnimationDrawable
            animationDrawable.setEnterFadeDuration(2500)
            animationDrawable.setExitFadeDuration(5000)
            animationDrawable.start()
        } catch (e: Exception) {
            snackBarError()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            binding.btnPlayGaaaame.setOnClickListener {
                findNavController().navigate(R.id.action_velcomeFragment_to_chooseLevelFragment)
                //            openFragChooseLevel()
            }
            binding.btnImgRules.setOnClickListener {
                findNavController().navigate(R.id.action_velcomeFragment_to_rulesFragment)
            }
            binding.btnImgExit.setOnClickListener {
                initAlertDialogBuilderExit()
            }
        } catch (e: Exception) {
            snackBarError()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun openFragChooseLevel() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun snackBarError() {
        Snackbar.make(
            binding.root,
            "There is some error, try again",
            Snackbar.LENGTH_LONG
        ).show()
        requireActivity().onBackPressed()
    }

    private fun initAlertDialogBuilderExit() {
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("The current data will not be saved. Do you really want to log out?")
            .setPositiveButton("Yes, Exit") { _, _ ->
                requireActivity().finish()
            }
            .setNegativeButton("Deny") { _, _ ->
            }
            .setCancelable(true)
            .create()
            .show()
    }
}