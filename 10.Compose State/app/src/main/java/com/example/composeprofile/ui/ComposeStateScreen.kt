package com.example.composeprofile.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val TAG = "ComposeState"


// =========================================================
// Main Screen
// =========================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeStateScreen() {

    /*
     * Parent owns the state.
     *
     * This is called STATE HOISTING.
     */

    var counter by remember {
        mutableStateOf(0)
    }

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }


    /*
     * This will run whenever counter changes.
     *
     * It is useful for understanding
     * recomposition.
     */

    LaunchedEffect(counter) {

        Log.d(
            TAG,
            "Counter changed -> $counter"
        )
    }


    Scaffold(

        topBar = {

            TopAppBar(
                title = {

                    Text(
                        text = "Compose State"
                    )
                }
            )
        }

    ) { innerPadding ->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)

        ) {


            // ------------------------------------------------
            // Counter Section
            // ------------------------------------------------

            CounterCard(

                count = counter,

                onIncrement = {

                    counter++
                },

                onDecrement = {

                    counter--
                },

                onReset = {

                    counter = 0
                }
            )


            // ------------------------------------------------
            // Form Section
            // ------------------------------------------------

            ProfileForm(

                name = name,

                email = email,

                onNameChange = { newName ->

                    name = newName
                },

                onEmailChange = { newEmail ->

                    email = newEmail
                }
            )


            // ------------------------------------------------
            // Parent State Display
            // ------------------------------------------------

            StatePreview(

                name = name,

                email = email,

                counter = counter
            )
        }
    }
}


// =========================================================
// Counter Card
// =========================================================

@Composable
fun CounterCard(

    count: Int,

    onIncrement: () -> Unit,

    onDecrement: () -> Unit,

    onReset: () -> Unit

) {

    /*
     * This composable DOES NOT own the counter.
     *
     * It receives the current value from the parent.
     *
     * It emits events back to the parent.
     */

    Card(

        modifier = Modifier.fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {

            Text(

                text = "Counter",

                fontSize = 20.sp,

                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            Text(

                text = count.toString(),

                fontSize = 40.sp,

                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            Row(

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)

            ) {

                Button(

                    onClick = {

                        onDecrement()
                    }

                ) {

                    Text(
                        text = "-"
                    )
                }


                Button(

                    onClick = {

                        onReset()
                    }

                ) {

                    Text(
                        text = "Reset"
                    )
                }


                Button(

                    onClick = {

                        onIncrement()
                    }

                ) {

                    Text(
                        text = "+"
                    )
                }
            }
        }
    }
}


// =========================================================
// Profile Form
// =========================================================

@Composable
fun ProfileForm(

    name: String,

    email: String,

    onNameChange: (String) -> Unit,

    onEmailChange: (String) -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)

        ) {

            Text(

                text = "Profile Form",

                fontSize = 20.sp,

                fontWeight = FontWeight.Bold
            )


            // ------------------------------------------------
            // Name
            // ------------------------------------------------

            OutlinedTextField(

                value = name,

                onValueChange = { newValue ->

                    onNameChange(newValue)
                },

                modifier = Modifier.fillMaxWidth(),

                label = {

                    Text(
                        text = "Name"
                    )
                },

                placeholder = {

                    Text(
                        text = "Enter your name"
                    )
                },

                singleLine = true
            )


            // ------------------------------------------------
            // Email
            // ------------------------------------------------

            OutlinedTextField(

                value = email,

                onValueChange = { newValue ->

                    onEmailChange(newValue)
                },

                modifier = Modifier.fillMaxWidth(),

                label = {

                    Text(
                        text = "Email"
                    )
                },

                placeholder = {

                    Text(
                        text = "Enter your email"
                    )
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),

                singleLine = true
            )
        }
    }
}


// =========================================================
// State Preview
// =========================================================

@Composable
fun StatePreview(

    name: String,

    email: String,

    counter: Int

) {

    Card(

        modifier = Modifier.fillMaxWidth()

    ) {

        Column(

            modifier = Modifier.padding(16.dp)

        ) {

            Text(

                text = "Current Parent State",

                fontSize = 18.sp,

                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Text(
                text = "Name: ${
                    if (name.isEmpty()) {
                        "Not entered"
                    } else {
                        name
                    }
                }"
            )


            Text(
                text = "Email: ${
                    if (email.isEmpty()) {
                        "Not entered"
                    } else {
                        email
                    }
                }"
            )


            Text(
                text = "Counter: $counter"
            )
        }
    }
}