package com.example.covid19tracker.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.covid19tracker.home.CovidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun SignUpScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: CovidViewModel = viewModel()) {

    val email by viewModel.email
    val password by viewModel.password
    val confirmPassword by viewModel.confirmPassword
    val showErrorDialog by viewModel.showErrorDialog
    val showSuccessDialog by viewModel.showSuccessDialog

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign Up")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { viewModel.email.value = it },
            label = { Text("Email") },
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { viewModel.password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = confirmPassword,
            onValueChange = { viewModel.confirmPassword.value = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.createAccount(navController)
        }) {
            Text("Sign Up")
        }

        if (showErrorDialog ) {
            AlertDialog(
                onDismissRequest = { viewModel.showErrorDialog.value = false },
                title = { Text("Error") },
                text = { Text("Passwords do not match or do not meet the credential criteria!") },
                confirmButton = {
                    Button(
                        onClick = { viewModel.showErrorDialog.value = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.showSuccessDialog.value = false },
                title = { Text("Success") },
                text = { Text("Account successfully created!") },
                confirmButton = {
                    Button(
                        onClick = { viewModel.showSuccessDialog.value = false
                            navController.navigate("signin")
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Already have an account?",
            color = Color.Gray,
            modifier = Modifier.clickable { navController.navigate("signin") }
        )
    }
}



