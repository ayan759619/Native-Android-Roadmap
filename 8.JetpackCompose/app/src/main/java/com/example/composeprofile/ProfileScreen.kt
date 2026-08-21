package com.example.composeprofile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button

@Composable
fun ProfileScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {        ProfileToolbar()

        ProfileHeader(
            name = "Ayan Karmakar",
            email = "ayan@example.com"
        )

        ProfileDetail(
            title = "Name",
            value = "Ayan Karmakar"
        )

        ProfileDetail(
            title = "Email",
            value = "ayan@example.com"
        )

        ProfileDetail(
            title = "Phone",
            value = "+91 9876543210"
        )

        ProfileButton(
            text = "Edit Profile",
            onClick = {
                Log.d("profilelifecycle", "Edit Profile clicked")
            }
        )

        ProfileButton(
            text = "Logout",
            onClick = {
                Log.d("Profilelogout", "Logout clicked")
            }
        )

        Text(
            text = "My Profile"
        )

        Text(
            text = "Ayan Karmakar"
        )

        Text(
            text = "ayan@example.com"
        )
    }
}
@Composable
fun ProfileToolbar() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "←",
            fontSize = 24.sp
        )

        Text(
            text = "My Profile",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "⋮",
            fontSize = 24.sp
        )
    }
}
@Composable
fun ProfileHeader(
    name: String,
    email: String
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "👤",
            fontSize = 60.sp
        )

        Text(
            text = name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = email,
            fontSize = 14.sp
        )
    }
}
@Composable
fun ProfileDetail(
    title: String,
    value: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Text(
            text = title,
            fontSize = 13.sp
        )

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
@Composable
fun ProfileButton(
    text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = text
        )
    }
}