package caruso.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.color
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var wordToGuess = FourLetterWordList.getRandomFourLetterWord().lowercase()
        val input = findViewById<TextInputEditText>(R.id.input)
        val reset = findViewById<Button>(R.id.reset)
        val button = findViewById<Button>(R.id.guess)
        val textView = findViewById<TextView>(R.id.textView)
        val finalWord = findViewById<TextView>(R.id.wordlie)
        finalWord.text = wordToGuess
        var guessCount = 0
        val star1 = findViewById<ImageView>(R.id.star1)
        val star2 = findViewById<ImageView>(R.id.star2)

        button.setOnClickListener{
            val guess = input.text.toString().lowercase()
            if (guess.length != 4)
                Toast.makeText(it.context,"Guess is not exactly 4 letter!",Toast.LENGTH_SHORT).show()
            else if (!guess.all { it.isLetter() })
                Toast.makeText(it.context,"Guess is not only letters!",Toast.LENGTH_SHORT).show()
            else {
                guessCount += 1
                val newText =
                    textView.text.toString() + "Guess $guessCount: $guess \nGuess $guessCount Check: " + checkGuess(
                        guess,
                        wordToGuess
                    ) + "\n"
                val spanString = SpannableString(newText)

                var index = 0
                for (char in newText) {
                    if (char == 'O')
                        spanString.setSpan(
                            ForegroundColorSpan(Color.GREEN),
                            index,
                            index + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    else if (char == 'X')
                        spanString.setSpan(
                            ForegroundColorSpan(Color.RED),
                            index,
                            index + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    else if (char == '+')
                        spanString.setSpan(
                            ForegroundColorSpan(Color.YELLOW),
                            index,
                            index + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    index++
                }
                if (wordToGuess == guess) {
                    star1.visibility = View.VISIBLE
                    star2.visibility = View.VISIBLE
                    guessCount = 3
                }
                textView.text = spanString
                if (guessCount == 3) {
                    finalWord.visibility = View.VISIBLE
                    button.visibility = View.INVISIBLE
                    reset.visibility = View.VISIBLE
                }
            }
        }

        reset.setOnClickListener{
            wordToGuess = FourLetterWordList.getRandomFourLetterWord().lowercase()
            finalWord.text = wordToGuess
            guessCount = 0
            star1.visibility = View.INVISIBLE
            star2.visibility = View.INVISIBLE
            finalWord.visibility = View.INVISIBLE
            button.visibility = View.VISIBLE
            reset.visibility = View.INVISIBLE
            textView.text = ""
        }
    }
    private fun checkGuess(guess: String, wordToGuess: String) : SpannableStringBuilder {
        var result = SpannableStringBuilder()
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result.color(Color.rgb(66, 245, 78), { append('O') } )
            }
            else if (guess[i] in wordToGuess) {
                result.color(Color.rgb(242, 239, 29), { append('+') } )
            }
            else {
                result.color(Color.rgb(242, 36, 29), { append('X') } )
            }
        }
        return result
    }
}