package com.gameartnumm.linearnumbers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gameartnumm.linearnumbers.data.Constance
import com.gameartnumm.linearnumbers.data.GameResult
import com.gameartnumm.linearnumbers.databinding.FragmentGameFinishedBinding
import com.google.android.material.snackbar.Snackbar

class GameFinishedFragment : Fragment() {

    private var userPercent = 0
    lateinit var currentResultInFinishFrag: GameResult
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("GameFinishedFragment = NULL")

    override fun onCreate(savedInstanceState: Bundle?) {
        requireArguments().getParcelable<GameResult>(Constance.GAME_RESULT)?.let {
            currentResultInFinishFrag = it
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonRetry.setOnClickListener {
            findNavController().navigate(R.id.action_gameFinishedFragment_to_chooseLevelFragment)
        }

        try {
            if (currentResultInFinishFrag.totalNumberOfAns == 0) {
                userPercent = 0
            } else {
                userPercent =
                    currentResultInFinishFrag.totalNumberOfAns / currentResultInFinishFrag.countOfCorrectAns * 100
            }

            if (currentResultInFinishFrag.winnerOrNot) {
                binding.emojiResult.setImageResource(R.drawable.smile)
            } else {
                binding.emojiResult.setImageResource(R.drawable.sad)
            }

            binding.buttonRetry.setOnClickListener {
                findNavController().navigate(R.id.action_gameFinishedFragment_to_chooseLevelFragment)
                //            changeOnPop()
            }
            binding.tvUserScoreAnswers.text =
                "Your scored  ${currentResultInFinishFrag.countOfCorrectAns} correct ANS"
            binding.tvRequiredAnswers.text =
                "You need ${currentResultInFinishFrag.gameSettings.minCountOfCorrectAns} correct ANS"
            binding.tvRequiredPercentage.text =
                "You need ${currentResultInFinishFrag.gameSettings.minPercentOfCorrectAns}% correct ANS"
            binding.tvScorePercentage.text = "You scored $userPercent% correct ANS"
        } catch (e: Exception) {
            snackBarError()
        }

        super.onViewCreated(view, savedInstanceState)
//        changeInActivityOnBackPressed()
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun snackBarError() {
        Snackbar.make(
            binding.root,
            "Oooops, there is some error, try again",
            Snackbar.LENGTH_LONG
        ).show()
        findNavController().navigate(R.id.action_gameFinishedFragment_to_chooseLevelFragment)
    }

    companion object {
        fun genereteGameFinishFragment(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constance.GAME_RESULT, gameResult)
                }
            }

        }
    }
//    private fun changeInActivityOnBackPressed() {
//        requireActivity().onBackPressedDispatcher.addCallback(
//            viewLifecycleOwner,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    changeOnPop()
//                }
//            })
//    }
//    private fun changeOnPop(){
//        requireActivity().supportFragmentManager.popBackStack(Constance.GAME_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//    }

}