package com.mateus.contadordecolonias

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.content.SharedPreferences
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.ContextCompat
import android.graphics.PorterDuffColorFilter
import android.graphics.PorterDuff

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private lateinit var counterTextView: TextView
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //edge-to-edge(): "expand the content to be drawn under the top status bar and under the navigation bar at the bottom"

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("counterPreferences", Context.MODE_PRIVATE)

        counterTextView = findViewById(R.id.counter)

        val savedCounter = sharedPreferences.getInt("counter", 0)
        counter = savedCounter

        updateCounterText()
    }

    private fun updateCounterText() {
        counterTextView.text = counter.toString()

        val resetImageButton = findViewById<ImageButton>(R.id.reset)
        val decreaseImageButton = findViewById<ImageButton>(R.id.decrease)

        if (counter == 0) {
            decreaseImageButton.isEnabled = false
            val decreaseDrawable = ContextCompat.getDrawable(this, R.drawable.rounded_corner_grey_deactivated)
            decreaseImageButton.background = decreaseDrawable

            resetImageButton.isEnabled = false
            val resetDrawable = ContextCompat.getDrawable(this, R.drawable.rounded_corner_grey_deactivated)
            resetImageButton.background = resetDrawable
        } else {
            decreaseImageButton.isEnabled = true
            val decreaseDrawable = ContextCompat.getDrawable(this, R.drawable.rounded_corner_pastel_red)
            decreaseImageButton.background = decreaseDrawable

            resetImageButton.isEnabled = true
            val resetDrawable = ContextCompat.getDrawable(this, R.drawable.rounded_corner_pastel_grey)
            resetImageButton.background = resetDrawable
        }
    }

    fun increase(view: View) {
        counter += 1
        updateCounterText()
        saveCounterToSharedPreferences()
    }

    fun decrease(view: View) {
        if (counter > 0) {
            counter -= 1
            updateCounterText()
            saveCounterToSharedPreferences()
        }
    }

    fun reset(view: View) {
        counter = 0
        updateCounterText()
        saveCounterToSharedPreferences()
    }

    private fun saveCounterToSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.putInt("counter", counter)
        editor.apply()
    }

}
