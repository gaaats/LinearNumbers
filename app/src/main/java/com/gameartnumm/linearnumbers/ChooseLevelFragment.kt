package com.gameartnumm.linearnumbers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.gameartnumm.linearnumbers.data.Difficulty
import com.gameartnumm.linearnumbers.databinding.FragmentChooseLevelBinding
import com.google.android.material.snackbar.Snackbar

class ChooseLevelFragment : Fragment() {
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding = NULL")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        try {
            with(binding) {
                btnImgExit.setOnClickListener {
                    initAlertDialogBuilderExit()
                }

                btnCholdren.setOnClickListener {
                    navigateToGameFragAndPutBundle(Difficulty.TEST)
    //                openFragGame(Difficulty.TEST)
                }
                btnEasy.setOnClickListener {
                    navigateToGameFragAndPutBundle(Difficulty.EASY)
    //                openFragGame(Difficulty.EASY)
                }
                btnNormal.setOnClickListener {
                    navigateToGameFragAndPutBundle(Difficulty.EASY)
                }
                btnHard.setOnClickListener {
    //                openFragGame(Difficulty.NORMAL)
                    navigateToGameFragAndPutBundle(Difficulty.NORMAL)
                }
                btnExtraHard.setOnClickListener {
    //                openFragGame(Difficulty.HARD)
                    navigateToGameFragAndPutBundle(Difficulty.HARD)
                }
            }
        } catch (e: Exception) {
            snackBarError()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun navigateToGameFragAndPutBundle(difficulty: Difficulty) {
        ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(diff = difficulty)
            .also {
                findNavController().navigate(it)
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initAlertDialogBuilderExit() {
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("The current data will not be saved. Do you really want to log out?")
            .setPositiveButton("Yes, Exit") { _, _ ->
                requireActivity().onBackPressed()
            }
            .setNegativeButton("Deny") { _, _ ->
            }
            .setCancelable(true)
            .create()
            .show()
    }

    private fun snackBarError() {
        Snackbar.make(
            binding.root,
            "Oooops, there is some error, try again",
            Snackbar.LENGTH_LONG
        ).show()
        requireActivity().onBackPressed()
    }

    companion object {
        fun newIntanceOfChooseLevelFrag(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }

}