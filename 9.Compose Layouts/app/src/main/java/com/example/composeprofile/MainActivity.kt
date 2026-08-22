package com.example.composeprofile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composeprofile.ui.EmployeeScreen
import com.example.composeprofile.ui.theme.ComposeProfileTheme

class MainActivity : ComponentActivity() {

    companion object {

        private const val TAG =
            "EmployeeLifecycle"
    }


    // -----------------------------------------------------
    // Activity Lifecycle
    // -----------------------------------------------------

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        Log.d(
            TAG,
            "Activity -> onCreate()"
        )


        setContent {

            ComposeProfileTheme {

                EmployeeScreen()
            }
        }
    }


    override fun onStart() {

        super.onStart()

        Log.d(
            TAG,
            "Activity -> onStart()"
        )
    }


    override fun onResume() {

        super.onResume()

        Log.d(
            TAG,
            "Activity -> onResume()"
        )
    }


    override fun onPause() {

        super.onPause()

        Log.d(
            TAG,
            "Activity -> onPause()"
        )
    }


    override fun onStop() {

        super.onStop()

        Log.d(
            TAG,
            "Activity -> onStop()"
        )
    }


    override fun onDestroy() {

        super.onDestroy()

        Log.d(
            TAG,
            "Activity -> onDestroy()"
        )
    }
}