package com.example.activitylifecycledemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ActivityLifecycle"
        const val EXTRA_USER_NAME = "USER_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity -> onCreate()")

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val openButton = findViewById<Button>(R.id.openButton)

        openButton.setOnClickListener {

            val name = nameInput.text.toString().trim()

            val finalName = if (name.isEmpty()) {
                "Guest"
            } else {
                name
            }

            // Activity is also a Context.
            val intent = Intent(
                this,
                SecondActivity::class.java
            )

            // Passing data using Intent.
            intent.putExtra(
                EXTRA_USER_NAME,
                finalName
            )

            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "MainActivity -> onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "MainActivity -> onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "MainActivity -> onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "MainActivity -> onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "MainActivity -> onDestroy()")
    }
}