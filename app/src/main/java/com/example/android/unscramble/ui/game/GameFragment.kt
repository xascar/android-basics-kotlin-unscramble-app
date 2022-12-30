/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Fragment where the game is played, contains the game logic.
 */
class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()


    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding

    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
//        binding = GameFragmentBinding.inflate(inflater, container, false) -- REPLACED TO USE DATA BINDING:

        //To use data binding:
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)


//        Log.d("GameFragment", "GameFragment created/re-created!")
//        Log.d("GameFragment", "Word: ${viewModel.currentScrambledWord}; " +
//                "Score: ${viewModel.score}; WordCount: ${viewModel.currentWordCount}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initializing the layout variables
        binding.gameViewModel = viewModel

        binding.maxNoOfWords = MAX_NO_OF_WORDS

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        // Update the UI
//        updateNextWordOnScreen() -- removed because we're gonna be attaching an observer to the LiveData
        //Commented because we're gonna be attaching an observer to the live data
//        binding.score.text = getString(R.string.score, viewModel.score) // This value will be updated according to liveData
//        binding.wordCount.text = getString(R.string.word_count, viewModel.currentWordCount, MAX_NO_OF_WORDS) // This value will be updated according to liveData

//        viewModel.currentWordCount.observe(viewLifecycleOwner) { wordCount ->
//            binding.wordCount.text = getString(R.string.word_count, wordCount, MAX_NO_OF_WORDS)
//        }
//        viewModel.score.observe(viewLifecycleOwner) { score ->
//            binding.score.text = getString(R.string.score, score)
//        }
        // Observe the currentScrambledWord LiveData.
//        viewModel.currentScrambledWord.observe(viewLifecycleOwner) { newWord ->   -- Removed because of data binding
//            binding.textViewUnscrambledWord.text = newWord
//        }

    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Log.d("GameFragment", "GameFragment destroyed!")
//    }

    /*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    */
    private fun onSubmitWord() {
//        currentScrambledWord = getNextScrambledWord()
//        currentWordCount++
//        score += SCORE_INCREASE
//        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
//        binding.score.text = getString(R.string.score, score)
//        setErrorTextField(false)
//        updateNextWordOnScreen()
        val playerWord = binding.textInputEditText.text.toString()

        if (viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (!viewModel.nextWord())  {
                showFinalScoreDialog()
            }
//                binding.wordCount.text = getString(R.string.word_count, viewModel.currentWordCount, MAX_NO_OF_WORDS) // This value will be updated according to liveData
//                binding.score.text = getString(R.string.score, viewModel.score)
//                updateNextWordOnScreen()  -- removed because we're gonna be attaching an observer to the LiveData
//            } else {
//                showFinalScoreDialog()
//            }
        } else {
            setErrorTextField(true)
        }
    }

    /*
     * Skips the current word without changing the score.
     * Increases the word count.
     */
    private fun onSkipWord() {
//        currentScrambledWord = getNextScrambledWord()
//        currentWordCount++
//        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
//        setErrorTextField(false)
//        updateNextWordOnScreen()
        if (viewModel.nextWord()) {
//            binding.wordCount.text = getString(R.string.word_count, viewModel.currentWordCount, MAX_NO_OF_WORDS)
            setErrorTextField(false)
//            updateNextWordOnScreen() -- removed because we're gonna be attaching an observer to the LiveData
        } else {
            showFinalScoreDialog()
        }
    }


//    /*
//     * Gets a random word for the list of words and shuffles the letters in it.
//     */
//    private fun getNextScrambledWord(): String {
//        val tempWord = allWordsList.random().toCharArray()
//        tempWord.shuffle()
//        return String(tempWord)
//    }

    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reinitializeData()
        setErrorTextField(false)
//        updateNextWordOnScreen() -- removed because we're gonna be attaching an observer to the LiveData
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

//    /*
//     * Displays the next scrambled word on screen. -- removed because we're gonna be attaching an observer to the LiveData
//     */
//    private fun updateNextWordOnScreen() {
//        binding.textViewUnscrambledWord.text = viewModel.currentScrambledWord
//    }

    /*
* Creates and shows an AlertDialog with the final score.
*/
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }

}
