package com.example.covid19tracker.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import com.example.covid19tracker.home.CovidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignInScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: CovidViewModel = viewModel()) {

    val email by viewModel.email
    var password by viewModel.password
    var showErrorDialog by viewModel.showErrorDialog

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign In")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { viewModel.email.value = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { viewModel.password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {  viewModel.login(navController) }) {
            Text("Sign In")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Error") },
                text = { Text("Invalid username or password") },
                confirmButton = {
                    Button(
                        onClick = { showErrorDialog = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }

        Text(
            text = "Don't have an account?",
            color = Color.Gray,
            modifier = Modifier.clickable { navController.navigate("signup") }
        )
    }
}