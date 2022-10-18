package com.bignerdranch.androin.geomain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    public lateinit var trueButton : Button
    public lateinit var  falseButton : Button
    public  lateinit var  nextButton: ImageButton
    public  lateinit var  backButton: ImageButton
    public lateinit var questionTextView: TextView
    public lateinit var ResultButton: Button

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    //Activity "onCreate"
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate(Bundle?) called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        //quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.button_true)
        falseButton = findViewById(R.id.button_false)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        questionTextView = findViewById(R.id.textView)
        ResultButton = findViewById(R.id.button_result)

        ResultButton.isVisible = false
        trueButton.setOnClickListener {
            CheckAnswer(true)
            trueButton.isEnabled = false
            quizViewModel.BlockButton()
            falseButton.isEnabled = false
            CheckCurr()

        }
        falseButton.setOnClickListener { CheckAnswer(false)
            CheckAnswer(false)
            trueButton.isEnabled = false
            quizViewModel.BlockButton()
            falseButton.isEnabled = false
            CheckCurr()
        }
        //questionTextView.setOnClickListener{
            //currentIndex = (currentIndex + 1) % questionBank.size
            //UpdateQuestion()
        //}
        nextButton.setOnClickListener {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
            quizViewModel.moveToNext()
            UpdateQuestion()

            //CheckCurr()
        }
        backButton.setOnClickListener {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
            quizViewModel.moveToBack()
            UpdateQuestion()

        }
        ResultButton.setOnClickListener {
            Toast.makeText(this, "Кол-во привильны ответов ${quizViewModel.currentTrue}" , Toast.LENGTH_SHORT).show()
        }
        UpdateQuestion()
    }
    //Activity "OnStart"
    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }
    //Activity "onResume"
    override fun onResume(){
        super.onResume()
        Log.d(TAG,"onResume() called")
    }
    //Activity "onPause"
    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }
    //Save
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "OnSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
    //Activity "onStop"
    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }
    //Activity "onDestroy"
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }
    private fun UpdateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        if(quizViewModel.block[quizViewModel.currentIndex] == false){
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
        else {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }
    private  fun CheckAnswer(UserAnswer: Boolean) {
        quizViewModel.currentAnswer = quizViewModel.currentAnswer + 1
        val correctAnwser = quizViewModel.currentQuestionAnswer
        val messageResId = if (UserAnswer == correctAnwser) {
            quizViewModel.currentTrue = quizViewModel.currentTrue + 1
            R.string.True_Toast
        }
        else {
            R.string.False_Toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
    private fun CheckCurr(){
        if(quizViewModel.currentAnswer == quizViewModel.QuestSize){
            R.string.True_Toast
            ResultButton.isVisible = true
            backButton.isEnabled = false
            nextButton.isEnabled = false
        }
    }
}