package com.example.activitylifecycledemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ActivityLifecycle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)

        Log.d(TAG, "SecondActivity -> onCreate()")

        val welcomeText = findViewById<TextView>(
            R.id.welcomeText
        )

        val backButton = findViewById<Button>(
            R.id.backButton
        )

        // Get data from Intent.
        val name = intent.getStringExtra(
            MainActivity.EXTRA_USER_NAME
        ) ?: "Guest"

        welcomeText.text = "Welcome, $name!"

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "SecondActivity -> onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "SecondActivity -> onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "SecondActivity -> onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "SecondActivity -> onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "SecondActivity -> onDestroy()")
    }
}