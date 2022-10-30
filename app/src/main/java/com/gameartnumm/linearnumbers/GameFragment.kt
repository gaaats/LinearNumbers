package com.gameartnumm.linearnumbers

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gameartnumm.linearnumbers.data.Constance
import com.gameartnumm.linearnumbers.data.Difficulty
import com.gameartnumm.linearnumbers.data.GameResult
import com.gameartnumm.linearnumbers.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar

class GameFragment : Fragment() {
    private val args: GameFragmentArgs by navArgs()

    //    private lateinit var currentLvlDifficulty: Difficulty

    private val currentLvlDifficulty: Difficulty by lazy {
        args.diff
    }
    private var listOfViews = mutableListOf<TextView>()

    private val vievModelFactory by lazy {
        GameVievModelFactory(requireActivity().application, currentLvlDifficulty)
    }
    private val gameVievModel by lazy {
        ViewModelProvider(this, vievModelFactory)[GameVievModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("GameFragment = NULL")

    override fun onCreate(savedInstanceState: Bundle?) {
        initVariableCurrentDifficulty()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        try {
            initListOfVievs()

            //here ITTTTT
//        gameVievModel.gameMethod()

            gameVievModel.questionLD.observe(viewLifecycleOwner) {
                binding.tvSum.text = it.totalSum.toString()
                binding.tvLeftVisibleNumber.text = it.visibleNumber.toString()
                for (indexOfViev in 0 until listOfViews.size) {
                    val variantOfAns = it.listOfVariants[indexOfViev].toString()
                    listOfViews[indexOfViev].text = variantOfAns
                }
            }
            gameVievModel.percentCorrectAnsLD.observe(viewLifecycleOwner) {
                binding.progressBar.setProgress(it, true)
            }

            gameVievModel.isEnoughNumCorrectAns.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.setTextColor(redOrGreenColor(it))
            }
//        gameVievModel.isEnoughPercentCorrectAnsLD.observe(viewLifecycleOwner) {
//            binding.progressBar.progressTintList = ColorStateList.valueOf(redOrGreenColor(it))
//        }
            gameVievModel.timeInStirngFormatLD.observe(viewLifecycleOwner) {
                binding.tvTimer.text = it
            }
            gameVievModel.minPercentCorrectAnsLD.observe(viewLifecycleOwner) {
                binding.progressBar.secondaryProgress = it
            }

            initSetOnClickListenersInTV()
            gameVievModel.percentCorrectAnsInStringLD.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.text = it
            }
            gameVievModel.gameResultLD.observe(viewLifecycleOwner) {
                Bundle().apply {
                    putParcelable(Constance.GAME_RESULT, it)
                }.also {
                    findNavController().navigate(R.id.action_gameFragment_to_gameFinishedFragment, it)
                }
    //
    //            openGameFinishedFragment(it)
            }
        } catch (e: Exception) {
            
            snackBarError()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    //    private fun initSetOnClickListenersInTV() {
//        for (indexOfViev in 0 until listOfViews.size) {
//            val numberInViev = listOfViews[indexOfViev].text.toString()
//            gameVievModel.takeUserAnsvCheckItUpdProgGenerNextQuest(numberInViev.toInt())
//        }
//    }
    private fun initSetOnClickListenersInTV() {
        for (indexOfViev in 0 until listOfViews.size) {
            listOfViews[indexOfViev].setOnClickListener {
                val numberInViev = listOfViews[indexOfViev].text.toString()
                gameVievModel.takeUserAnsvCheckItUpdProgGenerNextQuest(numberInViev.toInt())
            }

        }
    }

    private fun redOrGreenColor(trueOfFalse: Boolean): Int {
        return if (trueOfFalse) {
            R.color.green
        } else {
            R.color.red
        }
    }

    private fun initListOfVievs() {
        listOfViews.add(binding.tvOption1)
        listOfViews.add(binding.tvOption2)
        listOfViews.add(binding.tvOption3)
        listOfViews.add(binding.tvOption4)
        listOfViews.add(binding.tvOption5)
        listOfViews.add(binding.tvOption6)
    }

    private fun openGameFinishedFragment(curResult: GameResult) {
//        requireActivity().supportFragmentManager.beginTransaction().replace(
//            R.id.fragmentContainerView,
//            GameFinishedFragment.genereteGameFinishFragment(curResult)
//        )
//            .addToBackStack(null)
//            .commit()

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initVariableCurrentDifficulty() {
//        requireArguments().getParcelable<Difficulty>(Constance.LEVEL_OF_DIFFICULTY)?.let {
//            currentLvlDifficulty = it
//        }
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

        fun generateGameFragment(difficulty: Difficulty): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constance.LEVEL_OF_DIFFICULTY, difficulty)
                }
            }
        }
    }

}